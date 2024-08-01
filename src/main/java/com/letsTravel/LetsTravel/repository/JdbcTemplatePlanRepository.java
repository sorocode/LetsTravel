package com.letsTravel.LetsTravel.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.plan.PlanInfoDTO;

@Repository
public class JdbcTemplatePlanRepository implements PlanRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplatePlanRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<PlanInfoDTO> findPlanByMemberSeq(int memberSeq) {
		String sql = "SELECT Plan_seq, Plan_name, Country_code, Plan_start, Plan_ndays FROM Plan WHERE Mem_seq = ?";
		return jdbcTemplate.query(sql, planInfoRowMapper(), memberSeq);
	}

	@Override
	public PlanInfoDTO findPlanByPlanSeq(int planSeq) {
		String sql = "SELECT Plan_seq, Mem_seq, Plan_name, Country_code, Plan_start, Plan_ndays FROM Plan WHERE Plan_seq = ?";
		return jdbcTemplate.queryForObject(sql, planInfoRowMapper(), planSeq);
	}

	@Override
	public int addPlan(PlanInfoDTO planInfoDTO) {
		boolean isPlanNameNull = planInfoDTO.getPlanName() == null;
		String sql = "INSERT INTO Plan(Mem_seq, Country_code, Plan_start, Plan_ndays" + (isPlanNameNull ? "" : ", Plan_name") + ") VALUES(?, ?, ?, ?" + (isPlanNameNull ? "" : ", ?") + ");";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, planInfoDTO.getMemSeq());
				pstmt.setString(2, planInfoDTO.getCountryCode());
				pstmt.setDate(3, planInfoDTO.getPlanStart());
				pstmt.setInt(4, planInfoDTO.getPlanNDays());
				if (!isPlanNameNull)
					pstmt.setString(5, planInfoDTO.getPlanName());
				return pstmt;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	private RowMapper<PlanInfoDTO> planInfoRowMapper() {
		return new RowMapper<PlanInfoDTO>() {
			@Override
			public PlanInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlanInfoDTO planBasicInfoReadDTO = new PlanInfoDTO();
				planBasicInfoReadDTO.setPlanSeq(rs.getInt("Plan_seq"));
				planBasicInfoReadDTO.setMemSeq(rs.getInt("Mem_seq"));
				planBasicInfoReadDTO.setPlanName(rs.getString("Plan_name"));
				planBasicInfoReadDTO.setCountryCode(rs.getString("Country_code"));
				planBasicInfoReadDTO.setPlanStart(rs.getDate("Plan_start"));
				planBasicInfoReadDTO.setPlanNDays(rs.getInt("Plan_ndays"));
				return planBasicInfoReadDTO;
			}
		};
	}
}
