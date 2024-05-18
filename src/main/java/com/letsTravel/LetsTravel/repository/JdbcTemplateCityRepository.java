package com.letsTravel.LetsTravel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.CityDTO;

@Repository
public class JdbcTemplateCityRepository implements CityRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateCityRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CityDTO> findCities(String countryCode) {
		return jdbcTemplate.query(
				"SELECT C.City_seq, C.City_name, C.City_name_translated FROM CITY C WHERE C.Country_code = ?",
				new RowMapper<CityDTO>() {
					@Override
					public CityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CityDTO cityDTO = new CityDTO();
						cityDTO.setId(rs.getInt(1));
						cityDTO.setCountryCode(countryCode);
						cityDTO.setCityName(rs.getString(2));
						cityDTO.setCityNameTranslated(rs.getString(3));
						return cityDTO;
					}
				}, countryCode);
	}

	@Override
	public int addCity(CityCreateDTO cityCreateDTO) {
		String sql = "INSERT INTO City(Country_code, City_name"
				+ (cityCreateDTO.getCityLanguageCode().equals("ko") ? "_translated" : "")
				+ ") SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT City_seq FROM City WHERE City_name"
				+ (cityCreateDTO.getCityLanguageCode().equals("ko") ? "_translated" : "") + " = ?);";
		return jdbcTemplate.update(sql, cityCreateDTO.getCountryCode(), cityCreateDTO.getCityName(), cityCreateDTO.getCityName());
	}
}
