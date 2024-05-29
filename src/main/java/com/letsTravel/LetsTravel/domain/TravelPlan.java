package com.letsTravel.LetsTravel.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TravelPlan {
	private PlanCreateDTO plan;
	private List<ScheduleCreateDTO> placeList;
}
