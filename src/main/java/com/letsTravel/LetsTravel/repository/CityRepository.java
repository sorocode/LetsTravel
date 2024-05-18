package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.CityDTO;

public interface CityRepository {

	public List<CityDTO> findCities(String countryCode);
	public int addCity(CityCreateDTO cityCreateDTO);
}
