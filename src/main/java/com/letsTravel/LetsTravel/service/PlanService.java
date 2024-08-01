package com.letsTravel.LetsTravel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanInfoDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleInfoDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;
import com.letsTravel.LetsTravel.repository.MemberRepository;
import com.letsTravel.LetsTravel.repository.PlaceRepository;
import com.letsTravel.LetsTravel.repository.PlanRepository;
import com.letsTravel.LetsTravel.repository.ScheduleRepository;

@Service
public class PlanService {

	private final PlanRepository planRepository;
	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;
	private final CityRepository cityRepository;
	private final PlaceRepository placeRepository;

	@Autowired
	public PlanService(PlanRepository planRepository, ScheduleRepository scheduleRepository, MemberRepository memberRepository, CityRepository cityRepository, PlaceRepository placeRepository) {
		this.planRepository = planRepository;
		this.scheduleRepository = scheduleRepository;
		this.memberRepository = memberRepository;
		this.cityRepository = cityRepository;
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
		planDetailDTO.setPlanCities(cityRepository.findPlanCitiesByPlanSeq(planSeq));
		// 개선할 필요가 매우 매우 있어보여요
		List<ScheduleInfoDTO> schedules = scheduleRepository.findSchedulesByPlanSeq(planSeq);
		List<Integer> placeSeqList = new ArrayList<Integer>();
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
}
