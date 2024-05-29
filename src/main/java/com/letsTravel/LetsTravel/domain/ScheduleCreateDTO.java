package com.letsTravel.LetsTravel.domain;

import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleCreateDTO {

	private int planSeq;
	private String placeName;
	private int dateSeq;
	private int visitSeq;
	private Time visitTime;
}
