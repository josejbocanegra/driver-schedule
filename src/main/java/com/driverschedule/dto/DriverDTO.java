package com.driverschedule.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DriverDTO {
	private Long id;
	private String name;
	private String lat;
	private String lng;
	private Date lastUpdate;
}
