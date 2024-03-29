package com.driverschedule.controllers;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.driverschedule.dto.ScheduleDriverDTO;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.services.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ScheduleDriverDTO> findAll(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, 
			@RequestParam(required = false) Boolean isAvailable) {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(date, isAvailable);
		return modelMapper.map(schedules, new TypeToken<List<ScheduleDriverDTO>>() {
		}.getType());
	}	
}
