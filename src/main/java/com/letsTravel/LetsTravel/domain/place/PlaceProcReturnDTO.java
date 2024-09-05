package com.letsTravel.LetsTravel.domain.place;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceProcReturnDTO {

	Long placeSeq;
	boolean isExisted;
	Date placeInsertDate;
}
