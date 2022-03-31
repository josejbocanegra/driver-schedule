package com.driverschedule.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driverschedule.entities.DriverEntity;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.entities.ServiceEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.repositories.DriverRepository;
import com.driverschedule.repositories.ScheduleRepository;
import com.driverschedule.repositories.ServiceRepository;
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
	private ServiceRepository serviceRepository;
	
	@Autowired
	private DateService dateService;
	
	@Transactional
	public ScheduleEntity createSchedule(Long driverId, ScheduleEntity schedule) throws EntityNotFoundException, IllegalOperationException {
		log.info("Creating schedule");
		Optional<DriverEntity> driver = driverRepository.findById(driverId);
		
		if(driver.isEmpty()) {
			throw new EntityNotFoundException("The driver with the given id was not found");
		}
		
		if(schedule.getService().getDragLat() < 0 || schedule.getService().getDragLat() >100) {
			throw new IllegalOperationException("The drag latitude is out of range");
		}
		
		if(schedule.getService().getDragLng() < 0 || schedule.getService().getDragLng() >100) {
			throw new IllegalOperationException("The drag longitude is out of range");
		}
		
		if(schedule.getService().getDropLat() < 0 || schedule.getService().getDropLat() >100) {
			throw new IllegalOperationException("The drop latitude is out of range");
		}
		
		if(schedule.getService().getDropLng() < 0 || schedule.getService().getDropLng() >100) {
			throw new IllegalOperationException("The drop longitude is out of range");
		}
		
		List<ScheduleEntity> existentSchedules = scheduleRepository.findAllByDriverIdAndDate(driverId, schedule.getDate());
		if(!existentSchedules.isEmpty()) {
			if(Boolean.TRUE.equals(existentSchedules.get(0).getIsAvailable())) {
				existentSchedules.get(0).setIsAvailable(false);
				
				ServiceEntity service = new ServiceEntity();
				service.setDragLat(schedule.getService().getDragLat());
				service.setDragLng(schedule.getService().getDragLng());
				service.setDropLat(schedule.getService().getDropLat());
				service.setDropLng(schedule.getService().getDropLng());
				serviceRepository.save(service);
				
				existentSchedules.get(0).setService(service);
				scheduleRepository.save(existentSchedules.get(0));
				return existentSchedules.get(0);
			} else {
				throw new IllegalOperationException("The slot is not availble");
			}
		} else {
			throw new IllegalOperationException("There is not a slot in the selected date and time");
		}
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
}
