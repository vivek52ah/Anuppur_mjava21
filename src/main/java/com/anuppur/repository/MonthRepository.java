package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Month;


public interface MonthRepository extends JpaRepository<Month, Long> {

	Month findByMonthName(String monthName);

	

}
