package com.letsTravel.LetsTravel.domain.metropolis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetropolisCreateDTO {

	private String countryCode;
	private String type;
	private String metropolisName;
	private String metropolisNameLanguageCode;

	// PlaceService.createPlace의 코드가 더러워져서 추가 -- 2024.08.06 강봉수
	public MetropolisCreateDTO(String type, String metropolisName, String metropolisNameLanguageCode) {
		this.type = type;
		this.metropolisName = metropolisName;
		this.metropolisNameLanguageCode = metropolisNameLanguageCode;
	}
}
