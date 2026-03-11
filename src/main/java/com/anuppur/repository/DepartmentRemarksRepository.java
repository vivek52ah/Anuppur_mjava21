package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.DepartmentRemarks;

@Repository
public interface DepartmentRemarksRepository extends JpaRepository<DepartmentRemarks, Long>{

	List<DepartmentRemarks> findByworkIdAndEnabled(Long long1, short s);

	List<DepartmentRemarks> findByWorkId(Long id);
	
	@Query(value = "SELECT created_time FROM department_remarks WHERE id = :id", nativeQuery = true)
	String findCreatedDateByWorkId(@Param("id") Long id);
	
}
