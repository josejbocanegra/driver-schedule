package com.driverschedule.controllers;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.driverschedule.dto.ScheduleDTO;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.services.DriverScheduleService;

@RestController
@RequestMapping("/api/drivers")
public class DriverScheduleController {
	
	@Autowired
	DriverScheduleService scheduleService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{driverId}/schedules")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ScheduleDTO update(@PathVariable("driverId") Long driverId, @RequestBody ScheduleDTO scheduleDTO) throws EntityNotFoundException, IllegalOperationException {
		ScheduleEntity scheduleEntity = scheduleService.createSchedule(driverId, modelMapper.map(scheduleDTO, ScheduleEntity.class));
		return modelMapper.map(scheduleEntity, ScheduleDTO.class);
	}
	
	@GetMapping(value = "/{driverId}/schedules")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ScheduleDTO> findAll(@PathVariable("driverId") Long driverId, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws EntityNotFoundException, IllegalOperationException {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(driverId, date);
		return modelMapper.map(schedules, new TypeToken<List<ScheduleDTO>>() {
		}.getType());
	}
	
}
