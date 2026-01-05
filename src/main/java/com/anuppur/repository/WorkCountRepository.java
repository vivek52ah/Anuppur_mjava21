package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkCount;

public interface WorkCountRepository  extends JpaRepository<WorkCount, Long> {
    
}
