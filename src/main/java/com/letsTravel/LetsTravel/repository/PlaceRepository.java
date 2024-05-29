package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;
import com.letsTravel.LetsTravel.domain.PlaceReadDTO;

public interface PlaceRepository {

	// 뭘 반환할까
	public int addPlace(PlaceCreateDTO placeCreateDTO);
	public List<PlaceReadDTO> findPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword);
	public List<PlaceReadDTO> findPlaceByPlaceSeq(int placeSeq);
}
