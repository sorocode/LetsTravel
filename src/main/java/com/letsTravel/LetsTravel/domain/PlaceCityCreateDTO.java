package com.letsTravel.LetsTravel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceCityCreateDTO {

	private int placeSeq;
	private CityCreateDTO city;
}
