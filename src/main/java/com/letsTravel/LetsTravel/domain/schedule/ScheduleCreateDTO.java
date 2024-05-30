package com.letsTravel.LetsTravel.domain.schedule;

import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleCreateDTO {

	private int planSeq;
	private String placeId;
	private int dateSeq;
	private int visitSeq;
	private Time visitTime;
}
