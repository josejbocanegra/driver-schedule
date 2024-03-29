package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.driverschedule.services.DriverServiceService;
import com.driverschedule.utils.DateService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({DriverServiceService.class, DateService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverServiceServiceTest {
	
	@Autowired
	private DriverServiceService driverServiceService;

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
	void testCreateSchedule() throws EntityNotFoundException, IllegalOperationException {
		
		ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
		service.setId(null);
		service.setDragLat(10);
		service.setDragLng(10);
		service.setDropLat(10);
		service.setDropLng(10);
		entityManager.persist(service);
		
		availableSchedule.setService(service);
		
		ScheduleEntity newSchedule = driverServiceService.createService(driver.getId(), availableSchedule);
		
		assertNotNull(newSchedule);
	}
	
	@Test
	void testCreateScheduleInvalidDriver() {
		assertThrows(EntityNotFoundException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(0L, availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDragLat1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(-10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDragLat2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(150);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDragLng1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(-150);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDragLng2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(150);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDropLat1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(-150);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDropLat2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(150);
			service.setDropLng(10);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDropLng1() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(-150);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidDropLng2() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(150);
			entityManager.persist(service);
			
			availableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), availableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleSlotNotAvailable() {
		assertThrows(IllegalOperationException.class, ()->{
			ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
			
			service.setId(null);
			service.setDragLat(10);
			service.setDragLng(10);
			service.setDropLat(10);
			service.setDropLng(10);
			entityManager.persist(service);
			
			nonAvailableSchedule.setService(service);
			
			driverServiceService.createService(driver.getId(), nonAvailableSchedule);
		});
	}
	
	@Test
	void testCreateScheduleNoSlot() {
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
			
			driverServiceService.createService(driver.getId(), newSchedule);
		});
	}
}
