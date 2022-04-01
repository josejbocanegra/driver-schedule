package com.driverschedule.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driverschedule.entities.DriverEntity;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.repositories.DriverRepository;
import com.driverschedule.repositories.ScheduleRepository;
import com.driverschedule.utils.DateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private DriverRepository driverRepository;
		
	@Autowired
	private DateService dateService;
	
	@Transactional
	public ScheduleEntity createSchedule(Long driverId, ScheduleEntity schedule) throws EntityNotFoundException, IllegalOperationException {
		log.info("Creating schedule");
		Optional<DriverEntity> driver = driverRepository.findById(driverId);
		if(driver.isEmpty()){
			throw new EntityNotFoundException("The driver with the given id was not found");
		}
		
		List<ScheduleEntity> schedules = scheduleRepository.findAllByDriverIdAndDate(driverId, schedule.getDate());
		if(!schedules.isEmpty()) {
			throw new IllegalOperationException("There is a schedule in the selected slot");
		}
		
		schedule.setDriver(driver.get());
		return scheduleRepository.save(schedule);
	}
	
	@Transactional
	public List<ScheduleEntity> getSchedules(Long driverId, Date date) throws EntityNotFoundException, IllegalOperationException {
		log.info("Getting schedules for driver " + driverId);
		Optional<DriverEntity> driver = driverRepository.findById(driverId);
		if(driver.isEmpty()) {
			throw new EntityNotFoundException("The driver with the given id was not found");
		}
		
		if(date != null) {
			log.info("Getting schedules by date " + date);
			Date startDate = dateService.getStartTime(date);
			Date endDate = dateService.getEndTime(date);
		
			return scheduleRepository.findAllByDriverIdAndDateBetweenOrderByDateAsc(driverId, startDate, endDate);
		} else {
			return driver.get().getSchedules();
		}
	}
	
	@Transactional
	public void deleteSchedule(Long driverId, Long scheduleId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Getting schedules for driver " + driverId);
		Optional<DriverEntity> driver = driverRepository.findById(driverId);
		if(driver.isEmpty()) {
			throw new EntityNotFoundException("The driver with the given id was not found");
		}
		
		Optional<ScheduleEntity> schedule = scheduleRepository.findById(scheduleId);
		if(schedule.isEmpty()) {
			throw new EntityNotFoundException("The schedule with the given id was not found");
		}
		scheduleRepository.delete(schedule.get());
	}
}
