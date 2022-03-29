package com.driverschedule.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.repositories.ScheduleRepository;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Transactional
	public ScheduleEntity create(ScheduleEntity schedule) {
		return scheduleRepository.save(schedule);
	}
}
