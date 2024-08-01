package com.letsTravel.LetsTravel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.CityReadDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;

@Repository
public class JdbcTemplateCityRepository implements CityRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateCityRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CityReadDTO> findCities(String countryCode) {
		return jdbcTemplate.query(
				"SELECT C.City_seq, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name) AS cityName, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS cityNameTranslated, C.Country_code "
						+ "FROM City C LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq " + "WHERE C.Country_code = ? " + "ORDER BY cityName, cityNameTranslated;",
				rs -> {
					return extractData(rs);
				}, countryCode);
	}

	@Override
	public List<CityReadDTO> findCitiesByKeyword(String keyword) {
		return jdbcTemplate.query(
				"SELECT C.City_seq, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name) AS cityName, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS cityNameTranslated, C.Country_code "
						+ "FROM City C LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq "
						+ "WHERE (C.City_standard_seq IS NULL AND C.City_name LIKE ?) OR (C.City_standard_seq IS NOT NULL AND C.City_standard_seq IN (SELECT C3.City_standard_seq FROM City C3 WHERE C3.City_name LIKE ?));",
				rs -> {
					return extractData(rs);
				}, "%" + keyword + "%", "%" + keyword + "%");
	}

	@Override
	public int addCity(CityCreateDTO cityCreateDTO) {
		// NOT EXISTS VS IGNORE, 개선해야겠지?
		// 1. NOT EXISTS: 아직 City의 개수가 적어서 중복된 레코드를 넣을 때 압도적인 성능을 보여줌
		// 다만 새 레코드를 삽입할 때 SELECT 비용 + INSERT 비용까지 해서 가장 오래 걸림
		// 2. IGNORE: 대부분의 경우 0.01s > 새 Record insert > 중복 Record ignore
		// 결론: 이건 IGNORE 하는 게 맞는 듯? 아닌가
		String sql = "INSERT INTO City(Country_code, Type_seq, City_name, City_name_language_code, Is_admin_checked) SELECT ?, (SELECT Type_seq FROM Type WHERE Type_name = ?), ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT City_seq FROM City WHERE City_name = ?);";
		return jdbcTemplate.update(sql, cityCreateDTO.getCountryCode(), cityCreateDTO.getType(), cityCreateDTO.getCityName(), cityCreateDTO.getCityNameLanguageCode(),
				cityCreateDTO.getCityNameLanguageCode().equals("ko") ? 1 : 0, cityCreateDTO.getCityName());
	}

	@Override
	public int addPlaceCity(PlaceCityCreateDTO placeCityCreateDTO) {
		String sql = "INSERT IGNORE INTO Place_city VALUES(?, (SELECT City_seq FROM City WHERE Country_code = ? AND City_name = ?));";
		return jdbcTemplate.update(sql, placeCityCreateDTO.getPlaceSeq(), placeCityCreateDTO.getCity().getCountryCode(), placeCityCreateDTO.getCity().getCityName());
	}

	@Override
	public List<CityReadDTO> findPlanCitiesByPlanSeq(int planSeq) {
		String sql = "SELECT C.City_seq, C.Country_code, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name) AS cityName, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS cityNameTranslated "
				+ "FROM Plan P, City C LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq, Plan_city PC "
				+ "WHERE P.Plan_seq = PC.Plan_seq AND PC.City_seq = C.City_seq AND P.Plan_seq = ?;";
		return jdbcTemplate.query(sql, rs -> {
			return extractData(rs);
		}, planSeq);
	}

	private List<CityReadDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<CityReadDTO> cityList = new ArrayList<CityReadDTO>();
		CityReadDTO city = null;

		while (rs.next()) {
			if (city == null) {
				city = new CityReadDTO(new ArrayList<Integer>(Arrays.asList(rs.getInt("C.City_seq"))), rs.getString("C.Country_code"), rs.getString("cityName"), rs.getString("cityNameTranslated"));
			}
			else {
				// 이름이 중복됨 -> 같은 도시임
				if (city.getCityName().equals(rs.getString("cityName")) && city.getCityNameTranslated().equals(rs.getString("cityNameTranslated"))) {
					city.getCitySeq().add(rs.getInt("C.City_seq"));
				}
				else {
					cityList.add(city);
					city = new CityReadDTO(new ArrayList<Integer>(Arrays.asList(rs.getInt("C.City_seq"))), rs.getString("C.Country_code"), rs.getString("cityName"),
							rs.getString("cityNameTranslated"));
				}
			}
		}
		// 혹시나 마지막 레코드를 안 넣고 끝나는 거 방지
		if (city != null) {
			cityList.add(city);
		}

		return cityList;
	}
}
