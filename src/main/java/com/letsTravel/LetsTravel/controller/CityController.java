package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.city.CityReadDTO;
import com.letsTravel.LetsTravel.service.CityService;

@RestController
@RequestMapping(value = "/api")
public class CityController {

	private final CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping("/city/{country-code}")
	public List<CityReadDTO> readCityByCountryCode(@PathVariable("country-code") String countryCode) {
		List<CityReadDTO> cities = cityService.findCities(countryCode);
		return cities;
	}
}
