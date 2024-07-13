package com.letsTravel.LetsTravel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.place.AddressComponent;
import com.letsTravel.LetsTravel.domain.place.DisplayName;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceInfoDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceReadDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceWrapper;
import com.letsTravel.LetsTravel.domain.type.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeDetailDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeUpdateDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;
import com.letsTravel.LetsTravel.repository.PlaceRepository;
import com.letsTravel.LetsTravel.repository.TypeRepository;

@Service
public class PlaceService {

	private final PlaceRepository placeRepository;
	private final CityRepository cityRepository;
	private final TypeRepository typeRepository;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, CityRepository cityRepository, TypeRepository typeRepository) {
		this.placeRepository = placeRepository;
		this.cityRepository = cityRepository;
		this.typeRepository = typeRepository;
	}

	@Transactional
	public PlaceWrapper createPlace(PlaceWrapper placeWrapper) {

		// Place 개수만큼 place 등록
		for (int placeIndex = 0; placeIndex < placeWrapper.getPlaces().size(); placeIndex++) {
			Place place = placeWrapper.getPlaces().get(placeIndex);

			List<String> types = place.getTypes();

			// 넘어온 Place가 국가이거나 도시인지 체크
			if (types.contains("country") || types.contains("administrative_area_level_1") || types.contains("administrative_area_level_2")) {
				continue;
			}

			List<AddressComponent> addressComponentList = place.getAddressComponents();
			List<CityCreateDTO> cityList = new ArrayList<>();
			for (int addrComponentIndex = 0; addrComponentIndex < addressComponentList.size(); addrComponentIndex++) {
				String componentType = addressComponentList.get(addrComponentIndex).getTypes().get(0);
				if (componentType.equals("country")) {
					place.setCountryCode(addressComponentList.get(addrComponentIndex).getShortText());
				}
				if (componentType.equals("administrative_area_level_1") || componentType.equals("administrative_area_level_2")) {
					CityCreateDTO city = new CityCreateDTO();
					city.setCityName(addressComponentList.get(addrComponentIndex).getLongText());
					city.setCityNameLanguageCode(addressComponentList.get(addrComponentIndex).getLanguageCode());
					city.setType(addressComponentList.get(addrComponentIndex).getTypes().get(0));
					cityList.add(city);
				}
			}

			// City 저장(없으면 저장, 있으면 패스)
			for (int cityIndex = 0; cityIndex < cityList.size(); cityIndex++) {
				cityList.get(cityIndex).setCountryCode(place.getCountryCode());
				cityRepository.addCity(cityList.get(cityIndex));
			}

			// Place 저장
			int placeSeq = placeRepository.addPlace(place);
			place.setPlaceSeq(placeSeq);

			// Place의 City 저장
			for (int cityIndex = 0; cityIndex < cityList.size(); cityIndex++)
				cityRepository.addPlaceCity(new PlaceCityCreateDTO(placeSeq, cityList.get(cityIndex)));

			// Place의 Type 저장
			types.remove("establishment");
			types.remove("point_of_interest");
			if (types.size() == 0) {
				types.add("etc");
				place.setPrimaryType("etc");
				place.setPrimaryTypeDisplayName(new DisplayName("기타", "ko"));
			}
			for (int typeIndex = 0; typeIndex < types.size(); typeIndex++)
				typeRepository.addPlaceType(new PlaceTypeCreateDTO(placeSeq, types.get(typeIndex)));

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

		return placeWrapper;
	}

	public PlaceWrapper readPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword) {
		return placeRepository.findPlaces(countryCode, city, type, keyword);
	}

	public PlaceWrapper readPlaceByPlaceSeq(int placeSeq) {
		return placeRepository.findPlaceByPlaceSeq(placeSeq);
	}
}
