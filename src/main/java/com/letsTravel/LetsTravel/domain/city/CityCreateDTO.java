package com.letsTravel.LetsTravel.domain.city;

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
}
