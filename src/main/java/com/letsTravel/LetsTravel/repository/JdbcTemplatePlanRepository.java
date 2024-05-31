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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.Location;
import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanCreateDTO;
import com.letsTravel.LetsTravel.domain.plan.PlanDetailReadDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleDetailDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleReadDTO;

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

	@Override
	public List<PlanBasicInfoReadDTO> findPlanByMemberSeq(int memberSeq) {
		String sql = "SELECT Plan_seq, Plan_name, Country_code, Plan_start, Plan_ndays FROM Plan WHERE Mem_seq = ?";
		return jdbcTemplate.query(sql, new RowMapper<PlanBasicInfoReadDTO>() {
			@Override
			public PlanBasicInfoReadDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlanBasicInfoReadDTO planBasicInfoReadDTO = new PlanBasicInfoReadDTO();
				planBasicInfoReadDTO.setPlanSeq(rs.getInt("Plan_seq"));
				planBasicInfoReadDTO.setPlanName(rs.getString("Plan_name"));
				planBasicInfoReadDTO.setCountryCode(rs.getString("Country_code"));
				planBasicInfoReadDTO.setPlanStart(rs.getDate("Plan_start"));
				planBasicInfoReadDTO.setPlanNDays(rs.getInt("Plan_ndays"));
				return planBasicInfoReadDTO;
			}
		}, memberSeq);
	}

	@Override
	public PlanDetailReadDTO findPlanByPlanSeq(int planSeq) {
		String sql = "SELECT \r\n" + "    P.Plan_seq, P.Plan_name, P.Country_code, P.Plan_start, P.Plan_ndays, \r\n"
				+ "    M.Mem_seq, M.Nickname, \r\n"
				+ "    IF(CS.City_name_translated IS NULL, C.City_name, CS.City_name_translated) AS City_name, \r\n"
				+ "    S.Schedule_seq, S.Place_seq, \r\n" + "    PN.Display_name, \r\n"
				+ "    PL.Place_latitude, PL.Place_longitude, \r\n"
				+ "    IF(T.Type_name_translated IS NULL, T.Type_name, T.Type_name_translated) AS Primary_type, \r\n"
				+ "    S.Date_seq, S.Visit_seq, S.Visit_time\r\n" + "FROM \r\n" + "    Plan AS P\r\n"
				+ "    LEFT JOIN Plan_share AS PS ON P.Plan_seq = PS.Plan_seq\r\n"
				+ "    LEFT JOIN Member AS M ON PS.Mem_seq = M.Mem_seq\r\n"
				+ "    LEFT JOIN Plan_city AS PC ON P.Plan_seq = PC.Plan_seq\r\n"
				+ "    LEFT JOIN City AS C ON PC.City_seq = C.City_seq\r\n"
				+ "    LEFT JOIN City_standard AS CS ON C.City_standard_seq = CS.City_standard_seq\r\n"
				+ "    LEFT JOIN Schedule AS S ON P.Plan_seq = S.Plan_seq\r\n"
				+ "    LEFT JOIN Place AS PL ON S.Place_seq = PL.Place_seq\r\n"
				+ "    LEFT JOIN Place_name AS PN ON PL.Place_seq = PN.Place_seq\r\n"
				+ "    LEFT JOIN Place_type AS PT ON PL.Place_seq = PT.Place_seq\r\n"
				+ "    LEFT JOIN Type AS T ON PT.Type_seq = T.Type_seq\r\n" + "WHERE \r\n" + "    P.Plan_seq = ?\r\n"
				+ "    AND PT.Is_primary_type = 1 " 
				+ "ORDER BY\r\n" + "    S.Date_seq ASC, S.Visit_seq ASC;";
		return jdbcTemplate.query(sql, new ResultSetExtractor<PlanDetailReadDTO>() {

			@Override
			public PlanDetailReadDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
				PlanDetailReadDTO planDetail = null;
				Map<Integer, MemberBasicInfoReadDTO> planShareMember = new HashMap<>();
				Set<String> planCities = new HashSet<>();
				TreeMap<Integer, List<ScheduleDetailDTO>> scheduleMap = new TreeMap<Integer, List<ScheduleDetailDTO>>();
				List<ScheduleReadDTO> scheduleList = new ArrayList<>();

				while (rs.next()) {
					if (planDetail == null) {
						planDetail = new PlanDetailReadDTO();
						PlanBasicInfoReadDTO planBasicInfo = new PlanBasicInfoReadDTO();
						planBasicInfo.setPlanSeq(planSeq);
						planBasicInfo.setPlanName(rs.getString("P.Plan_name"));
						planBasicInfo.setCountryCode(rs.getString("P.Country_code"));
						planBasicInfo.setPlanStart(rs.getDate("P.Plan_start"));
						planBasicInfo.setPlanNDays(rs.getInt("P.Plan_ndays"));
						planDetail.setPlanInfo(planBasicInfo);
					}

					Integer shareMemberSeq = rs.getInt("M.Mem_seq");
					if (shareMemberSeq != 0) {
						MemberBasicInfoReadDTO memberBasicInfo = new MemberBasicInfoReadDTO();
						memberBasicInfo.setMemSeq(shareMemberSeq);
						memberBasicInfo.setNickName(rs.getString("M.Nickname"));
						planShareMember.put(shareMemberSeq, memberBasicInfo);
					}

					String cityName = rs.getString("City_name");
					if (cityName != null) {
						planCities.add(cityName);
					}

					Integer dateSeq = rs.getInt("S.Date_seq");
					if (!scheduleMap.containsKey(dateSeq)) {
						List<ScheduleDetailDTO> scheduleDetailList = new ArrayList<ScheduleDetailDTO>();
						scheduleMap.put(dateSeq, scheduleDetailList);

					}

					ScheduleDetailDTO scheduleDetailDTO = new ScheduleDetailDTO();
					scheduleDetailDTO.setScheduleSeq(rs.getInt("S.Schedule_seq"));
					scheduleDetailDTO.setPlaceSeq(rs.getInt("S.Place_seq"));
					scheduleDetailDTO.setPlaceName(rs.getString("PN.Display_name"));
					scheduleDetailDTO.setLocation(
							new Location(rs.getFloat("PL.Place_latitude"), rs.getFloat("PL.Place_longitude")));
					scheduleDetailDTO.setVisitSeq(rs.getInt("S.Visit_seq"));
					if (rs.getTime("S.Visit_time") != null)
						scheduleDetailDTO.setVisitTime(rs.getTime("S.Visit_time"));
					if (rs.getString("Primary_type") != null)
						scheduleDetailDTO.setPrimaryType(rs.getString("Primary_type"));
					scheduleMap.get(dateSeq).add(scheduleDetailDTO);
				}

				if (planDetail != null) {
					planDetail.setPlanShareMembers(new ArrayList<>(planShareMember.values()));
					planDetail.setPlanCities(new ArrayList<>(planCities));

					for (Integer k : scheduleMap.keySet()) {
						scheduleList.add(new ScheduleReadDTO(k, scheduleMap.get(k)));
					}
					planDetail.setSchedules(scheduleList);
				}
				return planDetail;
			}
		}, planSeq);
	}
}
