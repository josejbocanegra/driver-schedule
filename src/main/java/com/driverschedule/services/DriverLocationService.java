package com.driverschedule.services;

import com.driverschedule.dto.PointDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DriverLocationService {
    private String URL = "https://gist.githubusercontent.com/jeithc/96681e4ac7e2b99cfe9a08ebc093787c/raw/632ca4fc3ffe77b558f467beee66f10470649bb4/points.json";
	
    public PointDTO getDriverLocation(){
        PointDTO pointDTO = getClient().get()
	        .uri(URL)
	        .retrieve()
	        .bodyToMono(PointDTO.class)
	        .block();

        return pointDTO;
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
