package com.driverschedule.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {
	private Long id;
	private Date date;
	
	private Boolean isAvailable;
	private DriverDTO driver;
	private ServiceDTO service;
}
