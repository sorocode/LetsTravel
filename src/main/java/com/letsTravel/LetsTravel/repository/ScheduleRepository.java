package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.ScheduleCreateDTO;

public interface ScheduleRepository {

	int addSchedule(ScheduleCreateDTO scheduleCreateDTO);

}
