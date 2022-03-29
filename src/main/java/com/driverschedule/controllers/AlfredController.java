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

import com.driverschedule.dto.AlfredDetailDTO;
import com.driverschedule.entities.AlfredEntity;
import com.driverschedule.services.AlfredService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/alfreds")
public class AlfredController {
	
	@Autowired
	AlfredService alfredService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<AlfredDetailDTO> findAll(@RequestParam(required = false) String lat, @RequestParam(required = false) String lng) throws JsonMappingException, JsonProcessingException {
		List<AlfredEntity> drivers = alfredService.getAlfreds();
		return modelMapper.map(drivers, new TypeToken<List<AlfredDetailDTO>>() {
		}.getType());
	}
}
