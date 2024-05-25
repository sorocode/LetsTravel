package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.GoogleMapsPlace;
import com.letsTravel.LetsTravel.domain.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.PlaceReadDTO;
import com.letsTravel.LetsTravel.domain.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.PrimaryTypeUpdateDTO;
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
	public int createPlaces(List<GoogleMapsPlace> googleMapsPlaceList) {
		for (int placeIndex = 0; placeIndex < googleMapsPlaceList.size(); placeIndex++) {
			// City 저장(없으면 저장, 있으면 패스)
			List<CityCreateDTO> cityCreateDTOList = googleMapsPlaceList.get(placeIndex).getCities();
			for (int cityIndex = 0; cityIndex < cityCreateDTOList.size(); cityIndex++)
				cityRepository.addCity(cityCreateDTOList.get(cityIndex));

			// Place 저장
			int placeSeq = placeRepository.addPlace(googleMapsPlaceList.get(placeIndex).getPlaceDetail());

			// Place의 City 저장
			for (int cityIndex = 0; cityIndex < cityCreateDTOList.size(); cityIndex++)
				cityRepository.addPlaceCity(new PlaceCityCreateDTO(placeSeq, cityCreateDTOList.get(cityIndex)));

			// Place의 Type 저장
			List<String> types = googleMapsPlaceList.get(placeIndex).getTypes();
			for (int typeIndex = 0; typeIndex < types.size(); typeIndex++)
				typeRepository.addPlaceType(new PlaceTypeCreateDTO(placeSeq, types.get(typeIndex)));

			// Place의 Primary Type 설정
			typeRepository.modifyPrimaryType(new PrimaryTypeUpdateDTO(placeSeq,
					googleMapsPlaceList.get(placeIndex).getPrimaryTypeDetail().getPrimaryType()));

			// Type 번역
			typeRepository.modifyTypeNameTranslated(googleMapsPlaceList.get(placeIndex).getPrimaryTypeDetail());
		}

		return 200;
	}

	public List<PlaceReadDTO> findPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword) {
		return placeRepository.findPlaces(countryCode, city, type, keyword);
	}
}
