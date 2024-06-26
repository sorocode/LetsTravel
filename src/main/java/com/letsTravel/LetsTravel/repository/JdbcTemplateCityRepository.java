package com.letsTravel.LetsTravel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
				"SELECT C.City_seq AS id, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name) AS cityName, IF(C.City_standard_seq IS NULL, C.City_name, CS.City_name_translated) AS cityNameTranslated "
						+ "FROM City C LEFT JOIN City_standard CS ON C.City_standard_seq = CS.City_standard_seq "
						+ "WHERE C.Country_code = ?;",
				new RowMapper<CityReadDTO>() {
					@Override
					public CityReadDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CityReadDTO cityReadDTO = new CityReadDTO();
						cityReadDTO.setId(rs.getInt(1));
						cityReadDTO.setCountryCode(countryCode);
						cityReadDTO.setCityName(rs.getString(2));
						cityReadDTO.setCityNameTranslated(rs.getString(3));
						return cityReadDTO;
					}
				}, countryCode);
	}

	@Override
	public int addCity(CityCreateDTO cityCreateDTO) {
		// NOT EXISTS VS IGNORE, 개선해야겠지?
		// 1. NOT EXISTS: 아직 City의 개수가 적어서 중복된 레코드를 넣을 때 압도적인 성능을 보여줌
		// 다만 새 레코드를 삽입할 때 SELECT 비용 + INSERT 비용까지 해서 가장 오래 걸림
		// 2. IGNORE: 대부분의 경우 0.01s > 새 Record insert > 중복 Record ignore
		// 결론: 이건 IGNORE 하는 게 맞는 듯? 아닌가
		String sql = "INSERT INTO City(Country_code, City_name) SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT City_seq FROM City WHERE City_name = ?);";
		return jdbcTemplate.update(sql, cityCreateDTO.getCountryCode(), cityCreateDTO.getCityName(),
				cityCreateDTO.getCityName());
	}

	@Override
	public int addPlaceCity(PlaceCityCreateDTO placeCityCreateDTO) {
		String sql = "INSERT IGNORE INTO Place_city VALUES(?, (SELECT City_seq FROM City WHERE Country_code = ? AND City_name = ?));";
		return jdbcTemplate.update(sql, placeCityCreateDTO.getPlaceSeq(), placeCityCreateDTO.getCity().getCountryCode(),
				placeCityCreateDTO.getCity().getCityName());
	}
}
