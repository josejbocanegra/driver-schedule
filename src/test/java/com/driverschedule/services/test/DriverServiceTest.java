package com.driverschedule.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import com.driverschedule.services.DriverLocationService;
import com.driverschedule.services.DriverService;
import com.driverschedule.utils.DistanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({DriverService.class, DriverLocationService.class, DistanceService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverServiceTest {
	
	@Autowired
	private DriverService driverService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();
	private List<DriverEntity> driverList = new ArrayList<>();
	
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
		for (int i = 0; i < 3; i++) {
			DriverEntity driverEntity = factory.manufacturePojo(DriverEntity.class);
			driverEntity.setId(null);
			entityManager.persist(driverEntity);
			driverList.add(driverEntity);
		}
	}
	
	@Test
	void testCreateDriver() {
		DriverEntity newEntity = factory.manufacturePojo(DriverEntity.class);
		DriverEntity result = driverService.createDriver(newEntity);
		assertNotNull(result);
		DriverEntity entity = entityManager.find(DriverEntity.class, result.getId());

		assertEquals(result.getId(), entity.getId());
		assertEquals(result.getDistance(), entity.getDistance());
		assertEquals(result.getLastUpdate(), entity.getLastUpdate());
		assertEquals(result.getLat(), entity.getLat());
		assertEquals(result.getLng(), entity.getLng());
		assertEquals(result.getName(), entity.getName());
	}
	
	@Test
	void testGetDrivers() throws JsonMappingException, JsonProcessingException {
		List<DriverEntity> drivers = driverService.getDrivers(null, null);
		assertEquals(driverList.size(), drivers.size());

		for (DriverEntity driverEntity : driverList) {
			boolean found = false;
			for (DriverEntity storedEntity : drivers) {
				if (driverEntity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
	}
	
	@Test
	void testGetDriversWithDistance() throws JsonMappingException, JsonProcessingException {
		List<DriverEntity> drivers = driverService.getDrivers("10", "10");
		assertEquals(driverList.size(), drivers.size());

		for (DriverEntity driverEntity : driverList) {
			boolean found = false;
			for (DriverEntity storedEntity : drivers) {
				if (driverEntity.getId().equals(storedEntity.getId())) {
					found = true;
				}
			}
			assertTrue(found);
		}
	}
}
