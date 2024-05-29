package com.letsTravel.LetsTravel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.Location;
import com.letsTravel.LetsTravel.domain.PlaceCreateDTO;
import com.letsTravel.LetsTravel.domain.PlaceReadDTO;

@Repository
public class JdbcTemplatePlaceRepository implements PlaceRepository {

	private final SimpleJdbcCall simpleJdbcCall;
	private final JdbcTemplate jdbcTemplate;

	// Stored Procedure를 쓰는 게 맞나?
	public JdbcTemplatePlaceRepository(DataSource dataSource) {
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("add_Place_Proc");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int addPlace(PlaceCreateDTO placeCreateDTO) {
		// 이건 ON UPDATE DUPLICATE KEY가 맞음
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", placeCreateDTO.getId())
				.addValue("in_name", placeCreateDTO.getDisplayName())
				.addValue("in_name_language_code", placeCreateDTO.getLanguageCode())
				.addValue("in_formatted_address", placeCreateDTO.getFormattedAddress())
				.addValue("in_latitude", placeCreateDTO.getLocation().getLatitude())
				.addValue("in_longitude", placeCreateDTO.getLocation().getLongitude())
				.addValue("in_gmap_uri", placeCreateDTO.getGoogleMapsUri());

		Map out = simpleJdbcCall.execute(in);
		return (int) out.get("out_place_seq");
	}

	// 한 달 지난 거면 Places API 재호출해야 함
	@Override
	public List<PlaceReadDTO> findPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword) {
		StringBuilder sql = new StringBuilder(
				"SELECT P.Place_seq, PN.Display_name AS Place_name, P.Place_formatted_address, P.Place_latitude, P.Place_longitude, P.Place_gmap_uri FROM Place P, Place_name PN ");
		List<String> sqlArgs = new ArrayList<>();

		if (countryCode != null || city != null) {
			sql.append(", Place_city PC, City C ");
		}

		if (type != null) {
			sql.append(", Place_type PT, Type T ");
		}

		sql.append("WHERE P.Place_seq = PN.Place_seq ");

		if (countryCode != null || city != null) {
			sql.append("AND P.Place_seq = PC.Place_seq " + "AND C.City_seq = PC.City_seq ");
		}

		if (type != null) {
			sql.append("AND P.Place_seq = PT.Place_seq " + "AND T.Type_seq = PT.Type_seq ");
		}

		if (keyword != null) {
			sql.append("AND PN.Display_name LIKE ? ");
			sqlArgs.add("%" + keyword + "%");
		}

		if (countryCode != null) {
			sql.append("AND C.Country_code = ? ");
			sqlArgs.add(countryCode);
		}

		if (city != null) {
			sql.append("AND PC.City_seq IN (");
			for (int cityIndex = 0; cityIndex < city.size(); cityIndex++) {
				sql.append(city.get(cityIndex));
				if (cityIndex == city.size() - 1) {
					sql.append(");");
					break;
				}
				sql.append(", ");
			}
		}

		if (type != null) {
			sql.append("AND PT.Type_Seq IN (");
			for (int typeIndex = 0; typeIndex < type.size(); typeIndex++) {
				sql.append(type.get(typeIndex));
				if (typeIndex == type.size() - 1) {
					sql.append(");");
					break;
				}
				sql.append(", ");
			}
		}

		return jdbcTemplate.query(sql.toString(), new RowMapper<PlaceReadDTO>() {
			@Override
			public PlaceReadDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlaceReadDTO placeReadDTO = new PlaceReadDTO();
				placeReadDTO.setId(rs.getInt(1));
				placeReadDTO.setDisplayName(rs.getString(2));
				placeReadDTO.setFormattedAddress(rs.getString(3));
				placeReadDTO.setLocation(new Location(rs.getFloat(4), rs.getFloat(5)));
				placeReadDTO.setGoogleMapsUri(rs.getString(6));
				return placeReadDTO;
			}
		}, sqlArgs.toArray());
	}

}
