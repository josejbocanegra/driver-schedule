package com.driverschedule.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.driverschedule.dto.DriverDTO;
import com.driverschedule.dto.PointDTO;
import com.driverschedule.entities.DriverEntity;
import com.driverschedule.repositories.DriverRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverService {
	
	private String URL = "https://gist.githubusercontent.com/jeithc/96681e4ac7e2b99cfe9a08ebc093787c/raw/632ca4fc3ffe77b558f467beee66f10470649bb4/points.json";
	
	@Autowired
	DriverRepository driverRepository;
	
	@Transactional
	public DriverEntity createDriver(DriverEntity driver) {
		log.info("Creating driver");
		return driverRepository.save(driver);
	}	
	
	@Transactional
	public List<DriverEntity> getDrivers() throws JsonMappingException, JsonProcessingException {
		log.info("Getting all drivers");
		
		PointDTO response = getClient().get()
	        .uri(URL)
	        .retrieve()
	        .bodyToMono(PointDTO.class)
	        .block();
		
		List<DriverEntity> driversList = driverRepository.findAll();
		
		for (DriverEntity driverEntity : driversList) {
			Optional<DriverDTO> driverDTO = response.getAlfreds().stream()
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
	
	public WebClient getClient() {
		WebClient client = WebClient.builder()
        .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->{
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                configurer.customCodecs().register(new Jackson2JsonDecoder(mapper, MimeTypeUtils.parseMimeType(MediaType.TEXT_PLAIN_VALUE)));
                }).build())
        .build();
		return client;
	}
	
}
