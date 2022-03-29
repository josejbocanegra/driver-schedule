package com.driverschedule.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {
	private Date date;
	private Date startTime;
	private Integer dragLat;
	private Integer dragLng;
	private Integer dropLat;
	private Integer dropLng;
	private DriverDTO alfred;
}
