package com.driverschedule.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driverschedule.entities.DriverEntity;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.entities.ServiceEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.ErrorMessage;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.repositories.DriverRepository;
import com.driverschedule.repositories.ScheduleRepository;
import com.driverschedule.repositories.ServiceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverServiceService {
	
	@Autowired
	DriverRepository driverRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Transactional
	public ScheduleEntity createService(Long driverId, ScheduleEntity schedule) throws EntityNotFoundException, IllegalOperationException {
		log.info("Creating schedule");
		Optional<DriverEntity> driver = driverRepository.findById(driverId);
		
		if(driver.isEmpty()) {
			throw new EntityNotFoundException(ErrorMessage.DRIVER_NOT_FOUND);
		}
		
		if(schedule.getService().getDragLat() < 0 || schedule.getService().getDragLat() >100) {
			throw new IllegalOperationException(ErrorMessage.DRAG_LAT_OOR);
		}
		
		if(schedule.getService().getDragLng() < 0 || schedule.getService().getDragLng() >100) {
			throw new IllegalOperationException(ErrorMessage.DRAG_LNG_OOR);
		}
		
		if(schedule.getService().getDropLat() < 0 || schedule.getService().getDropLat() >100) {
			throw new IllegalOperationException(ErrorMessage.DROP_LAT_OOR);
		}
		
		if(schedule.getService().getDropLng() < 0 || schedule.getService().getDropLng() >100) {
			throw new IllegalOperationException(ErrorMessage.DROP_LNG_OOR);
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
				throw new IllegalOperationException(ErrorMessage.SLOT_NOT_AVAILABLE);
			}
		} else {
			throw new IllegalOperationException(ErrorMessage.THERE_IS_NOT_SLOT);
		}
	}
}
