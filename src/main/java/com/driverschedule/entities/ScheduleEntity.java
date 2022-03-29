package com.driverschedule.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ScheduleEntity extends BaseEntity{
	private Date date;
	private Date startTime;
	private Integer dragLat;
	private Integer dragLng;
	private Integer dropLat;
	private Integer dropLng;
	
	@ManyToOne
	private DriverEntity driver;
}
