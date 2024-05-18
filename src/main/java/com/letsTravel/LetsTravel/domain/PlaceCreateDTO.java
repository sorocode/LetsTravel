package com.letsTravel.LetsTravel.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceCreateDTO {
	
	private String placeId;
	private String placeName;
	private String placeFormattedAddress;
	private String cityName;
	private String cityLanguageCode;
	private String countryCode;
	private float placeLatitude;
	private float placeLongitude;
	private String placeGmapUri;
	private Date placeInsertDate;
}
