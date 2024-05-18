package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.GoogleMapsPlace;
import com.letsTravel.LetsTravel.repository.PlaceRepository;

@Service
public class PlaceService {

	private final PlaceRepository placeRepository;
	private final CityService cityService;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, CityService cityService) {
		this.placeRepository = placeRepository;
		this.cityService = cityService;
	}

	public int createPlaces(List<GoogleMapsPlace> GoogleMapsPlaceList) {
		
		int result = 0;
		
		for (int i = 0; i < GoogleMapsPlaceList.size(); i++) {
			// City 저장(없으면 저장, 있으면 패스)
			List<CityCreateDTO> cityCreateDTOList = GoogleMapsPlaceList.get(i).getCities();
			for (int j = 0; j < cityCreateDTOList.size(); j++)
				cityService.addCity(cityCreateDTOList.get(i));
			
			// Place 저장
			result += placeRepository.addPlace(GoogleMapsPlaceList.get(i).getPlaceDetail());
			
			// Type 번역
		}

		return result;
	}

}
