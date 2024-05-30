package com.letsTravel.LetsTravel.domain.plan;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlanBasicInfoReadDTO {
	private int planSeq;
	private String planName;
	private String countryCode;
	private Date planStart;
	private int planNDays;
}
