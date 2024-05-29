package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.TravelPlan;
import com.letsTravel.LetsTravel.repository.PlanRepository;
import com.letsTravel.LetsTravel.repository.ScheduleRepository;

@Service
public class PlanService {

	private final PlanRepository planRepository;
	private final ScheduleRepository scheduleRepository;

	@Autowired
	public PlanService(PlanRepository planRepository, ScheduleRepository scheduleRepository) {
		this.planRepository = planRepository;
		this.scheduleRepository = scheduleRepository;
	}

	public int createPlan(TravelPlan travelPlan) {
		int planSeq = planRepository.addPlan(travelPlan.getPlan());

		List<ScheduleCreateDTO> scheduleList = travelPlan.getPlaceList();
		for (int i = 0; i < scheduleList.size(); i++) {
			scheduleList.get(i).setPlanSeq(planSeq);
			scheduleRepository.addSchedule(scheduleList.get(i));
		}
		return planSeq;
	}

}
