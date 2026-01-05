package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.LocationPoints;

@Repository
public interface pointsrepository  extends JpaRepository<LocationPoints, Long>{

	List<LocationPoints> findAllByworkID(Long workId);

}
