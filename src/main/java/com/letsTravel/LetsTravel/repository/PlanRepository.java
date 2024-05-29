package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.PlanCreateDTO;
import com.letsTravel.LetsTravel.domain.TravelPlan;

public interface PlanRepository {

	public int addPlan(PlanCreateDTO planCreateDTO);

}
