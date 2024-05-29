package com.letsTravel.LetsTravel.controller;

import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.service.PlanService;

@RestController
public class PlanController {

	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	
}
