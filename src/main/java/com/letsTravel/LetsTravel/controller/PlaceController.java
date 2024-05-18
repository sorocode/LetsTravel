package com.letsTravel.LetsTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;
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
	public int createPlace(PlaceCreateDTO placeCreateDTO) {
		return placeService.createPlace(placeCreateDTO);
	}
}
