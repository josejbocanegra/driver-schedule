package com.driverschedule.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.driverschedule.dto.DriverDTO;
import com.driverschedule.dto.PointDTO;
import com.driverschedule.entities.DriverEntity;
import com.driverschedule.repositories.DriverRepository;
import com.driverschedule.utils.DistanceService;
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
	
	@Autowired
	DistanceService distanceService;
	
	@Transactional
	public DriverEntity createDriver(DriverEntity driver) {
		log.info("Creating driver");
		return driverRepository.save(driver);
	}	
	
	@Transactional
	public List<DriverEntity> getDrivers(String lat, String lng) throws JsonProcessingException {
		log.info("Getting all drivers");
		
		PointDTO pointDTO = driverLocationService.getDriverLocation();
		
		List<DriverEntity> driversList = driverRepository.findAll();
		
		for (DriverEntity driverEntity : driversList) {
			Optional<DriverDTO> driverDTO = pointDTO.getAlfreds().stream()
        		.filter(alfred -> driverEntity.getId().equals(alfred.getId()))
                .findFirst();

			if(driverDTO.isPresent()) {
				driverEntity.setLat(driverDTO.get().getLat());
				driverEntity.setLng(driverDTO.get().getLng());
				driverEntity.setLastUpdate(driverDTO.get().getLastUpdate());
				
				if(lat != null && lng != null) {
					float distance = distanceService.getDistanceBetweenTwoPoints(Integer.parseInt(lat), Integer.parseInt(lng), Integer.parseInt(driverDTO.get().getLat()), Integer.parseInt(driverDTO.get().getLng()));
					driverEntity.setDistance(distance);
				}
			}
		}			  
		return driverRepository.findAll(Sort.by(Sort.Direction.ASC, "distance"));
	}	
		
	
	
}
