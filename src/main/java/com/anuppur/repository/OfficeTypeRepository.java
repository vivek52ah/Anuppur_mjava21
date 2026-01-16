package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.OfficeType;

public interface OfficeTypeRepository extends JpaRepository<OfficeType, String>{

	
	List<OfficeType> findByIdIn(List<Long> ids);
	OfficeType findById(Long officeTypeId);
}
