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
	private String CountryCode;
	private Location location;
	private String googleMapsUri;
	private DisplayName displayName;
	private DisplayName primaryTypeDisplayName;
	private String primaryType;
}
