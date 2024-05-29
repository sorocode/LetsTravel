package com.letsTravel.LetsTravel.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanCreateDTO {

	private int memSeq;
	private String planName;
	private String countryCode;
	private Date planStart;
	private int planNDays;
}
