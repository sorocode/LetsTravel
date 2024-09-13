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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.letsTravel.LetsTravel.domain.place.AddressComponent;
import com.letsTravel.LetsTravel.domain.place.DisplayName;
import com.letsTravel.LetsTravel.domain.place.Location;
import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.place.PlaceProcReturnDTO;

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
		placeProcReturnDTO.setPlaceSeq((Long) out.get("out_place_seq"));
		placeProcReturnDTO.setExisted((boolean) out.get("out_is_existed"));
		placeProcReturnDTO.setPlaceInsertDate((Date) out.get("out_insert_date"));
		return placeProcReturnDTO;
	}

	// 한 달 지난 거면 Places API 재호출해야 함
	// Paging을 위해 Place_seq만 반환하도록 변경 -- 2024.07.26(강봉수)
	@Override
	public List<Long> findPlaces(List<Integer> city, List<Integer> type, String keyword, Integer page, Integer size, String sort) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT P.Place_seq " + "FROM Place P, Place_name PN, Place_type PT, Place_city PC "
				+ "WHERE P.Place_seq = PN.Place_seq AND P.Place_seq = PT.Place_seq AND P.Place_seq = PC.Place_Seq ");
		List<String> sqlArgs = new ArrayList<>();

		if (!ObjectUtils.isEmpty(keyword)) {
			sql.append("AND PN.Display_name LIKE ? ");
			sqlArgs.add("%" + keyword + "%");
		}

		if (!CollectionUtils.isEmpty(city)) {
			sql.append("AND PC.City_seq IN (");
			for (int citySeq : city) {
				sql.append(citySeq + ",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
		}

		if (!CollectionUtils.isEmpty(type)) {
			sql.append("AND PT.Type_Seq IN (");
			for (int typeSeq : type) {
				sql.append(typeSeq + ",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
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
		System.out.println(sql.toString());
		return jdbcTemplate.query(sql.toString(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("P.Place_seq");
			}
		}, sqlArgs.toArray());
	}

	@Override
	public List<Place> findPlaceByPlaceSeq(List<Long> placeSeqList) {
		if (CollectionUtils.isEmpty(placeSeqList)) {
			return new ArrayList<Place>();
		}

		StringBuilder sql = new StringBuilder(
				"SELECT \r\n"
				+ "    P.Place_seq, \r\n"
				+ "    P.Place_id, \r\n"
				+ "    T.Type_name, \r\n"
				+ "    T.Type_name_translated, \r\n"
				+ "    PT.Is_Primary_type, \r\n"
				+ "    P.Place_formatted_address, \r\n"
				+ "    M.Country_code, \r\n"
				+ "    IF(M.Metropolis_standard_seq IS NULL, M.Metropolis_name, MS.Metropolis_name_translated) AS Metropolis_name,\r\n"
				+ "    IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS City_name, \r\n"
				+ "    (SELECT Type.Type_name FROM Type WHERE M.Type_seq = Type.Type_seq) AS Metropolis_type,\r\n"
				+ "    (SELECT Type.Type_name FROM Type WHERE C.Type_seq = Type.Type_seq) AS City_type,\r\n"
				+ "    P.Place_latitude, \r\n"
				+ "    P.Place_longitude, \r\n"
				+ "    P.Place_gmap_uri, \r\n"
				+ "    PN.Display_name, \r\n"
				+ "    PN.Display_name_language_code\r\n"
				+ "FROM \r\n"
				+ "    Place P \r\n"
				+ "    LEFT JOIN Place_name PN ON P.Place_seq = PN.Place_seq\r\n"
				+ "    JOIN Place_city PC ON P.Place_seq = PC.Place_seq\r\n"
				+ "    JOIN City C ON C.City_seq = PC.City_seq \r\n"
				+ "    LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq\r\n"
				+ "    JOIN Place_type PT ON P.Place_seq = PT.Place_seq\r\n"
				+ "    JOIN Type T ON T.Type_seq = PT.Type_seq, \r\n"
				+ "    Metropolis_city MC\r\n"
				+ "    JOIN Metropolis M ON MC.Metropolis_seq = M.Metropolis_seq\r\n"
				+ "    LEFT JOIN Metropolis_standard MS ON M.Metropolis_standard_seq = MS.Metropolis_standard_seq\r\n"
				+ "WHERE \r\n"
				+ "    C.City_seq = MC.City_seq " + "AND P.Place_seq IN (");
		for (long placeSeq : placeSeqList) {
			sql.append(placeSeq);
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") ");

		// Place_seq 정렬이 안되어 extractData에서 하나의 place_seq에 대해 여러 Place 인스턴스가 발생하는 버그 수정
		// -- 2024.08.09
		sql.append("ORDER BY P.Place_seq ");

		return jdbcTemplate.query(sql.toString(), rs -> {
			return extractData(rs);
		});
	}

	@Override
	public List<Place> findPlaceByPlaceId(List<String> placeIdList) {
		if (CollectionUtils.isEmpty(placeIdList)) {
			return new ArrayList<Place>();
		}

		// City의 type을 type_seq로 받던 것을 type_name으로 받도록 쿼리 개선 -- 2024.08.06 강봉수
		StringBuilder sql = new StringBuilder(
				"SELECT \r\n"
						+ "    P.Place_seq, \r\n"
						+ "    P.Place_id, \r\n"
						+ "    T.Type_name, \r\n"
						+ "    T.Type_name_translated, \r\n"
						+ "    PT.Is_Primary_type, \r\n"
						+ "    P.Place_formatted_address, \r\n"
						+ "    M.Country_code, \r\n"
						+ "    IF(M.Metropolis_standard_seq IS NULL, M.Metropolis_name, MS.Metropolis_name_translated) AS Metropolis_name,\r\n"
						+ "    IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS City_name, \r\n"
						+ "    (SELECT Type.Type_name FROM Type WHERE M.Type_seq = Type.Type_seq) AS Metropolis_type,\r\n"
						+ "    (SELECT Type.Type_name FROM Type WHERE C.Type_seq = Type.Type_seq) AS City_type,\r\n"
						+ "    P.Place_latitude, \r\n"
						+ "    P.Place_longitude, \r\n"
						+ "    P.Place_gmap_uri, \r\n"
						+ "    PN.Display_name, \r\n"
						+ "    PN.Display_name_language_code\r\n"
						+ "FROM \r\n"
						+ "    Place P \r\n"
						+ "    LEFT JOIN Place_name PN ON P.Place_seq = PN.Place_seq\r\n"
						+ "    JOIN Place_city PC ON P.Place_seq = PC.Place_seq\r\n"
						+ "    JOIN City C ON C.City_seq = PC.City_seq \r\n"
						+ "    LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq\r\n"
						+ "    JOIN Place_type PT ON P.Place_seq = PT.Place_seq\r\n"
						+ "    JOIN Type T ON T.Type_seq = PT.Type_seq, \r\n"
						+ "    Metropolis_city MC\r\n"
						+ "    JOIN Metropolis M ON MC.Metropolis_seq = M.Metropolis_seq\r\n"
						+ "    LEFT JOIN Metropolis_standard MS ON M.Metropolis_standard_seq = MS.Metropolis_standard_seq\r\n"
						+ "WHERE \r\n"
						+ "    C.City_seq = MC.City_seq " + "AND P.Place_id IN (");
		for (String placeId: placeIdList) {
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") ");

		// Place_seq 정렬이 안되어 extractData에서 하나의 place_seq에 대해 여러 Place 인스턴스가 발생하는 버그 수정
		// -- 2024.08.09
		sql.append("ORDER BY P.Place_seq ");

		return jdbcTemplate.query(sql.toString(), rs -> {
			return extractData(rs);
		}, placeIdList.toArray());
	}

	private List<Place> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Place> placeList = new ArrayList<Place>();
		Set<String> typeSet = new LinkedHashSet<String>();
		Set<AddressComponent> addrComponentSet = new LinkedHashSet<AddressComponent>();
		Place place = null;
		DisplayName displayName1 = null;
		DisplayName displayName2 = null;
		DisplayName primaryTypeDisplayName = null;

		// 어떻게 개선하지
		while (rs.next()) {
			if (place == null) {
				place = new Place(rs.getInt("P.Place_seq"), rs.getString("P.Place_id"), rs.getString("P.Place_formatted_address"), rs.getString("M.Country_code"),
						new Location(rs.getFloat("P.Place_latitude"), rs.getFloat("P.Place_longitude")), rs.getString("P.Place_gmap_uri"));
				AddressComponent addressComponent = new AddressComponent(rs.getString("City_name"), Arrays.asList(rs.getString("City_type")));
				addrComponentSet.add(addressComponent);
			}
			else {
				if (place.getPlaceSeq() != rs.getInt("P.Place_seq")) {
					place.setTypes(List.copyOf(typeSet));
					typeSet.clear();
					place.setAddressComponents(List.copyOf(addrComponentSet));
					addrComponentSet.clear();
					displayName1 = null;
					displayName2 = null;
					primaryTypeDisplayName = null;
					placeList.add(place);
					place = new Place(rs.getInt("P.Place_seq"), rs.getString("P.Place_id"), rs.getString("P.Place_formatted_address"), rs.getString("M.Country_code"),
							new Location(rs.getFloat("P.Place_latitude"), rs.getFloat("P.Place_longitude")), rs.getString("P.Place_gmap_uri"));
					AddressComponent addressComponent = new AddressComponent(rs.getString("City_name"), Arrays.asList(rs.getString("City_type")));
					addrComponentSet.add(addressComponent);
				}
			}

			typeSet.add(rs.getString("T.Type_name"));
			AddressComponent addressComponent = new AddressComponent(rs.getString("Metropolis_name"), Arrays.asList(rs.getString("Metropolis_type")));
			addrComponentSet.add(addressComponent);

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
		place.setAddressComponents(List.copyOf(addrComponentSet));
		placeList.add(place);
		return placeList;
	}
}
