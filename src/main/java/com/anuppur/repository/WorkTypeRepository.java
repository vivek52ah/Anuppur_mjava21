package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.WorkType;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long>

{

	
	


	@Query(value = "SELECT * FROM mst_work_type WHERE work_type_name_e = :workTypeNameE LIMIT 1", nativeQuery = true)
	WorkType findByWorkTypeNameE(@Param("workTypeNameE") Long workTypeNameE);

	Page<WorkType> findByEnabled(Pageable pageable, Short enabled);

	Page<WorkType> findByWorkTypeNameEContainingAndEnabled(Pageable pageable, String searchParameter, Short enabled);

	List<WorkType> findByEnabled(Short s);

	Boolean existsByworkTypeNameE(String workTypeNameE);

	boolean existsByworkTypeNameEAndEnabled(String workTypeNameE, Short enabled);


}
