package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkSubType;
import com.anuppur.entity.WorkType;


public interface WorkSubTypeRepository  extends JpaRepository<WorkSubType, Long>{
	
	List<WorkSubType> findByEnabled(Short isEnabled);

	WorkSubType findByWorkSubTypeNameE(String workSubTypeNameE);

	boolean existsByworkSubTypeNameE(String workSubTypeNameE);

	Page<WorkSubType> findByworkSubTypeNameEContainingAndEnabled(Pageable pageable, String searchParameter,
			Short enabled);

	Page<WorkSubType> findByEnabled(Pageable pageable, Short enabled);

	
	Page<WorkSubType> findByWorkSubTypeNameEContainingAndEnabled(Pageable pageable, String searchParameter, Short enabled);

	boolean existsByworkSubTypeNameEAndEnabled(String workSubTypeNameE, Short enabled);


}
