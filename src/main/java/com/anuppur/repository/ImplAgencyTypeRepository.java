package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.ImplementationAgencyType;

public interface ImplAgencyTypeRepository extends JpaRepository<ImplementationAgencyType, Long> {

	ImplementationAgencyType findByImplAgencyType(String implAgencyType);

}
