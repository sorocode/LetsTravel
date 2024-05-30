package com.letsTravel.LetsTravel.domain.plan;

import java.util.List;

import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelPlan {
	private PlanCreateDTO plan;
	private List<ScheduleCreateDTO> schedules;
}
