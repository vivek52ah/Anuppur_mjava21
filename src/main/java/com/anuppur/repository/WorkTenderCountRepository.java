package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.WorkTenderCount;

public interface WorkTenderCountRepository extends JpaRepository<WorkTenderCount, Long> {

	List<WorkTenderCount> findByWorkIdAndStatusId(Long workId, Long status);

	List<WorkTenderCount> findByWorkIdAndStatus(Long workId, String workStatus);
	
	
 
}


