package com.letsTravel.LetsTravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisReadDTO;
import com.letsTravel.LetsTravel.repository.MetropolisRepository;

@Service
public class MetropolisService {

	private final MetropolisRepository metropolisRepository;
	
	@Autowired
	public MetropolisService(MetropolisRepository metropolisRepository) {
		this.metropolisRepository = metropolisRepository;
	}

	public List<MetropolisReadDTO> findMetropolises(String keyword, List<String> countryCodeList) {
		return metropolisRepository.findMetropolises(keyword, countryCodeList);
	}

	public int addCity(MetropolisCreateDTO metropolisCreateDTO) {
		return metropolisRepository.addMetropolis(metropolisCreateDTO);
	}
}
