package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.TypeTranslateDTO;

public interface TypeRepository {

	public int modifyTypeNameTranslated(TypeTranslateDTO typeTranslateDTO);
	public int addPlaceType(PlaceTypeCreateDTO type);
}
