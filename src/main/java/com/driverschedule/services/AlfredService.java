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

import com.driverschedule.dto.AlfredDTO;
import com.driverschedule.dto.PointDTO;
import com.driverschedule.entities.AlfredEntity;
import com.driverschedule.repositories.AlfredRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlfredService {
	
	private String URL = "https://gist.githubusercontent.com/jeithc/96681e4ac7e2b99cfe9a08ebc093787c/raw/632ca4fc3ffe77b558f467beee66f10470649bb4/points.json";
	
	@Autowired
	AlfredRepository alfredRepository;
	
	@Transactional
	public AlfredEntity createAlfred(AlfredEntity driver) {
		return alfredRepository.save(driver);
	}	
	
	@Transactional
	public List<AlfredEntity> getAlfreds() throws JsonMappingException, JsonProcessingException {
		log.info("Getting all alfreds");
		return getUpdatedLocation();
	}	
	
	public List<AlfredEntity> getUpdatedLocation() throws JsonMappingException, JsonProcessingException{

		List<AlfredEntity> alfredList = new ArrayList<>();

		PointDTO response = getClient().get()
	        .uri(URL)
	        .retrieve()
	        .bodyToMono(PointDTO.class)
	        .block();
		
		for(int i = 0; i < response.getAlfreds().size(); i++) {
			AlfredDTO alfred = response.getAlfreds().get(i);
			Optional<AlfredEntity> alfredEntity = alfredRepository.findById(alfred.getId());
			if(!alfredEntity.isEmpty()) {
				alfredEntity.get().setLat(alfred.getLat());
				alfredEntity.get().setLng(alfred.getLng());
				alfredList.add(alfredEntity.get());
			}
			
		}
			    
		return alfredList;
	}
	
	public float getClosestDriver(int x1, int y1) {
		int x2 = 0;
		int y2 = 0;
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
