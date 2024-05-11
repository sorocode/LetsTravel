package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.CityDTO;
import com.letsTravel.LetsTravel.service.CityService;

@RestController
@RequestMapping(value = "/api")
public class CityController {

	private final CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping("/city/{countryCode}")
	public List<CityDTO> readCityByCountryCode(@PathVariable("countryCode") String countryCode) {
		List<CityDTO> cities = cityService.findCities(countryCode);
		return cities;
	}
}