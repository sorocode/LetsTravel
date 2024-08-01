package com.letsTravel.LetsTravel.repository;

import java.util.List;

import com.letsTravel.LetsTravel.domain.schedule.ScheduleCreateDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleInfoDTO;
import com.letsTravel.LetsTravel.domain.schedule.ScheduleUpdateDTO;

public interface ScheduleRepository {

	int addSchedule(ScheduleCreateDTO scheduleCreateDTO);
	int modifySchedule(ScheduleUpdateDTO scheduleUpdateDTO);
	List<ScheduleInfoDTO> findSchedulesByPlanSeq(int planSeq);
}
