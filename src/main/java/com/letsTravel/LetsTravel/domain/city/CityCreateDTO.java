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
	
	private String type;
	private String cityName;
	private String cityNameLanguageCode;
}
