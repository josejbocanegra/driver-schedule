package com.driverschedule.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDriverDTO {
	private Long id;
	private Date date;
	
	private Boolean isAvailable;
	private ServiceDTO service;
	private DriverDTO driver;
}
