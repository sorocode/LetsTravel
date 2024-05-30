package com.letsTravel.LetsTravel.domain.plan;

import java.util.List;

import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleReadDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanDetailReadDTO {
	private PlanBasicInfoReadDTO planInfo;
	private List<MemberBasicInfoReadDTO> planShareMembers;
	private List<String> planCities;
	private List<ScheduleReadDTO> schedules;
}
