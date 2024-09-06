package com.letsTravel.LetsTravel.domain.city;

import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MetropolisCityCreateDTO {

	private MetropolisCreateDTO metropolisCreateDTO;
	private long citySeq;
}
