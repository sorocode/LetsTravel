package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateScheduleRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int addSchedule(ScheduleCreateDTO scheduleCreateDTO) {
		boolean isVisitTimeNull = scheduleCreateDTO.getVisitTime() == null;
		String sql = "INSERT INTO Schedule(Plan_seq, Place_seq, Date_seq, Visit_seq"
				+ (isVisitTimeNull ? "" : ", Visit_time")
				+ ") VALUE(?, (SELECT Place_seq FROM Place WHERE Place_id = ?), ?, ?"
				+ (isVisitTimeNull ? "" : ", ?") + ");";
		return jdbcTemplate.update(sql, scheduleCreateDTO.getPlanSeq(), scheduleCreateDTO.getPlaceId(),
				scheduleCreateDTO.getDateSeq(), scheduleCreateDTO.getVisitSeq(), scheduleCreateDTO.getVisitTime());
	}
}
