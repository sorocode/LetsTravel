package com.letsTravel.LetsTravel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.MetropolisCityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;
import com.letsTravel.LetsTravel.domain.place.AddressComponent;
import com.letsTravel.LetsTravel.domain.place.DisplayName;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceProcReturnDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceWrapper;
import com.letsTravel.LetsTravel.domain.type.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeDetailDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeUpdateDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;
import com.letsTravel.LetsTravel.repository.MetropolisRepository;
import com.letsTravel.LetsTravel.repository.PlaceRepository;
import com.letsTravel.LetsTravel.repository.TypeRepository;

@Service
public class PlaceService {

	private final PlaceRepository placeRepository;
	private final MetropolisRepository metropolisRepository;
	private final TypeRepository typeRepository;
	private final CityRepository cityRepository;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, MetropolisRepository metropolisRepository, TypeRepository typeRepository, CityRepository cityRepository) {
		this.placeRepository = placeRepository;
		this.metropolisRepository = metropolisRepository;
		this.typeRepository = typeRepository;
		this.cityRepository = cityRepository;
	}

	@Transactional
	public PlaceWrapper createPlace(PlaceWrapper placeWrapper) {

		// Place 개수만큼 place 등록
		for (int placeIndex = 0; placeIndex < placeWrapper.getPlaces().size(); placeIndex++) {
			Place place = placeWrapper.getPlaces().get(placeIndex);

			List<String> types = place.getTypes();

			// 넘어온 Place가 국가이거나 도시인지 체크
			if (types.contains("country") || types.contains("administrative_area_level_1") || types.contains("administrative_area_level_2") || types.contains("colloquial_area")) {
				continue;
			}

			// countryCode만 뽑아올 수 있는 방법이 없을까
			List<AddressComponent> addressComponentList = place.getAddressComponents();
			List<MetropolisCreateDTO> metropolisList = new ArrayList<>();
			CityCreateDTO city = null;

			for (AddressComponent addressComponent : addressComponentList) {
				addressComponent.getTypes().remove("political"); // political이 componentType으로 선정되는 경우가 있어 추가 -- 2024.07.31 강봉수
				String componentType = addressComponent.getTypes().get(0);
				if (componentType.equals("country")) {
					place.setCountryCode(addressComponent.getShortText());
				}
				else if (componentType.equals("administrative_area_level_1") || componentType.equals("administrative_area_level_2")) {
					metropolisList.add(new MetropolisCreateDTO(componentType, addressComponent.getLongText(), addressComponent.getLanguageCode()));
				}
				// locality, sublocality_level_1 둘 중 하나 저장 -- 2024.08.06 강봉수
				else if (componentType.equals("locality")) {
					city = new CityCreateDTO(componentType, addressComponent.getLongText(), addressComponent.getLanguageCode());
				}
				else if (city == null && componentType.equals("sublocality_level_1")) {
					city = new CityCreateDTO(componentType, addressComponent.getLongText(), addressComponent.getLanguageCode());
				}
			}

			// Place 저장
			PlaceProcReturnDTO placeProcReturnDTO = placeRepository.addPlace(place);
			Long placeSeq = placeProcReturnDTO.getPlaceSeq();
			place.setPlaceSeq(placeSeq);

			// 등록한 적 없는 Place이면 DB에 저장
			if (!placeProcReturnDTO.isExisted()) {
				metropolisList.get(0).setCountryCode(place.getCountryCode());
				// City 저장
				Long citySeq = cityRepository.addCity(city, metropolisList.get(0));

				// Metropolis 저장
				for (MetropolisCreateDTO metropolisCreateDTO : metropolisList) {
					metropolisCreateDTO.setCountryCode(place.getCountryCode());
					metropolisRepository.addMetropolis(metropolisCreateDTO);
				}

				// Metropolis-City 관계 저장
				for (MetropolisCreateDTO metropolisCreateDTO : metropolisList) {
					cityRepository.addMetropolisCity(new MetropolisCityCreateDTO(metropolisCreateDTO, citySeq));
				}

				// Place의 City 저장
				cityRepository.addPlaceCity(new PlaceCityCreateDTO(placeSeq, citySeq));

				// Place의 Type 저장
				types.remove("establishment");
				types.remove("point_of_interest");
				if (types.size() == 0) {
					types.add("etc");
					place.setPrimaryType("etc");
					place.setPrimaryTypeDisplayName(new DisplayName("기타", "ko"));
				}
				for (String type : types)
					typeRepository.addPlaceType(new PlaceTypeCreateDTO(placeSeq, type));

				// Primary Type 정보가 없으면
				if (place.getPrimaryType() == null) {
					place.setPrimaryType(types.get(0));
					place.setPrimaryTypeDisplayName(new DisplayName(types.get(0), "en"));
				}

				// Place의 Primary Type 설정
				typeRepository.modifyPrimaryType(new PrimaryTypeUpdateDTO(placeSeq, place.getPrimaryType()));

				// Type 번역
				// Type 번역이 얼추 완료되면 지워도 됨
				if (place.getPrimaryTypeDisplayName().getLanguageCode().equals("ko"))
					typeRepository.modifyTypeNameTranslated(new PrimaryTypeDetailDTO(place.getPrimaryTypeDisplayName().getText(), place.getPrimaryType()));
			}
		}

		return placeWrapper;
	}

	public PlaceWrapper readPlaces(List<Integer> city, List<Integer> type, String keyword, Integer page, Integer size, String sort) {
		return new PlaceWrapper(placeRepository.findPlaceByPlaceSeq(placeRepository.findPlaces(city, type, keyword, page, size, sort)));
	}

	public PlaceWrapper readPlaceByPlaceSeq(List<Long> placeSeq) {
		return new PlaceWrapper(placeRepository.findPlaceByPlaceSeq(placeSeq));
	}
}
