package com.driverschedule.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {
	private Long id;
	private String name;
	private String lat;
	private String lng;
	private Date lastUpdate;
}
