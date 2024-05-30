package com.letsTravel.LetsTravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.plan.PlanBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailReadDTO;
import com.letsTravel.LetsTravel.domain.plan.TravelPlan;
import com.letsTravel.LetsTravel.service.PlanService;

@RestController
@RequestMapping(value = "/api")
public class PlanController {

	private final PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@GetMapping("/member/{member-seq}/plan")
	public List<PlanBasicInfoReadDTO> readPlanByMemberSeq(@PathVariable("member-seq")int memberSeq){
		return planService.readPlanByMemberSeq(memberSeq);
	}
	
	@GetMapping("/plan/{plan-seq}")
	public PlanDetailReadDTO readPlanByPlanSeq(@PathVariable("plan-seq")int planSeq) {
		return planService.readPlanByPlanSeq(planSeq);
	}
	
	@PostMapping("/plan")
	public int createPlan(@RequestBody TravelPlan travelPlan) {
		return planService.createPlan(travelPlan);
	}
}
