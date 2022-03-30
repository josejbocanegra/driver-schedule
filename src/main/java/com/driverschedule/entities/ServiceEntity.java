package com.driverschedule.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "service_generator", sequenceName = "service_sequence",  initialValue = 100)
public class ServiceEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "service_generator")
	private Long id;

	private Integer dragLat;
	private Integer dragLng;
	private Integer dropLat;
	private Integer dropLng;
	
	@OneToOne(mappedBy = "service")
	private ScheduleEntity schedule;
}
