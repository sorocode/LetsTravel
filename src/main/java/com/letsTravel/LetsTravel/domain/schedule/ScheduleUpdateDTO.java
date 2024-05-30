package com.letsTravel.LetsTravel.domain.schedule;

import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleUpdateDTO {

	private int scheduleSeq;
	private int placeSeq;
	private int dateSeq;
	private int visitSeq;
	private Time visitTime;
}
