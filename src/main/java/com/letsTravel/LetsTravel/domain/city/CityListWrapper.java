package com.letsTravel.LetsTravel.domain.city;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CityListWrapper {
	private List<CityReadDTO> city;
}
