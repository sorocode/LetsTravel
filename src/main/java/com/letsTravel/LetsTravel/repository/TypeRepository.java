package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.type.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeDetailDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeUpdateDTO;

public interface TypeRepository {

	public int modifyTypeNameTranslated(PrimaryTypeDetailDTO typeTranslateDTO);
	public int addPlaceType(PlaceTypeCreateDTO type);
	public int modifyPrimaryType(PrimaryTypeUpdateDTO primaryTypeUpdateDTO);
}
