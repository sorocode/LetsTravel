package com.letsTravel.LetsTravel.domain.place;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Place {

	private long placeSeq;
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

	public Place(int placeSeq) {
		this.placeSeq = placeSeq;
	}

	public Place(Location location) {
		this.location = location;
	}

	public String toString() {
		return displayName.getText();
	}

	public String getCityName() {
		String cityName = null;
		for (AddressComponent addressComponent : this.addressComponents) {
			if (addressComponent.getTypes().get(0).equals("locality") || addressComponent.getTypes().get(0).equals("sublocality_level_1")) {
				cityName = addressComponent.getLongText();
				break;
			}
		}
		return cityName;
	}
}
