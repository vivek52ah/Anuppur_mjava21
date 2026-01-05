package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Division;

public interface DivisionRepository extends JpaRepository<Division, Long> {

	List<Division> findByEnabled(Short isEnabled);
	
	Division findByDivisionNameAndEnabled(String divisionName, Short isEnabled);

	List<Division> findByDivisionIdAndEnabled(Long divisionCode, short s);
}
