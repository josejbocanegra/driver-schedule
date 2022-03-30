package com.driverschedule.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "driver_generator", sequenceName = "driver_sequence",  initialValue = 100)
public class DriverEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "driver_generator")
	private Long id;
	private String name;
	private String lat;
	private String lng;
	private Date lastUpdate;
	private Float distance;
		
	@OneToMany (mappedBy = "driver")
	private List<ScheduleEntity> schedules = new ArrayList<>();
}
