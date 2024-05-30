package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.plan.PlanBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanSeqReturnDTO;
import com.letsTravel.LetsTravel.domain.plan.TravelPlan;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
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

	@Transactional
	public PlanSeqReturnDTO createPlan(TravelPlan travelPlan) {
		int planSeq = planRepository.addPlan(travelPlan.getPlan());

		List<ScheduleCreateDTO> scheduleList = travelPlan.getSchedules();
		for (int i = 0; i < scheduleList.size(); i++) {
			scheduleList.get(i).setPlanSeq(planSeq);
			scheduleRepository.addSchedule(scheduleList.get(i));
		}
		return new PlanSeqReturnDTO(planSeq);
	}

	public List<PlanBasicInfoReadDTO> readPlanByMemberSeq(int memberSeq) {
		return planRepository.findPlanByMemberSeq(memberSeq);
	}

	public PlanDetailReadDTO readPlanByPlanSeq(int planSeq) {
		return planRepository.findPlanByPlanSeq(planSeq);
	}
}
