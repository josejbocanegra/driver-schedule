package com.driverschedule.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "schedule_generator", sequenceName = "schedule_sequence",  initialValue = 100)
public class ScheduleEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule_generator")
	private Long id;
	
	private Date date;
	private Boolean isAvailable;
	
	@ManyToOne
	private DriverEntity driver;
	
	@OneToOne
	private ServiceEntity service;
}
