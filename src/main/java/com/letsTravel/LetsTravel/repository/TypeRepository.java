package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.PrimaryTypeUpdateDTO;
import com.letsTravel.LetsTravel.domain.PrimaryTypeDetailDTO;

public interface TypeRepository {

	public int modifyTypeNameTranslated(PrimaryTypeDetailDTO typeTranslateDTO);
	public int addPlaceType(PlaceTypeCreateDTO type);
	public int modifyPrimaryType(PrimaryTypeUpdateDTO primaryTypeUpdateDTO);
}
