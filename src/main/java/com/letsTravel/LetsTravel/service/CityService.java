package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.CityReadDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;

@Service
public class CityService {

	private final CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public List<CityReadDTO> findCities(String countryCode) {
		return cityRepository.findCities(countryCode);
	}

	public int addCity(CityCreateDTO cityCreateDTO) {
		return cityRepository.addCity(cityCreateDTO);
	}

}
