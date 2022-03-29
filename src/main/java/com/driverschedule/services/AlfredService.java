package com.driverschedule.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.driverschedule.dto.AlfredDTO;
import com.driverschedule.dto.AlfredDetailDTO;
import com.driverschedule.dto.PointDTO;
import com.driverschedule.entities.AlfredEntity;
import com.driverschedule.repositories.AlfredRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AlfredService {
	
	private String URL = "https://gist.githubusercontent.com/jeithc/96681e4ac7e2b99cfe9a08ebc093787c/raw/632ca4fc3ffe77b558f467beee66f10470649bb4/points.json";
	
	@Autowired
	AlfredRepository alfredRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public AlfredEntity createAlfred(AlfredEntity driver) {
		return alfredRepository.save(driver);
	}	
	
	@Transactional
	public List<AlfredEntity> getAlfreds() throws JsonMappingException, JsonProcessingException {
		getUpdatedLocation();
		return alfredRepository.findAll();
	}	
	
	public void  getUpdatedLocation() throws JsonMappingException, JsonProcessingException{
		WebClient client = WebClient.create();

		PointDTO response = client.get()
	        .uri(URL)
	        .retrieve()
	        .bodyToFlux(PointDTO.class)
	        .blockLast();
		
		for(int i = 0; i < response.getAlfreds().size(); i++) {
			log.info("In the loop");
			
		}
			    
		
		/*
		for(int i = 0; i < points.getAlfreds().size(); i++) {
			log.info("In the loop");
			AlfredDTO alfred = points.getAlfreds().get(i);
			Optional<AlfredEntity> alfredEntity = alfredRepository.findById(alfred.getId());
			if(!alfredEntity.isEmpty()) {
				alfredEntity.get().setLat(alfred.getLat());
				alfredEntity.get().setLat(alfred.getLng());
				alfredList.add(alfredEntity.get());
				System.out.println("Existe" + alfredEntity.get().getId());
			}
		}
		
		return alfredList;*/
	}
	
	public float getClosestDriver(int x1, int y1) {
		int x2 = 0;
		int y2 = 0;
		float distance = (float) Math.sqrt( Math.pow((x2 - x1), 2)  +  Math.pow((y2 - y1), 2) );
		return distance;
	}
}
