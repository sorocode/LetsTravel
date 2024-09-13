package com.letsTravel.LetsTravel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanInfoDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanWrapper;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleInfoDTO;
import com.letsTravel.LetsTravel.repository.MetropolisRepository;
import com.letsTravel.LetsTravel.repository.MemberRepository;
import com.letsTravel.LetsTravel.repository.PlaceRepository;
import com.letsTravel.LetsTravel.repository.PlanRepository;
import com.letsTravel.LetsTravel.repository.ScheduleRepository;
import com.letsTravel.LetsTravel.util.tsp.Christofides;

@Service
public class PlanService {

	private final PlanRepository planRepository;
	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;
	private final MetropolisRepository metropolisRepository;
	private final PlaceRepository placeRepository;

	@Autowired
	public PlanService(PlanRepository planRepository, ScheduleRepository scheduleRepository, MemberRepository memberRepository, MetropolisRepository metropolisRepository,
			PlaceRepository placeRepository) {
		this.planRepository = planRepository;
		this.scheduleRepository = scheduleRepository;
		this.memberRepository = memberRepository;
		this.metropolisRepository = metropolisRepository;
		this.placeRepository = placeRepository;
	}

	@Transactional
	public PlanDetailDTO createPlan(PlanDetailDTO planDetailDTO) {
		int planSeq = planRepository.addPlan(planDetailDTO.getPlanInfo());
		planDetailDTO.getPlanInfo().setPlanSeq(planSeq);

		List<ScheduleInfoDTO> scheduleList = planDetailDTO.getSchedules();
		for (int i = 0; i < scheduleList.size(); i++) {
			ScheduleCreateDTO schedule = new ScheduleCreateDTO(scheduleList.get(i));
			schedule.setPlanSeq(planSeq);
			scheduleRepository.addSchedule(schedule);
		}

		planDetailDTO.setSchedules(scheduleList);
		return planDetailDTO;
	}

	public List<PlanInfoDTO> readPlanByMemberSeq(int memberSeq) {
		return planRepository.findPlanByMemberSeq(memberSeq);
	}

	public PlanDetailDTO readPlanByPlanSeq(int planSeq) {
		PlanDetailDTO planDetailDTO = new PlanDetailDTO();
		planDetailDTO.setPlanInfo(planRepository.findPlanByPlanSeq(planSeq));
		planDetailDTO.setPlanShareMembers(memberRepository.findPlanShareMemberByPlanSeq(planSeq));
		// 개선할 필요가 매우 매우 있어보여요
		List<ScheduleInfoDTO> schedules = scheduleRepository.findSchedulesByPlanSeq(planSeq);
		List<Long> placeSeqList = new ArrayList<Long>();
		for (int scheduleIndex = 0; scheduleIndex < schedules.size(); scheduleIndex++) {
			placeSeqList.add(schedules.get(scheduleIndex).getPlace().getPlaceSeq());
		}
		List<Place> placeList = placeRepository.findPlaceByPlaceSeq(placeSeqList);
		// O(N^2)이잔아..
		for (int scheduleIndex = 0; scheduleIndex < schedules.size(); scheduleIndex++) {
			int placeIndex = 0;
			while (placeList.get(placeIndex).getPlaceSeq() != schedules.get(scheduleIndex).getPlace().getPlaceSeq())
				placeIndex++;
			schedules.get(scheduleIndex).setPlace(placeList.get(placeIndex));
		}
		planDetailDTO.setSchedules(schedules);
		return planDetailDTO;
	}

	public PlanWrapper getRecommendPlan(String countryCode, List<String> placeIdList, String accommodationId, String airportId, Integer planNDays, Integer tspProcessingTime) {
		// PlanGenerator planGenerator = new PlanGenerator(placeRepository,
		// tspProcessingTime);
		// return new PlanWrapper(planGenerator.generateRecommendPlan(countryCode,
		// placeIdList, accommodationId, airportId, planNDays));
		return new PlanWrapper(generateRecommendPlan(countryCode, placeIdList, accommodationId, airportId, planNDays, tspProcessingTime));
	}

	private PlanDetailDTO generateRecommendPlan(String countryCode, List<String> placeIdList, String accommodationId, String airportId, Integer planNDays, Integer tspProcessingTime) {
		PlanDetailDTO plan = new PlanDetailDTO();
		List<Place> placeList = placeRepository.findPlaceByPlaceId(placeIdList);
		Place accommodation = null;
		String accommodationCity = null;
		Place airport = null;
		String airportCity = null;

		// locality(이하 도시)별로 장소를 분류
		Map<String, List<Place>> placesGroupedByCity = new HashMap<String, List<Place>>();
		// O(3N)
		for (Place place : placeList) {
			String cityName = place.getCityName();
			if (placesGroupedByCity.get(cityName) == null) {
				placesGroupedByCity.put(cityName, new ArrayList<Place>(Arrays.asList(place)));
			}
			else {
				placesGroupedByCity.get(cityName).add(place);
			}

			// 숙소 찾기
			if (place.getId().equals(accommodationId)) {
				accommodation = place;
				accommodationCity = cityName;
			}
			// 공항 찾기
			if (place.getId().equals(airportId)) {
				airport = place;
				airportCity = cityName;
			}
		}

		// 공항 있는 도시 따로 빼놓기
		List<Place> placesInAirportCity = placesGroupedByCity.remove(airportCity);

		// 도시별로 Place 하나씩 추출
		List<Place> cityTraversalList = new ArrayList<Place>();
		for (Entry<String, List<Place>> entry : placesGroupedByCity.entrySet()) {
			String city = entry.getKey();
			// 숙소가 있는 도시이면 맨 앞에 삽입
			if (city.equals(accommodationCity)) {
				cityTraversalList.add(0, entry.getValue().get(0));
			}
			else {
				cityTraversalList.add(entry.getValue().get(0));
			}
		}

		// 도시 순회 순서 최적화
		List<Integer> cityTraversalOrder = Christofides.christofidesAlgorithm(cityTraversalList, tspProcessingTime).getFinalTour();
		cityTraversalList = sortPlacesByTraversalOrder(cityTraversalList, cityTraversalOrder);
		// 일자별 여행 장소 저장할 Map
		Map<Integer, List<Place>> planMap = new HashMap<Integer, List<Place>>();
		// 2박 3일
		if (planNDays == 3) {
			// 도시 수 5개 이상
			if (cityTraversalList.size() >= 5) {
				// 첫날: 숙소 있는 곳과 멀지 않은 곳에 있는 도시
				String firstDayCity = cityTraversalList.remove(cityTraversalList.size() - 1).getCityName();
				List<Place> firstDay = placesGroupedByCity.remove(firstDayCity);
				if (accommodation != null)
					firstDay.add(0, accommodation);
				// (공항을 제외하고) 여행지+숙소의 최적 경로
				List<Integer> firstDayOrder = Christofides.christofidesAlgorithm(firstDay, tspProcessingTime).getFinalTour();
				firstDay = sortPlacesByTraversalOrder(firstDay, firstDayOrder);
				// 공항->숙소->여행지->숙소
				if (airport != null)
					firstDay.add(0, airport);
				if (accommodation != null)
					firstDay.add(accommodation);
				planMap.put(1, firstDay);

				// 막날: 숙소 있는 도시
				String lastDayCity = cityTraversalList.remove(0).getCityName();
				List<Place> lastDay = placesGroupedByCity.remove(lastDayCity);
				// 숙소를 처음으로 이동
				if (accommodation != null) {
					lastDay.remove(accommodation);
					lastDay.add(0, accommodation);
				}
				// (공항을 제외하고) 여행지+숙소의 최적 경로
				List<Integer> lastDayOrder = Christofides.christofidesAlgorithm(lastDay, tspProcessingTime).getFinalTour();
				lastDay = sortPlacesByTraversalOrder(lastDay, lastDayOrder);
				// 숙소->여행지->공항
				if (airport != null)
					lastDay.add(airport);
				planMap.put(planNDays, lastDay);

				// 둘째날: 나머지 도시의 여행지를 총집합
				List<Place> secondDay = placesGroupedByCity.values().stream().flatMap(places -> places.stream()).collect(Collectors.toList());
				// 숙소 시작
				if (accommodation != null)
					secondDay.add(0, accommodation);
				// 여행지+숙소의 최적 경로
				List<Integer> secondDayOrder = Christofides.christofidesAlgorithm(secondDay, tspProcessingTime).getFinalTour();
				secondDay = sortPlacesByTraversalOrder(secondDay, secondDayOrder);
				// 숙소->여행지->숙소
				if (accommodation != null)
					secondDay.add(accommodation);
				planMap.put(2, secondDay);

				System.out.println("1일차: " + firstDay);
				System.out.println("2일차: " + secondDay);
				System.out.println("3일차: " + lastDay);
			}
			// 도시 수 2개 이하는 계획 안 짜줘요ㅗ
			else if (cityTraversalList.size() <= 2)
				;
			// 도시수 3~4개
			else {
				// 첫날: 숙소 있는 도시
				String firstDayCity = cityTraversalList.remove(0).getCityName();
				List<Place> firstDay = placesGroupedByCity.remove(firstDayCity);
				// 숙소를 처음으로 이동
				if (accommodation != null) {
					firstDay.remove(accommodation);
					firstDay.add(0, accommodation);
				}
				// (공항을 제외하고) 여행지+숙소의 최적 경로
				List<Integer> firstDayOrder = Christofides.christofidesAlgorithm(firstDay, tspProcessingTime).getFinalTour();
				firstDay = sortPlacesByTraversalOrder(firstDay, firstDayOrder);
				// 공항->숙소->여행지->숙소
				if (airport != null)
					firstDay.add(0, airport);
				if (accommodation != null)
					firstDay.add(accommodation);
				planMap.put(1, firstDay);

				// 숙소->공항
				List<Place> lastDay = new ArrayList<Place>();
				if (accommodation != null)
					lastDay.add(accommodation);
				if (airport != null)
					lastDay.add(airport);
				planMap.put(planNDays, lastDay);

				// 둘째날: 나머지 도시의 여행지를 총집합
				List<Place> secondDay = placesGroupedByCity.values().stream().flatMap(places -> places.stream()).collect(Collectors.toList());
				// 숙소 시작
				if (accommodation != null)
					secondDay.add(0, accommodation);
				// 여행지+숙소의 최적 경로
				List<Integer> secondDayOrder = Christofides.christofidesAlgorithm(secondDay, tspProcessingTime).getFinalTour();
				secondDay = sortPlacesByTraversalOrder(secondDay, secondDayOrder);
				// 숙소->여행지->숙소
				if (accommodation != null)
					secondDay.add(accommodation);
				planMap.put(2, secondDay);

				System.out.println("1일차: " + firstDay);
				System.out.println("2일차: " + secondDay);
				System.out.println("3일차: " + lastDay);
			}
		}
		// 1박 2일 or 당일치기는 계획 안 짜줘요
		else if (planNDays < 3)
			;
		// 3박 4일 이상
		else {
			// 첫날과 막날을 제외하고 하루에 방문할 도시의 개수
			int cityPerDay = (cityTraversalList.size() - 2) / (planNDays - 2);
			// 첫날: 숙소 있는 곳과 멀지 않은 곳에 있는 도시
			List<String> firstDayCity = new ArrayList<String>(Arrays.asList(cityTraversalList.remove(cityTraversalList.size() - 1).getCityName()));
			List<Place> firstDay = placesGroupedByCity.remove(firstDayCity.get(0));
			if (accommodation != null)
				firstDay.add(0, accommodation);
			// (공항을 제외하고) 여행지+숙소의 최적 경로
			List<Integer> firstDayOrder = Christofides.christofidesAlgorithm(firstDay, tspProcessingTime).getFinalTour();
			firstDay = sortPlacesByTraversalOrder(firstDay, firstDayOrder);
			// 공항->숙소->여행지->숙소
			if (airport != null)
				firstDay.add(0, airport);
			if (accommodation != null)
				firstDay.add(accommodation);
			planMap.put(1, firstDay);
			System.out.println("1일차: " + firstDay + " / " + firstDayCity);

			// 막날: 숙소 있는 도시
			List<String> lastDayCity = new ArrayList<String>(Arrays.asList(cityTraversalList.remove(0).getCityName()));
			List<Place> lastDay = placesGroupedByCity.remove(lastDayCity.get(0));
			// 숙소를 처음으로 이동
			if (accommodation != null) {
				lastDay.remove(accommodation);
				lastDay.add(0, accommodation);
			}
			// (공항을 제외하고) 여행지+숙소의 최적 경로
			List<Integer> lastDayOrder = Christofides.christofidesAlgorithm(lastDay, tspProcessingTime).getFinalTour();
			lastDay = sortPlacesByTraversalOrder(lastDay, lastDayOrder);
			// 숙소->여행지->공항
			if (airport != null)
				lastDay.add(airport);
			planMap.put(planNDays, lastDay);

			// 첫날과 막날을 제외한 나머지
			// additionalCityVisitCount의 값만큼 하루에 하나씩 도시를 더 방문해야 함.
			int additionalCityVisitCount = cityTraversalList.size() % (planNDays - 2);
			for (int dayIndex = 0; dayIndex < planNDays - 2; dayIndex++) {
				List<String> dayCity = new ArrayList<String>();
				List<Place> dayPlace = new ArrayList<Place>();
				// 하루에 도시 방문해야 하는 만큼 추가
				for (int cityVisitCount = 0; cityVisitCount < cityPerDay; cityVisitCount++) {
					dayCity.add(cityTraversalList.remove(0).getCityName());
					dayPlace.addAll(placesGroupedByCity.remove(dayCity.get(cityVisitCount)));
				}
				if (additionalCityVisitCount > 0) {
					dayCity.add(cityTraversalList.remove(0).getCityName());
					dayPlace.addAll(placesGroupedByCity.remove(dayCity.get(cityPerDay)));
					additionalCityVisitCount--;
				}
				// 숙소 시작
				if (accommodation != null)
					dayPlace.add(0, accommodation);
				// 여행지+숙소의 최적 경로
				List<Integer> dayOrder = Christofides.christofidesAlgorithm(dayPlace, tspProcessingTime).getFinalTour();
				dayPlace = sortPlacesByTraversalOrder(dayPlace, dayOrder);
				// 숙소->여행지->숙소
				if (accommodation != null)
					dayPlace.add(accommodation);
				planMap.put(2 + dayIndex, dayPlace);
				System.out.println((2 + dayIndex) + "일차: " + dayPlace + " / " + dayCity);
			}
			System.out.println(planNDays + "일차: " + lastDay + " / " + lastDayCity);
		}
		plan.setPlanInfo(new PlanInfoDTO());
		plan.setPlanShareMembers(new ArrayList<MemberBasicInfoReadDTO>());
		List<ScheduleInfoDTO> schedules = new ArrayList<ScheduleInfoDTO>();
		for (Map.Entry<Integer, List<Place>> entry : planMap.entrySet()) {
			List<Place> placeListPerDay = entry.getValue();
			for (int placeIndex = 0; placeIndex < placeListPerDay.size(); placeIndex++) {
				ScheduleInfoDTO schedule = new ScheduleInfoDTO();
				schedule.setDateSeq(entry.getKey());
				schedule.setVisitSeq(placeIndex + 1);
				schedule.setPlace(placeListPerDay.get(placeIndex));
				schedules.add(schedule);
			}
		}
		plan.setSchedules(schedules);

		return plan;
	}

	private List<Place> sortPlacesByTraversalOrder(List<Place> places, List<Integer> traversalOrder) {
		List<Place> sortedList = new ArrayList<Place>();
		for (int index : traversalOrder) {
			sortedList.add(places.get(index));
		}
		return sortedList;

	}
}