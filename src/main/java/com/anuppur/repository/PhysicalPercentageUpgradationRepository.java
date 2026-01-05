package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.PhysicalPercentageUpgradation;
import com.anuppur.entity.WorkSubStatus;

public interface PhysicalPercentageUpgradationRepository extends JpaRepository<PhysicalPercentageUpgradation, Long>{

	PhysicalPercentageUpgradation findByWorkSubStatus(WorkSubStatus workSubStatus);

}
