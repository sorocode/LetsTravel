package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.MetropolisCityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;

public interface CityRepository {
	
	public Long addCity(CityCreateDTO cityCreateDTO, MetropolisCreateDTO metropolisCreateDTO);
	public int addMetropolisCity(MetropolisCityCreateDTO metropolisCityCreateDTO);
	public int addPlaceCity(PlaceCityCreateDTO placeCityCreateDTO);
}
