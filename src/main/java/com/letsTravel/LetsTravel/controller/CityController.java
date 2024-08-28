package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.city.CityListWrapper;
import com.letsTravel.LetsTravel.service.CityService;

@RestController
@RequestMapping(value = "/api")
public class CityController {

	private final CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping("/city")
	public CityListWrapper readCityByKeyWord(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "country-code", required = false) List<String> countryCodeList) {
		CityListWrapper cityListWrapper = new CityListWrapper(cityService.findCities(keyword, countryCodeList));
		return cityListWrapper;
	}
}
