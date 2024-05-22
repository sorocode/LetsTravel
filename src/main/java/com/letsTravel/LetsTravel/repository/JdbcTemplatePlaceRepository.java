package com.letsTravel.LetsTravel.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;

@Repository
public class JdbcTemplatePlaceRepository implements PlaceRepository {

	private final SimpleJdbcCall simpleJdbcCall;

	// Stored Procedure를 쓰는 게 맞나?
	public JdbcTemplatePlaceRepository(DataSource dataSource) {
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("add_Place_Proc");
	}

	@Override
	public int addPlace(PlaceCreateDTO placeCreateDTO) {
		// 이건 ON UPDATE DUPLICATE KEY가 맞음
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("in_id", placeCreateDTO.getId())
				.addValue("in_name", placeCreateDTO.getDisplayName())
				.addValue("in_name_language_code", placeCreateDTO.getLanguageCode())
				.addValue("in_formatted_address", placeCreateDTO.getFormattedAddress())
				.addValue("in_latitude", placeCreateDTO.getLocation().getLatitude())
				.addValue("in_longitude", placeCreateDTO.getLocation().getLongitude())
				.addValue("in_gmap_uri", placeCreateDTO.getGoogleMapsUri());
		
		Map out = simpleJdbcCall.execute(in);
		return (int)out.get("out_place_seq");
	}

}
