package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.WorkGeoLocation;

@Repository
public interface WorkGeoLocationRepository  extends JpaRepository<WorkGeoLocation, Long>{

	List<WorkGeoLocation> findByworkId(Long workId);

	boolean existsByWorkId(Long id);

	
	
	
	
}
