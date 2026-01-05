package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkType;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long>

{

	
	


	WorkType findByWorkTypeNameE(Long workTypeNameE);

	Page<WorkType> findByEnabled(Pageable pageable, Short enabled);

	Page<WorkType> findByWorkTypeNameEContainingAndEnabled(Pageable pageable, String searchParameter, Short enabled);

	List<WorkType> findByEnabled(Short s);

	Boolean existsByworkTypeNameE(String workTypeNameE);

	boolean existsByworkTypeNameEAndEnabled(String workTypeNameE, Short enabled);


}
