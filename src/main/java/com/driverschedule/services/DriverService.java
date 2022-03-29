package com.driverschedule.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.driverschedule.dto.DriverDTO;
import com.driverschedule.dto.PointDTO;
import com.driverschedule.entities.DriverEntity;
import com.driverschedule.repositories.DriverRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverService {
	
	@Autowired
	DriverRepository driverRepository;

	@Autowired
	DriverLocationService driverLocationService;
	
	@Transactional
	public DriverEntity createDriver(DriverEntity driver) {
		log.info("Creating driver");
		return driverRepository.save(driver);
	}	
	
	@Transactional
	public List<DriverEntity> getDrivers() throws JsonMappingException, JsonProcessingException {
		log.info("Getting all drivers");
		
		PointDTO pointDTO = driverLocationService.getDriverLocation();
		
		List<DriverEntity> driversList = driverRepository.findAll();
		
		for (DriverEntity driverEntity : driversList) {
			Optional<DriverDTO> driverDTO = pointDTO.getAlfreds().stream()
        		.filter(alfred -> driverEntity.getId() == alfred.getId())
                .findFirst();

			if(driverDTO.isPresent()) {
				driverEntity.setLat(driverDTO.get().getLat());
				driverEntity.setLng(driverDTO.get().getLng());
				driverEntity.setLastUpdate(driverDTO.get().getLastUpdate());
			}
		}			    
		return driversList;
	}	
		
	public float getDistanceBetweenTwoPoints(int x1, int x2, int y1, int y2) {
		float distance = (float) Math.sqrt( Math.pow((x2 - x1), 2)  +  Math.pow((y2 - y1), 2) );
		return distance;
	}
	
}
