package com.letsTravel.LetsTravel.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.letsTravel.LetsTravel.domain.city.PlaceCityCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisCreateDTO;
import com.letsTravel.LetsTravel.domain.metropolis.MetropolisReadDTO;

@Repository
public class JdbcTemplateMetropolisRepository implements MetropolisRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateMetropolisRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// Metropolis_standard 기준으로 변경해야 함
	@Override
	public List<MetropolisReadDTO> findMetropolises(String keyword, List<String> countryCodeList) {
		StringBuilder sql = new StringBuilder("SELECT MS.* " + "FROM Metropolis_standard MS, Metropolis_alias MA " + "WHERE MS.Metropolis_standard_seq = MA.Metropolis_standard_seq ");
		List<String> sqlArgs = new ArrayList<String>();

		if (!ObjectUtils.isEmpty(keyword)) {
			sql.append("AND MA.Alias LIKE ?");
			sqlArgs.add(keyword + "%");
		}

		if (!ObjectUtils.isEmpty(countryCodeList)) {
			sql.append("AND MS.Country_code IN (");
			for (String countryCode : countryCodeList) {
				sql.append("?,");
				sqlArgs.add(countryCode);
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
		}
		sql.append("ORDER BY MS.Country_code, MS.Metropolis_name, MS.Metropolis_name_translated;");

		return jdbcTemplate.query(sql.toString(), metropolisRowMapper(), sqlArgs.toArray());
	}

	@Override
	public int addMetropolis(MetropolisCreateDTO metropolisCreateDTO) {
		String sql = "INSERT IGNORE INTO Metropolis(Country_code, Type_seq, Metropolis_name, Is_admin_checked) VALUES(?, (SELECT Type_seq FROM Type WHERE Type_name = ?), ?, ?);";
		return jdbcTemplate.update(sql, metropolisCreateDTO.getCountryCode(), metropolisCreateDTO.getType(), metropolisCreateDTO.getMetropolisName(),
				metropolisCreateDTO.getMetropolisNameLanguageCode().equals("ko") ? 1 : 0);
	}

	private RowMapper<MetropolisReadDTO> metropolisRowMapper() {
		return new RowMapper<MetropolisReadDTO>() {
			@Override
			public MetropolisReadDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MetropolisReadDTO metropolisReadDTO = new MetropolisReadDTO();
				metropolisReadDTO.setMetropolisStandardSeq(rs.getInt("MS.Metropolis_standard_seq"));
				metropolisReadDTO.setCountryCode(rs.getString("MS.Country_code"));
				metropolisReadDTO.setMetropolisName(rs.getString("MS.Metropolis_name"));
				metropolisReadDTO.setMetropolisNameTranslated(rs.getString("MS.Metropolis_standard_seq"));
				return null;
			}
		};

	}

//	private List<MetropolisReadDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
//		List<MetropolisReadDTO> metropolisList = new ArrayList<MetropolisReadDTO>();
//		MetropolisReadDTO metropolis = null;
//
//		while (rs.next()) {
//			if (metropolis == null) {
//				metropolis = new MetropolisReadDTO(new ArrayList<Integer>(Arrays.asList(rs.getInt("MS.Metropolis_standard_seq"))), rs.getString("MS.Country_code"), rs.getString("MS.Metropolis_name"), rs.getString("MS.Metropolis_name_translated"));
//			}
//			else {
//				// 이름이 중복됨 -> 같은 도시임
//				if (metropolis.getMetropolisName().equals(rs.getString("cityName")) && metropolis.getMetropolisNameTranslated().equals(rs.getString("cityNameTranslated"))) {
//					metropolis.getMetropolisSeq().add(rs.getInt("C.City_seq"));
//				}
//				else {
//					metropolisList.add(metropolis);
//					metropolis = new MetropolisReadDTO(new ArrayList<Integer>(Arrays.asList(rs.getInt("C.City_seq"))), rs.getString("C.Country_code"), rs.getString("cityName"),
//							rs.getString("cityNameTranslated"));
//				}
//			}
//		}
//		// 혹시나 마지막 레코드를 안 넣고 끝나는 거 방지
//		if (metropolis != null) {
//			metropolisList.add(metropolis);
//		}
//
//		return metropolisList;
//	}
}
