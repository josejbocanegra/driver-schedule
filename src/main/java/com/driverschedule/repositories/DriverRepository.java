package com.driverschedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driverschedule.entities.DriverEntity;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

}
