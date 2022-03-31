package com.driverschedule.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driverschedule.entities.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
	public List<ScheduleEntity> findAllByDriverIdAndDate(Long driverId, Date date);
	List<ScheduleEntity> findAllByDateBetweenOrderByDateAsc(Date start, Date end);
	
	List<ScheduleEntity> findAllByDriverIdAndDateBetweenOrderByDateAsc(Long driverId, Date start, Date end);
	
	List<ScheduleEntity> findAllByIsAvailableAndDateBetweenOrderByDateAsc(Boolean isAvailable, Date start, Date end);
	
	List<ScheduleEntity> findAllByIsAvailableOrderByDateAsc(Boolean isAvailable);
}
