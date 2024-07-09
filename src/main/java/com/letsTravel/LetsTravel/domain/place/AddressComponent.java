package com.letsTravel.LetsTravel.domain.place;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressComponent {
	private String longText;
    private String shortText;
    private List<String> types;
    private String languageCode;
}
