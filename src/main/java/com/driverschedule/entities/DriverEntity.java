package com.driverschedule.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DriverEntity extends BaseEntity {

	private String name;
	private String lat;
	private String lng;
	private Date lastUpdate;
		
	@OneToMany (mappedBy = "driver")
	private List<ScheduleEntity> schedules = new ArrayList<>();
}