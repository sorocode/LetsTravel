package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.GoogleMapsPlace;
import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;

@Repository
public class JdbcTemplatePlaceRepository implements PlaceRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplatePlaceRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int addPlace(PlaceCreateDTO placeCreateDTO) {
		String sql = "INSERT INTO Place(Place_id, Place_name, Place_formatted_address, Place_latitude, Place_longitude, Place_gmap_uri, Place_insert_date) "
				+ "VALUES(?, ?, ?, ?, ?, ?, curdate()) " + "ON DUPLICATE KEY UPDATE Place_insert_date = curdate();";
		return jdbcTemplate.update(sql, placeCreateDTO.getId(), placeCreateDTO.getDisplayName(),
				placeCreateDTO.getFormattedAddress(), placeCreateDTO.getLocation().getLatitude(),
				placeCreateDTO.getLocation().getLongitude(), placeCreateDTO.getGoogleMapsUri());

	}

}
