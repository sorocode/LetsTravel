package com.letsTravel.LetsTravel.domain.place;

import java.util.List;

import com.letsTravel.LetsTravel.domain.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceInfoDTO {
	private int placeSeq;
	private String placeId;
	private String displayName;
	private String countryCode;
	private List<String> city;
	private String formattedAddress;
	private Location location;
	private String primaryType;
	private String googleMapsUri;
}
