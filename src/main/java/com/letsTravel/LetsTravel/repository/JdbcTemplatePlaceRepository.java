package com.letsTravel.LetsTravel.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.letsTravel.LetsTravel.domain.Location;
import com.letsTravel.LetsTravel.domain.place.AddressComponent;
import com.letsTravel.LetsTravel.domain.place.DisplayName;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceProcReturnDTO;
import com.letsTravel.LetsTravel.domain.place.PlaceWrapper;

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
	public PlaceProcReturnDTO addPlace(Place place) {
		// 이건 ON UPDATE DUPLICATE KEY가 맞음
		SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", place.getId()).addValue("in_name", place.getDisplayName().getText())
				.addValue("in_name_language_code", place.getDisplayName().getLanguageCode()).addValue("in_formatted_address", place.getFormattedAddress())
				.addValue("in_latitude", place.getLocation().getLatitude()).addValue("in_longitude", place.getLocation().getLongitude()).addValue("in_gmap_uri", place.getGoogleMapsUri());

		Map out = simpleJdbcCall.execute(in);

		PlaceProcReturnDTO placeProcReturnDTO = new PlaceProcReturnDTO();
		placeProcReturnDTO.setPlaceSeq((int) out.get("out_place_seq"));
		placeProcReturnDTO.setExisted((boolean) out.get("out_is_existed"));
		placeProcReturnDTO.setPlaceInsertDate((Date) out.get("out_insert_date"));
		return placeProcReturnDTO;
	}

	// 한 달 지난 거면 Places API 재호출해야 함
	// Paging을 위해 Place_seq만 반환하도록 변경 -- 2024.07.26(강봉수)
	@Override
	public List<Integer> findPlaces(String countryCode, List<Integer> city, List<Integer> type, String keyword, Integer page, Integer size, String sort) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT P.Place_seq " + "FROM Place P, Place_name PN, Place_city PC, Place_type PT, City C " + "WHERE P.Place_seq = PN.Place_seq "
				+ "AND P.Place_seq = PC.Place_seq " + "AND C.City_seq = PC.City_seq " + "AND P.Place_seq = PT.Place_seq ");
		List<String> sqlArgs = new ArrayList<>();

		if (!ObjectUtils.isEmpty(keyword)) {
			sql.append("AND PN.Display_name LIKE ? ");
			sqlArgs.add("%" + keyword + "%");
		}

		if (!ObjectUtils.isEmpty(countryCode)) {
			sql.append("AND C.Country_code = ? ");
			sqlArgs.add(countryCode);
		}

		if (!CollectionUtils.isEmpty(city)) {
			sql.append("AND PC.City_seq IN (");
			sql.append("SELECT C2.City_seq ");
			sql.append("FROM City C2 ");
			sql.append("WHERE (C2.City_standard_seq IS NULL AND C2.City_seq IN (");
			for (int cityIndex = 0; cityIndex < city.size(); cityIndex++) {
				sql.append(city.get(cityIndex));
				if (cityIndex == city.size() - 1) {
					sql.append(") ");
					break;
				}
				sql.append(", ");

			}
			sql.append(") OR (C2.City_standard_seq IS NOT NULL AND C2.City_standard_seq IN (");
			sql.append("SELECT C3.City_standard_seq ");
			sql.append("FROM City C3 ");
			sql.append("WHERE C3.City_seq IN (");
			for (int cityIndex = 0; cityIndex < city.size(); cityIndex++) {
				sql.append(city.get(cityIndex));
				if (cityIndex == city.size() - 1) {
					sql.append(") ");
					break;
				}
				sql.append(", ");

			}
			sql.append("))) ");
		}

		if (!CollectionUtils.isEmpty(type)) {
			sql.append("AND PT.Type_Seq IN (");
			for (int typeIndex = 0; typeIndex < type.size(); typeIndex++) {
				sql.append(type.get(typeIndex));
				if (typeIndex == type.size() - 1) {
					sql.append(") ");
					break;
				}
				sql.append(", ");
			}
		}

		// place 파라미터 삭제 -- 2024.07.26(강봉수)
		/*
		 * if (place != null && place.size() != 0) { sql.append("AND P.Place_Seq IN (");
		 * for (int placeIndex = 0; placeIndex < place.size(); placeIndex++) {
		 * sql.append(place.get(placeIndex)); if (placeIndex == place.size() - 1) {
		 * sql.append(") "); break; } sql.append(", "); } }
		 */

		if (size != null) {
			sql.append("LIMIT " + size);
			if (page != null) {
				sql.append(" OFFSET " + size * (page - 1));
			}
		}
		else {
			sql.append("LIMIT 10");
		}

		sql.append(";");

		return jdbcTemplate.query(sql.toString(), new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("P.Place_seq");
			}
		}, sqlArgs.toArray());
	}

	@Override
	public PlaceWrapper findPlaceByPlaceSeq(List<Integer> placeSeq) {
		if (CollectionUtils.isEmpty(placeSeq)) {
			return new PlaceWrapper(new ArrayList<Place>());
		}

		StringBuilder sql = new StringBuilder(
				"SELECT P.Place_seq, P.Place_id, T.Type_name, T.Type_name_translated, PT.Is_Primary_type, P.Place_formatted_address, C.Country_code, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS City_name, IF(C.City_standard_seq IS NULL, C.City_name_language_code, 'ko') AS City_name_language_code, C.Type_seq, P.Place_latitude, P.Place_longitude, P.Place_gmap_uri, PN.Display_name, PN.Display_name_language_code "
						+ "FROM Place P, Place_name PN, Place_city PC, City C LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq, Place_type PT, Type T "
						+ "WHERE P.Place_seq = PN.Place_seq " + "AND P.Place_seq = PC.Place_seq " + "AND C.City_seq = PC.City_seq " + "AND P.Place_seq = PT.Place_seq "
						+ "AND T.Type_seq = PT.Type_seq " + "AND P.Place_seq IN (");
		for (int placeIndex = 0; placeIndex < placeSeq.size(); placeIndex++) {
			sql.append(placeSeq.get(placeIndex));
			if (placeIndex == placeSeq.size() - 1) {
				sql.append(") ");
				break;
			}
			sql.append(", ");
		}

		return jdbcTemplate.query(sql.toString(), rs -> {
			return extractData(rs);
		});
	}

	private PlaceWrapper extractData(ResultSet rs) throws SQLException, DataAccessException {
		PlaceWrapper placeWrapper = new PlaceWrapper();
		List<Place> placeList = new ArrayList<Place>();
		Set<String> typeSet = new LinkedHashSet<String>();
		Set<AddressComponent> citySet = new LinkedHashSet<AddressComponent>();
		Place place = null;
		DisplayName displayName1 = null;
		DisplayName displayName2 = null;
		DisplayName primaryTypeDisplayName = null;

		// 어떻게 개선하지
		while (rs.next()) {
			if (place == null) {
				place = new Place(rs.getInt("P.Place_seq"), rs.getString("P.Place_id"), rs.getString("P.Place_formatted_address"), rs.getString("C.Country_code"),
						new Location(rs.getFloat("P.Place_latitude"), rs.getFloat("P.Place_longitude")), rs.getString("P.Place_gmap_uri"));
			}
			else {
				if (place.getPlaceSeq() != rs.getInt("P.Place_seq")) {
					place.setTypes(List.copyOf(typeSet));
					typeSet.clear();
					place.setAddressComponents(List.copyOf(citySet));
					citySet.clear();
					displayName1 = null;
					displayName2 = null;
					primaryTypeDisplayName = null;
					placeList.add(place);
					place = new Place(rs.getInt("P.Place_seq"), rs.getString("P.Place_id"), rs.getString("P.Place_formatted_address"), rs.getString("C.Country_code"),
							new Location(rs.getFloat("P.Place_latitude"), rs.getFloat("P.Place_longitude")), rs.getString("P.Place_gmap_uri"));
				}
			}

			typeSet.add(rs.getString("T.Type_name"));
			AddressComponent addressComponent = new AddressComponent();
			addressComponent.setLongText(rs.getString("City_name"));
			addressComponent.setLanguageCode(rs.getString("City_name_language_code"));
			addressComponent.setTypes(Arrays.asList(rs.getInt("C.Type_seq") == 1 ? "administrative_area_level_1" : "administrative_area_level_2"));
			citySet.add(addressComponent);

			// displayName1이 없을 때
			if (displayName1 == null) {
				displayName1 = new DisplayName(rs.getString("PN.Display_name"), rs.getString("PN.Display_name_language_code"));
				place.setDisplayName(displayName1);
			}
			// displayName1이 있고 displayName2가 없을 때
			else if (displayName2 == null && rs.getString("PN.Display_name").equals(displayName1.getText()) == false) {
				displayName2 = new DisplayName(rs.getString("PN.Display_name"), rs.getString("PN.Display_name_language_code"));
				place.setDisplayName2(displayName2);
			}

			// primaryType 설정
			if (primaryTypeDisplayName == null && rs.getInt("PT.Is_primary_type") == 1) {
				place.setPrimaryType(rs.getString("T.Type_name"));
				primaryTypeDisplayName = new DisplayName();
				// 미래에는 Type의 번역이 전부 되어 있겠지만 안 되어 있을 근미래를 위해
				if (rs.getString("T.Type_name_translated") != null) {
					primaryTypeDisplayName.setText(rs.getString("T.Type_name_translated"));
					primaryTypeDisplayName.setLanguageCode("ko");
					place.setPrimaryTypeDisplayName(primaryTypeDisplayName);
				}
			}
		}

		place.setTypes(List.copyOf(typeSet));
		place.setAddressComponents(List.copyOf(citySet));
		placeList.add(place);
		placeWrapper.setPlaces(placeList);
		return placeWrapper;
	}
}
