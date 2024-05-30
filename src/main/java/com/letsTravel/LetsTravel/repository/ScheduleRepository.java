package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleUpdateDTO;

public interface ScheduleRepository {

	int addSchedule(ScheduleCreateDTO scheduleCreateDTO);

	int modifySchedule(ScheduleUpdateDTO scheduleUpdateDTO);

}
