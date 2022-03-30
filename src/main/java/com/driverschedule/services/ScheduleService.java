package com.driverschedule.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.repositories.ScheduleRepository;
import com.driverschedule.utils.DateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleService {
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	DateService dateService;
	
	@Transactional
	public List<ScheduleEntity> getSchedules(Date date) {
		log.info("Getting schedules");
		if(date != null) {
			log.info("Getting schedules by date " + date);
			Date startDate = dateService.getStartTime(date);
			Date endDate = dateService.getEndTime(date);
				
			return scheduleRepository.findAllByDateBetweenOrderByDateAsc(startDate, endDate);
		}
		
		return scheduleRepository.findAll();
	}
}
