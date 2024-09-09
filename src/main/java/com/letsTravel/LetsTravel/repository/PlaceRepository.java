package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceProcReturnDTO;

public interface PlaceRepository {

	// 뭘 반환할까
	public PlaceProcReturnDTO addPlace(Place place);
	public List<Long> findPlaces(List<Integer> city, List<Integer> type, String keyword, Integer page, Integer size, String sort);
	public List<Place> findPlaceByPlaceSeq(List<Long> placeSeqList);
	public List<Place> findPlaceByPlaceId(List<String> placeIdList);
}
