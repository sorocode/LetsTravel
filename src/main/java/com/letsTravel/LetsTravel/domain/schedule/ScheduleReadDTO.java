package com.letsTravel.LetsTravel.domain.schedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReadDTO {

	private int dateSeq;
	private List<ScheduleDetailDTO> scheduleDetail;
}
