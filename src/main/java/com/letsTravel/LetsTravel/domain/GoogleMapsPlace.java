package com.letsTravel.LetsTravel.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleMapsPlace {
	
	private PlaceCreateDTO placeDetail;
	private List<CityCreateDTO> cities;
	private List<String> types;
	private String primaryTypeDisplayName;
	private String primaryType;
}
