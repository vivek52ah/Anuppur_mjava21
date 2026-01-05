package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.AsGeneratedCount;

public interface AsGeneratedCountRepository   extends JpaRepository<AsGeneratedCount, Long> {

	AsGeneratedCount findByFinancialYear(String financialYear);
    
}
