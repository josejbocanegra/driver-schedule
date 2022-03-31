package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.driverschedule.entities.DriverEntity;
import com.driverschedule.entities.ScheduleEntity;
import com.driverschedule.services.ScheduleService;
import com.driverschedule.utils.DateService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ScheduleService.class, DateService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScheduleServiceTest {
	
	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();
	
	private ScheduleEntity availableSchedule;
	private ScheduleEntity nonAvailableSchedule;
	private DriverEntity driver;
	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ScheduleEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from DriverEntity").executeUpdate();
	}
	
	private void insertData() {
		
		driver = factory.manufacturePojo(DriverEntity.class);
		driver.setId(null);
		entityManager.persist(driver);
		
		availableSchedule = factory.manufacturePojo(ScheduleEntity.class);
		availableSchedule.setId(null);
		availableSchedule.setService(null);
		availableSchedule.setIsAvailable(true);
		availableSchedule.setDriver(driver);
		entityManager.persist(availableSchedule);
		
		nonAvailableSchedule = factory.manufacturePojo(ScheduleEntity.class);
		nonAvailableSchedule.setId(null);
		nonAvailableSchedule.setService(null);
		nonAvailableSchedule.setIsAvailable(false);
		nonAvailableSchedule.setDriver(driver);
		entityManager.persist(nonAvailableSchedule);
	}
	
	@Test 
	void testGetSchedules() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(null, null);
		assertNotNull(schedules);
	}
	
	@Test 
	void testGetSchedulesWithDate() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(availableSchedule.getDate(), null);
		assertNotNull(schedules);
	}
	
	@Test 
	void testGetAvailableSchedulesWithDate() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(availableSchedule.getDate(), true);
		assertNotNull(schedules);
	}
	
	@Test 
	void testGetAvailableSchedulesWithoutDate() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(null, true);
		assertNotNull(schedules);
	}
	
	@Test 
	void testGetNonAvailableSchedulesWithDate() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(availableSchedule.getDate(), false);
		assertNotNull(schedules);
	}
	
	@Test 
	void testGetNonAvailableSchedulesWithoutDate() {
		List<ScheduleEntity> schedules = scheduleService.getSchedules(null, false);
		assertNotNull(schedules);
	}
}
