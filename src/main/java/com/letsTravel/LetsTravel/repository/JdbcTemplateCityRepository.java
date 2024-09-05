package com.letsTravel.LetsTravel.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.city.CityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.MetropolisCityCreateDTO;
import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;

@Repository
public class JdbcTemplateCityRepository implements CityRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateCityRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long addCity(CityCreateDTO cityCreateDTO, MetropolisCreateDTO metropolisCreateDTO) {
		String sql = "INSERT INTO City(Type_seq, City_name, Is_admin_checked) SELECT (SELECT Type_seq FROM Type WHERE Type_name = ?), ?, "
				+ (cityCreateDTO.getCityNameLanguageCode().equals("ko") ? 1 : 0)
				+ " FROM DUAL WHERE NOT EXISTS(SELECT C.City_seq FROM City C, Metropolis_city MC, Metropolis M WHERE C.City_seq = MC.City_seq AND M.Metropolis_seq = MC.Metropolis_seq AND M.Country_code = ? AND M.Metropolis_name = ? AND C.City_name = ?);";
		List<String> sqlArgs = new ArrayList<String>(Arrays.asList(cityCreateDTO.getType(), cityCreateDTO.getCityName(), metropolisCreateDTO.getCountryCode()));
		sqlArgs.add(metropolisCreateDTO.getMetropolisName());
		sqlArgs.add(cityCreateDTO.getCityName());

		System.out.println(metropolisCreateDTO);
		System.out.println(sqlArgs);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int argIndex = 0; argIndex < sqlArgs.size(); argIndex++) {
					pstmt.setString(argIndex + 1, sqlArgs.get(argIndex));
				}
				return pstmt;
			}
		}, keyHolder);

		if (keyHolder.getKey() == null) {
			String sql2 = "SELECT C.City_seq FROM City C, Metropolis_city MC, Metropolis M WHERE C.City_seq = MC.City_seq AND M.Metropolis_seq = MC.Metropolis_seq AND M.Country_code = ? AND M.Metropolis_name = ? AND C.City_name = ?";
			return jdbcTemplate.queryForObject(sql2, Long.class, metropolisCreateDTO.getCountryCode(), metropolisCreateDTO.getMetropolisName(), cityCreateDTO.getCityName());
		}

		return keyHolder.getKey().longValue();
	}

	@Override
	public int addMetropolisCity(MetropolisCityCreateDTO metropolisCityCreateDTO) {
		String sql = "INSERT IGNORE INTO Metropolis_city VALUES((SELECT Metropolis_seq FROM Metropolis WHERE Country_code = ? AND Metropolis_name = ?), ?)";
		return jdbcTemplate.update(sql, metropolisCityCreateDTO.getMetropolisCreateDTO().getCountryCode(), metropolisCityCreateDTO.getMetropolisCreateDTO().getMetropolisName(),
				metropolisCityCreateDTO.getCitySeq());
	}

	@Override
	public int addPlaceCity(PlaceCityCreateDTO placeCityCreateDTO) {
		String sql = "INSERT IGNORE INTO Place_city VALUES(?, ?)";
		return jdbcTemplate.update(sql, placeCityCreateDTO.getPlaceSeq(), placeCityCreateDTO.getCitySeq());
	}

}
