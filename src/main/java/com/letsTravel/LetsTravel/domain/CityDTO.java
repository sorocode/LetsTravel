package com.letsTravel.LetsTravel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CityDTO {

	private int id;
	private String cityName;
	private String cityNameTranslated;
}
