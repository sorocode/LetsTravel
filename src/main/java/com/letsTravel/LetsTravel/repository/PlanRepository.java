package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.plan.PlanBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanCreateDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailReadDTO;
import com.letsTravel.LetsTravel.domain.plan.TravelPlan;

public interface PlanRepository {

	public int addPlan(PlanCreateDTO planCreateDTO);

	public List<PlanBasicInfoReadDTO> findPlanByMemberSeq(int memberSeq);

	public PlanDetailReadDTO findPlanByPlanSeq(int planSeq);

}
