package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;

public interface PlaceRepository {

	// 뭘 반환할까
	public int addPlace(PlaceCreateDTO placeCreateDTO);
}
