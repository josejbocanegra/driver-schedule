package com.driverschedule.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.driverschedule.dto.ScheduleDTO;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.services.DriverServiceService;

@RestController
@RequestMapping("/api/drivers")
public class DriverServiceController {
	
	@Autowired
	DriverServiceService driverServiceService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/{driverId}/services")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ScheduleDTO update(@PathVariable("driverId") Long driverId, @RequestBody ScheduleDTO scheduleDTO) throws EntityNotFoundException, IllegalOperationException {
		ScheduleEntity scheduleEntity = driverServiceService.createService(driverId, modelMapper.map(scheduleDTO, ScheduleEntity.class));
		return modelMapper.map(scheduleEntity, ScheduleDTO.class);
	}
}
