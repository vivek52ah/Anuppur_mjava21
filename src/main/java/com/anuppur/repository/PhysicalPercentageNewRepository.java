package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.District;
import com.anuppur.entity.PhysicalPercentageNew;
import com.anuppur.entity.WorkSubStatus;

public interface PhysicalPercentageNewRepository extends JpaRepository<PhysicalPercentageNew, Long>{


	PhysicalPercentageNew findByWorkSubStatus(WorkSubStatus workSubStatus);

}
