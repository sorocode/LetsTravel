package com.letsTravel.LetsTravel.domain.place;

import java.util.List;

import com.letsTravel.LetsTravel.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Place {

	private int placeSeq;
	private String id;
	private List<String> types;
	private String formattedAddress;
	private List<AddressComponent> addressComponents;
	private String countryCode;
	private Location location;
	private String googleMapsUri;
	private DisplayName displayName;
	private DisplayName displayName2;
	private DisplayName primaryTypeDisplayName;
	private String primaryType;
	
	public Place(int placeSeq, String id, String formattedAddress, String countryCode, Location location, String googleMapsUri) {
		this.placeSeq = placeSeq;
		this.id = id;
		this.formattedAddress = formattedAddress;
		this.countryCode = countryCode;
		this.location = location;
		this.googleMapsUri = googleMapsUri;
	}
}
