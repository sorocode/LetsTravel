package com.letsTravel.LetsTravel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.place.GoogleMapsPlace;
import com.letsTravel.LetsTravel.domain.place.PlaceInfoDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceReadDTO;
import com.letsTravel.LetsTravel.domain.type.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.type.PrimaryTypeUpdateDTO;
import com.letsTravel.LetsTravel.repository.CityRepository;
import com.letsTravel.LetsTravel.repository.PlaceRepository;
import com.letsTravel.LetsTravel.repository.TypeRepository;

@Service
public class PlaceService {

	private final PlaceRepository placeRepository;
	private final CityRepository cityRepository;
	private final TypeRepository typeRepository;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, CityRepository cityRepository, TypeRepository typeRepository) {
		this.placeRepository = placeRepository;
		this.cityRepository = cityRepository;
		this.typeRepository = typeRepository;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<PlaceInfoDTO> createPlace(GoogleMapsPlace googleMapsPlace) {
		List<PlaceInfoDTO> placeInfoList = new ArrayList<>();

		// City 저장(없으면 저장, 있으면 패스)
		List<CityCreateDTO> cityCreateDTOList = googleMapsPlace.getCities();
		for (int cityIndex = 0; cityIndex < cityCreateDTOList.size(); cityIndex++)
			cityRepository.addCity(cityCreateDTOList.get(cityIndex));

		// Place 저장
		int placeSeq = placeRepository.addPlace(googleMapsPlace.getPlaceDetail());
		PlaceInfoDTO placeInfo = new PlaceInfoDTO();
		placeInfo.setPlaceSeq(placeSeq);
		placeInfo.setPlaceId(googleMapsPlace.getPlaceDetail().getId());
		placeInfo.setDisplayName(googleMapsPlace.getPlaceDetail().getDisplayName());
		placeInfo.setCountryCode(googleMapsPlace.getCities().get(0).getCountryCode());
		List<String> cityList = new ArrayList<String>();
		for (int i = 0; i < googleMapsPlace.getCities().size(); i++) {
			cityList.add(googleMapsPlace.getCities().get(i).getCityName());
		}
		placeInfo.setCity(cityList);
		placeInfo.setFormattedAddress(googleMapsPlace.getPlaceDetail().getFormattedAddress());
		placeInfo.setLocation(googleMapsPlace.getPlaceDetail().getLocation());
		placeInfo.setPrimaryType(googleMapsPlace.getPrimaryTypeDetail().getPrimaryTypeDisplayName());
		placeInfo.setGoogleMapsUri(googleMapsPlace.getPlaceDetail().getGoogleMapsUri());
		placeInfoList.add(placeInfo);

		// Place의 City 저장
		for (int cityIndex = 0; cityIndex < cityCreateDTOList.size(); cityIndex++)
			cityRepository.addPlaceCity(new PlaceCityCreateDTO(placeSeq, cityCreateDTOList.get(cityIndex)));

		// Place의 Type 저장
		List<String> types = googleMapsPlace.getTypes();
		for (int typeIndex = 0; typeIndex < types.size(); typeIndex++)
			typeRepository.addPlaceType(new PlaceTypeCreateDTO(placeSeq, types.get(typeIndex)));

		// Place의 Primary Type 설정
		typeRepository.modifyPrimaryType(
				new PrimaryTypeUpdateDTO(placeSeq, googleMapsPlace.getPrimaryTypeDetail().getPrimaryType()));

		// Type 번역
		typeRepository.modifyTypeNameTranslated(googleMapsPlace.getPrimaryTypeDetail());

		return placeInfoList;
	}

	public List<PlaceReadDTO> readPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword) {
		return placeRepository.findPlaces(countryCode, city, type, keyword);
	}

	public List<PlaceReadDTO> readPlaceByPlaceSeq(int placeSeq) {
		return placeRepository.findPlaceByPlaceSeq(placeSeq);
	}
}
