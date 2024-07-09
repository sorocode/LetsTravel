package com.letsTravel.LetsTravel.domain.city;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CityReadDTO {

	private int citySeq;
	private String countryCode;
	private String cityName;
	private String cityNameTranslated;
}