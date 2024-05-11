package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.CityDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;

@Service
public class CityService {

	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public List<CityDTO> findCities(String countryCode) {
		return cityRepository.findCities(countryCode);
	}
}