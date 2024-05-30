package com.letsTravel.LetsTravel.domain.place;

import java.util.List;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeDetailDTO;

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
	private PrimaryTypeDetailDTO primaryTypeDetail;
}
