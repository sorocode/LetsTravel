package com.letsTravel.LetsTravel.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.PlanCreateDTO;
import com.letsTravel.LetsTravel.domain.TravelPlan;

@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplatePlanRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int addPlan(PlanCreateDTO planCreateDTO) {
		boolean isPlanNameNull = planCreateDTO.getPlanName() == null;
		String sql = "INSERT INTO Plan(Mem_seq, Country_code, Plan_start, Plan_ndays"
				+ (isPlanNameNull ? "" : ", Plan_name") + ") VALUES(?, ?, ?, ?" + (isPlanNameNull ? "" : ", ?") + ");";
		System.out.println(sql);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, planCreateDTO.getMemSeq());
				pstmt.setString(2, planCreateDTO.getCountryCode());
				pstmt.setDate(3, planCreateDTO.getPlanStart());
				pstmt.setInt(4, planCreateDTO.getPlanNDays());
				if (!isPlanNameNull)
					pstmt.setString(5, planCreateDTO.getPlanName());
				return pstmt;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
}
