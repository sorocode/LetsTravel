package com.letsTravel.LetsTravel.domain.metropolis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetropolisReadDTO {

	private Integer metropolisStandardSeq;
	private String countryCode;
	private String metropolisName;
	private String metropolisNameTranslated;
}