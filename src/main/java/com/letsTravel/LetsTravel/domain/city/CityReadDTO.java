package com.letsTravel.LetsTravel.domain.city;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityReadDTO {

	private List<Integer> citySeq;
	private String countryCode;
	private String cityName;
	private String cityNameTranslated;
}