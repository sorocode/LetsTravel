package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.plan.PlanInfoDTO;

public interface PlanRepository {

	public int addPlan(PlanInfoDTO planInfoDTO);
	public List<PlanInfoDTO> findPlanByMemberSeq(int memberSeq);
	public PlanInfoDTO findPlanByPlanSeq(int planSeq);
}
