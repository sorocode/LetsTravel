package com.letsTravel.LetsTravel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CityCreateDTO {

	private String countryCode;
	private String cityName;
	private String cityLanguageCode;
}
