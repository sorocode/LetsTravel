package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.CityReadDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;

public interface CityRepository {

	public List<CityReadDTO> findCities(String countryCode);
	public int addCity(CityCreateDTO cityCreateDTO);
	public int addPlaceCity(PlaceCityCreateDTO placeCityCreateDTO);
}
