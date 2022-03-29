package com.driverschedule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driverschedule.entities.AlfredEntity;

@Repository
public interface AlfredRepository extends JpaRepository<AlfredEntity, Long> {

}
