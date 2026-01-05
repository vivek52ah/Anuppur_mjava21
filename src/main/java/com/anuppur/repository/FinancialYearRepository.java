package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anuppur.entity.FinancialYear;

public interface FinancialYearRepository  extends JpaRepository<FinancialYear, Long> {
	
	List<FinancialYear> findByEnabled(Short isEnabled);
	
	FinancialYear findByFinancialYearAndEnabled(String fyName, Short isEnabled);

	List<FinancialYear> findByEnabledOrderByIdDesc(Short enabled);
	
	@Query(value  = "SELECT financial_year FROM mst_financial_year m ORDER BY m.id DESC  limit 1 ", nativeQuery = true)
    String findMostRecentFinancialYear();
    
}
