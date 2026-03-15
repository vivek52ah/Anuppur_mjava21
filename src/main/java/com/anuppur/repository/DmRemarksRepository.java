package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.Block;
import com.anuppur.entity.DmRemarks;

public interface DmRemarksRepository extends JpaRepository<DmRemarks, Long>{

	List<DmRemarks> findByworkId(Long long1);

	
	@Query(value = "SELECT created_time FROM dm_remarks WHERE id = :id", nativeQuery = true)
	String findCreatedDateByWorkId(@Param("id") Long id);


	List<DmRemarks> findByworkIdAndEnabled(Long long1, Short s);


	List<DmRemarks> findByWorkId(Long valueOf);

	List<DmRemarks> findByEnabled(Short enabled);

	
}
