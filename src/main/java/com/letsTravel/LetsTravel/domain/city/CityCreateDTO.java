package com.letsTravel.LetsTravel.domain.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityCreateDTO {

	private String countryCode;
	private String type;
	private String cityName;
	private String cityNameLanguageCode;

	// PlaceService.createPlace의 코드가 더러워져서 추가 -- 2024.08.06 강봉수
	public CityCreateDTO(String type, String cityName, String cityNameLanguageCode) {
		this.type = type;
		this.cityName = cityName;
		this.cityNameLanguageCode = cityNameLanguageCode;
	}
}
