package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkHead;

public interface WorkHeadRepository extends JpaRepository<WorkHead, Long> {

	List<WorkHead> findByEnabled(Short isEnabled);
	
	List<WorkHead> findByEnabledAndPriorityType(Short isEnabled, Integer priorityType);
	

	WorkHead findByHeadName(Long headName);

	Page<WorkHead> findByHeadNameContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	Page<WorkHead> findByEnabled(Pageable pageable, short s);

	WorkHead findByHeadNameAndEnabled(String workHeadName, short s);
}
