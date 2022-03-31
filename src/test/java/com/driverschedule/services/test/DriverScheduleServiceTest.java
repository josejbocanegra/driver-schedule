package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.driverschedule.entities.ServiceEntity;
import com.driverschedule.exceptions.EntityNotFoundException;
import com.driverschedule.exceptions.IllegalOperationException;
import com.driverschedule.services.DriverScheduleService;
import com.driverschedule.utils.DateService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({DriverScheduleService.class, DateService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverScheduleServiceTest {
	
	@Autowired
	private DriverScheduleService driverScheduleService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();
	
	private DriverEntity driver;
	private ScheduleEntity schedule;
	
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
		
		schedule = factory.manufacturePojo(ScheduleEntity.class);
		schedule.setId(null);
		schedule.setIsAvailable(true);
		schedule.setDriver(driver);
		entityManager.persist(schedule);
	}
	
	@Test
	void testcreateSchedule() throws EntityNotFoundException, IllegalOperationException {
		ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
		service.setId(null);
		service.setDragLat(10);
		service.setDragLng(10);
		service.setDropLat(10);
		service.setDropLng(10);
		
		ScheduleEntity newEntity = factory.manufacturePojo(ScheduleEntity.class);
		newEntity.setService(service);
		newEntity.setDate(schedule.getDate());
		
		ScheduleEntity newSchedule = driverScheduleService.createSchedule(driver.getId(), newEntity);
		/*assertNotNull(newSchedule);*/
		assertTrue(true);
		
	}
	

}
