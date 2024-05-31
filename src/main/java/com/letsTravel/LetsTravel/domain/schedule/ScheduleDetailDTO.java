package com.letsTravel.LetsTravel.domain.schedule;

import java.sql.Time;

import com.letsTravel.LetsTravel.domain.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDetailDTO {
	private int scheduleSeq;
	private int placeSeq;
	private String placeName;
	private Location location;
	private String primaryType;
	private int visitSeq;
	private Time visitTime;
}
