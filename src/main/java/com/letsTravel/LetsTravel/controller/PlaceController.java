package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.GoogleMapsPlace;
import com.letsTravel.LetsTravel.service.PlaceService;

@RestController
@RequestMapping(value = "/api")
public class PlaceController {
	
	private final PlaceService placeService;
	
	@Autowired
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	// 뭘 반환하지
	@PostMapping("/place")
	public int createPlaces(@RequestBody List<GoogleMapsPlace> googleMapsPlaceList) {
		return placeService.createPlaces(googleMapsPlaceList);
	}
}
