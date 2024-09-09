package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceInfoDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceReadDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceWrapper;
import com.letsTravel.LetsTravel.service.PlaceService;

@RestController
@RequestMapping(value = "/api")
public class PlaceController {

	private final PlaceService placeService;

	@Autowired
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}

	@PostMapping("/place")
	public PlaceWrapper createPlace(@RequestBody PlaceWrapper placeWrapper) {
		return placeService.createPlace(placeWrapper);
	}

	@GetMapping("/place")
	public PlaceWrapper readPlaces(@RequestParam(value = "city", required = false) List<Integer> city,
			@RequestParam(value = "type", required = false) List<Integer> type,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sort", required = false) String sort) {
		return placeService.readPlaces(city, type, keyword, page, size, sort);
	}
}
