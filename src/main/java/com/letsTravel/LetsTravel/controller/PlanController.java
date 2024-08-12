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

import com.letsTravel.LetsTravel.domain.plan.PlanInfoDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanWrapper;
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
	public List<PlanInfoDTO> readPlanByMemberSeq(@PathVariable("member-seq") int memberSeq) {
		return planService.readPlanByMemberSeq(memberSeq);
	}

	@GetMapping("/plan/{plan-seq}")
	public PlanWrapper readPlanByPlanSeq(@PathVariable("plan-seq") int planSeq) {
		PlanDetailDTO planDetailDTO = planService.readPlanByPlanSeq(planSeq);
		return new PlanWrapper(planDetailDTO);
	}

	@PostMapping("/plan")
	public PlanWrapper createPlan(@RequestBody PlanWrapper planWrapper) {
		PlanDetailDTO planDetailDTO = planService.createPlan(planWrapper.getPlan());
		return new PlanWrapper(planDetailDTO);
	}

	@GetMapping("/plan/recommend")
	public PlanWrapper getRecommendPlan(@RequestParam("country-code") String countryCode, 
			@RequestParam("place-id") List<String> placeIdList,
			@RequestParam(value="accommodation-id", required = false) String accommodationId,
			@RequestParam(value ="airport-id", required = false) String airportId,
			@RequestParam("ndays") Integer planNDays,
			@RequestParam("tsp-processing-time") Integer tspProcessingTime) {
		return planService.getRecommendPlan(countryCode, placeIdList, accommodationId, airportId, planNDays, tspProcessingTime);
	}
}
