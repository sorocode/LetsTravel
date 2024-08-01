package com.letsTravel.LetsTravel.domain.schedule;

import java.sql.Time;
import java.util.List;

import com.letsTravel.LetsTravel.domain.place.Place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInfoDTO {
	
	private int scheduleSeq;
	private Place place;
	private int dateSeq;
	private int visitSeq;
	private Time visitTime;
}
