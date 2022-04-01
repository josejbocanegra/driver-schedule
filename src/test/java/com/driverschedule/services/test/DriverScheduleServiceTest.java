package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
	void testCreateSchedule() throws EntityNotFoundException, IllegalOperationException {
		entityManager.getEntityManager().createQuery("delete from ScheduleEntity").executeUpdate();
		ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
		newSchedule.setId(null);
		newSchedule.setIsAvailable(false);
		newSchedule.setDriver(null);
		newSchedule.setService(null);
		entityManager.persist(newSchedule);

		ScheduleEntity newEntity = driverScheduleService.createSchedule(driver.getId(), newSchedule);
		assertNotNull(newEntity);
	}

	@Test
	void testCreateScheduleInvalidDriver() {
		assertThrows(EntityNotFoundException.class, ()->{
			entityManager.getEntityManager().createQuery("delete from ScheduleEntity").executeUpdate();
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setId(null);
			newSchedule.setIsAvailable(false);
			newSchedule.setDriver(null);
			newSchedule.setService(null);
			entityManager.persist(newSchedule);

			driverScheduleService.createSchedule(0L, newSchedule);
		});
	}
	
	@Test
	void testCreateScheduleInvalidSlot() {
		assertThrows(IllegalOperationException.class, ()->{
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setId(null);
			newSchedule.setIsAvailable(true);
			newSchedule.setService(null);
			newSchedule.setDate(availableSchedule.getDate());

			driverScheduleService.createSchedule(driver.getId(), newSchedule);
		});
	}

	@Test
	void testUpdateScheduleEmptySlog() throws EntityNotFoundException, IllegalOperationException {
		entityManager.getEntityManager().createQuery("delete from ScheduleEntity").executeUpdate();
		ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
		newSchedule.setId(null);
		newSchedule.setIsAvailable(false);
		newSchedule.setDriver(null);
		newSchedule.setService(null);
		entityManager.persist(newSchedule);

		ScheduleEntity newEntity = driverScheduleService.updateSchedule(driver.getId(), newSchedule.getId(), newSchedule);
		assertNotNull(newEntity);
	}

	@Test
	void testUpdateSchedule() throws EntityNotFoundException, IllegalOperationException {

		ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
		newSchedule.setId(null);
		newSchedule.setDate(availableSchedule.getDate());
		newSchedule.setIsAvailable(false);
		newSchedule.setDriver(null);
		newSchedule.setService(null);

		ScheduleEntity newEntity = driverScheduleService.updateSchedule(driver.getId(), availableSchedule.getId(), newSchedule);
		assertNotNull(newEntity);
	}

	@Test
	void testUpdateScheduleInvalidDriver() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setId(null);
			newSchedule.setDate(availableSchedule.getDate());
			newSchedule.setIsAvailable(false);
			newSchedule.setDriver(null);
			newSchedule.setService(null);
			driverScheduleService.updateSchedule(0L, availableSchedule.getId(), newSchedule);
		});
	}

	@Test
	void testUpdateScheduleInvalidSchedule() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setId(null);
			newSchedule.setDate(availableSchedule.getDate());
			newSchedule.setIsAvailable(false);
			newSchedule.setDriver(null);
			newSchedule.setService(null);
			driverScheduleService.updateSchedule(driver.getId(), 0L, newSchedule);
		});
	}

	@Test
	void testUpdateScheduleBussySlot() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(IllegalOperationException.class, ()->{
			ScheduleEntity newSchedule = factory.manufacturePojo(ScheduleEntity.class);
			newSchedule.setId(null);
			newSchedule.setDate(nonAvailableSchedule.getDate());
			newSchedule.setIsAvailable(false);
			newSchedule.setDriver(null);
			newSchedule.setService(null);
			driverScheduleService.updateSchedule(driver.getId(), availableSchedule.getId(), newSchedule);
		});
	}

	@Test
	void testGetSchedules() throws EntityNotFoundException, IllegalOperationException {
		List<ScheduleEntity> schedules = driverScheduleService.getSchedules(driver.getId(), availableSchedule.getDate());
		assertNotNull(schedules);
	}
	
	@Test
	void testGetSchedulesNullDate() throws EntityNotFoundException, IllegalOperationException {
		List<ScheduleEntity> schedules = driverScheduleService.getSchedules(driver.getId(), null);
		assertNotNull(schedules);
	}
	
	@Test
	void testGetSchedulesInvalidDriver() {
		assertThrows(EntityNotFoundException.class, ()->{
			driverScheduleService.getSchedules(0L, availableSchedule.getDate());
		});
	}
	
	@Test
	void testDeleteSchedule() throws EntityNotFoundException, IllegalOperationException {
		Long scheduleId = availableSchedule.getId();
		driverScheduleService.deleteSchedule(driver.getId(), availableSchedule.getId());
		ScheduleEntity storedSchedule = entityManager.find(ScheduleEntity.class, scheduleId);
		assertNull(storedSchedule);
	}
	
	@Test
	void testDeleteScheduleInvalidSchedule() {
		assertThrows(EntityNotFoundException.class, ()->{
			driverScheduleService.deleteSchedule(driver.getId(), 0L);
		});
	}
	
	@Test
	void testDeleteScheduleInvalidDriver() {
		assertThrows(EntityNotFoundException.class, ()->{
			driverScheduleService.deleteSchedule(0L, availableSchedule.getId());
		});
	}
}
