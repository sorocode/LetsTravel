package com.letsTravel.LetsTravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;
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

	public int createPlace(PlaceCreateDTO placeCreateDTO) {
		// City가 저장되어 있는지 확인
		CityCreateDTO cityCreateDTO = CityCreateDTO.builder()
				.countryCode(placeCreateDTO.getCountryCode())
				.cityName(placeCreateDTO.getCityName())
				.cityLanguageCode(placeCreateDTO.getCityLanguageCode())
				.build();
		cityService.addCity(cityCreateDTO);
		
		return 0;
	}

}
