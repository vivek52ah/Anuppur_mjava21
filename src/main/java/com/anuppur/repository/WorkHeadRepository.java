package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.WorkHead;

public interface WorkHeadRepository extends JpaRepository<WorkHead, Long> {

	List<WorkHead> findByEnabled(Short isEnabled);
	
	List<WorkHead> findByEnabledAndPriorityType(Short isEnabled, Integer priorityType);
	

	@Query(value = "SELECT * FROM mst_work_head WHERE head_name = :headName LIMIT 1", nativeQuery = true)
	WorkHead findByHeadName(@Param("headName") Long headName);

	Page<WorkHead> findByHeadNameContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	Page<WorkHead> findByEnabled(Pageable pageable, short s);

	WorkHead findByHeadNameAndEnabled(String workHeadName, short s);
}
