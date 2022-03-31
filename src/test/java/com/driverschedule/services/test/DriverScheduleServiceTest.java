package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	private ScheduleEntity availableSchedule;
	private ScheduleEntity nonAvailableSchedule;
	
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
		availableSchedule.setIsAvailable(true);
		availableSchedule.setDriver(driver);
		availableSchedule.setService(null);
		entityManager.persist(availableSchedule);
		
		nonAvailableSchedule = factory.manufacturePojo(ScheduleEntity.class);
		nonAvailableSchedule.setId(null);
		nonAvailableSchedule.setIsAvailable(false);
		nonAvailableSchedule.setDriver(driver);
		nonAvailableSchedule.setService(null);
		entityManager.persist(nonAvailableSchedule);
	}
	
	@Test
	void testcreateSchedule() throws EntityNotFoundException, IllegalOperationException {
		
		ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
		service.setId(null);
		service.setDragLat(10);
		service.setDragLng(10);
		service.setDropLat(10);
		service.setDropLng(10);
		entityManager.persist(service);
		
		availableSchedule.setService(service);
		
		ScheduleEntity newSchedule = driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		
		assertNotNull(newSchedule);
	}
	
	@Test
	void testcreateScheduleInvalidDriver() {
		assertThrows(EntityNotFoundException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(0L, availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDragLat1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(-10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDragLat2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(150);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDragLng1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(-150);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDragLng2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(150);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDropLat1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(-150);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDropLat2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(150);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDropLng1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(-150);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleInvalidDropLng2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(150);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleSlotNotAvailable() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			nonAvailableSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), nonAvailableSchedule);
		});
	}
	
	@Test
	void testcreateScheduleNoSlot() {
		assertThrows(IllegalOperationException.class, ()->{
			
			entityManager.getEntityManager().createQuery("delete from ScheduleEntity").executeUpdate();
			
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setService(service);
			
			driverScheduleService.createSchedule(driver.getId(), newSchedule);
		});
	}
	
	@Test
	void testgetSchedules() throws EntityNotFoundException, IllegalOperationException {
		List<ScheduleEntity> schedules = driverScheduleService.getSchedules(driver.getId(), availableSchedule.getDate());
		assertNotNull(schedules);
	}
	
}
