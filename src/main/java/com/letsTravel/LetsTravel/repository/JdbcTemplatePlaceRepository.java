package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;

@Repository
public class JdbcTemplatePlaceRepository implements PlaceRepository{

	private final JdbcTemplate jdbcTemplate;
	
	public JdbcTemplatePlaceRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int addPlace(PlaceCreateDTO placeCreateDTO) {
		return 1;
	}

}
