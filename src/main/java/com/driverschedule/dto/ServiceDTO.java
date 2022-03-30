package com.driverschedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {
	private Long id;
	private Integer dragLat;
	private Integer dragLng;
	private Integer dropLat;
	private Integer dropLng;
}
