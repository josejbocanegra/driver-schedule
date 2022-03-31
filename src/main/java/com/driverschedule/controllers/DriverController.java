package com.driverschedule.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.driverschedule.dto.DriverDetailDTO;
import com.driverschedule.dto.DriverDistanceDetailDTO;
import com.driverschedule.entities.DriverEntity;
import com.driverschedule.services.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
	
	@Autowired
	DriverService driverService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<DriverDetailDTO> findAll(@RequestParam(required = false) String lat, @RequestParam(required = false) String lng) throws JsonMappingException, JsonProcessingException {
		List<DriverEntity> drivers = driverService.getDrivers(lat, lng);
		return modelMapper.map(drivers, new TypeToken<List<DriverDetailDTO>>() {
		}.getType());
	}
	
	@GetMapping("/byDistance")
	@ResponseStatus(code = HttpStatus.OK)
	public List<DriverDistanceDetailDTO> findAllByDistance(@RequestParam(required = false) String lat, @RequestParam(required = false) String lng) throws JsonMappingException, JsonProcessingException {
		List<DriverEntity> drivers = driverService.getDrivers(lat, lng);
		return modelMapper.map(drivers, new TypeToken<List<DriverDistanceDetailDTO>>() {
		}.getType());
	}
}
