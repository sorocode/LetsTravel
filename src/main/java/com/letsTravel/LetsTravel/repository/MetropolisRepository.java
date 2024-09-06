package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisReadDTO;

public interface MetropolisRepository {

	public List<MetropolisReadDTO> findMetropolises(String keyword, List<String> countryCodeList);
	public int addMetropolis(MetropolisCreateDTO cityCreateDTO);
}
