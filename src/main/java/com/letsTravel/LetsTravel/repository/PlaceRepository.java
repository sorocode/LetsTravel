package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceWrapper;

public interface PlaceRepository {

	// 뭘 반환할까
	public int addPlace(Place place);
	public List<Integer> findPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword, Integer page, Integer size, String sort);
	public PlaceWrapper findPlaceByPlaceSeq(List<Integer> placeSeq);
}
