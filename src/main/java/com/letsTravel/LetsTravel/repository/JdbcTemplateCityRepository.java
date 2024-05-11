package com.letsTravel.LetsTravel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.CityDTO;

@Repository
public class JdbcTemplateCityRepository implements CityRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateCityRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CityDTO> findCities(String countryCode) {
		return jdbcTemplate.query("SELECT C.City_seq, C.City_name_translated FROM CITY C WHERE C.Country_code = ?", 
				new RowMapper<CityDTO>() {
			@Override
			public CityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CityDTO cityDTO = new CityDTO();
				cityDTO.setCitySeq(rs.getInt(1));
				cityDTO.setCityName(rs.getString(2));
				return cityDTO;
			}
		},
		countryCode);
	}
}
