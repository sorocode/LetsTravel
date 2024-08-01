package com.letsTravel.LetsTravel.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.place.Place;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleInfoDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleUpdateDTO;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateScheduleRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int addSchedule(ScheduleCreateDTO scheduleCreateDTO) {
		boolean isVisitTimeNull = scheduleCreateDTO.getVisitTime() == null;
		String sql = "INSERT INTO Schedule(Plan_seq, Place_seq, Date_seq, Visit_seq" + (isVisitTimeNull ? "" : ", Visit_time") + ") VALUE(?, (SELECT Place_seq FROM Place WHERE Place_id = ?), ?, ?"
				+ (isVisitTimeNull ? "" : ", ?") + ");";
		return jdbcTemplate.update(sql, scheduleCreateDTO.getPlanSeq(), scheduleCreateDTO.getPlaceId(), scheduleCreateDTO.getDateSeq(), scheduleCreateDTO.getVisitSeq(),
				scheduleCreateDTO.getVisitTime());
	}

	@Override
	public int modifySchedule(ScheduleUpdateDTO scheduleUpdateDTO) {
		String sql = "UPDATE Schedule SET Place_seq = ?, Date_seq = ?, Visit_seq = ?, Visit_time = ? WHERE Schedule_seq = ?";
		return jdbcTemplate.update(sql, scheduleUpdateDTO.getPlaceSeq(), scheduleUpdateDTO.getDateSeq(), scheduleUpdateDTO.getVisitSeq(), scheduleUpdateDTO.getVisitTime(),
				scheduleUpdateDTO.getScheduleSeq());
	}

	@Override
	public List<ScheduleInfoDTO> findSchedulesByPlanSeq(int planSeq) {
		String sql = "SELECT S.Schedule_seq, S.Place_seq, S.Date_seq, S.Visit_seq, S.Visit_time FROM Schedule S WHERE S.Plan_seq = ?;";
		return jdbcTemplate.query(sql, scheduleInfoRowMapper(), planSeq);
	}

	private RowMapper<ScheduleInfoDTO> scheduleInfoRowMapper() {
		return (rs, rowNum) -> {
			ScheduleInfoDTO scheduleInfo = new ScheduleInfoDTO();
			scheduleInfo.setScheduleSeq(rs.getInt("S.Schedule_seq"));
			scheduleInfo.setPlace(new Place(rs.getInt("S.Place_Seq")));
			scheduleInfo.setDateSeq(rs.getInt("S.Date_seq"));
			scheduleInfo.setVisitSeq(rs.getInt("S.Visit_seq"));
			scheduleInfo.setVisitTime(rs.getTime("S.Visit_time"));
			return scheduleInfo;
		};
	}
}
