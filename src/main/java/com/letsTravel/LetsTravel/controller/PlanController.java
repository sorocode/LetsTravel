package com.letsTravel.LetsTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.TravelPlan;
import com.letsTravel.LetsTravel.service.PlanService;

@RestController
@RequestMapping(value = "/api")
public class PlanController {

	private final PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@PostMapping("/plan")
	public int createPlan(@RequestBody TravelPlan travelPlan) {
		return planService.createPlan(travelPlan);
	}
}
