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
public class AddressComponent {
	private String longText;
	private String shortText;
	private List<String> types;
	private String languageCode;

	public AddressComponent(String longText, List<String> types) {
		this.longText = longText;
		this.types = types;
	}
}
