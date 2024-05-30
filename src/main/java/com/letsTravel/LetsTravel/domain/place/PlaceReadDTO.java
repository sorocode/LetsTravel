package com.letsTravel.LetsTravel.domain.place;

import com.letsTravel.LetsTravel.domain.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceReadDTO {

	private int id;
	private String displayName;
	private String formattedAddress;
	private Location location;
	private String googleMapsUri;
}
