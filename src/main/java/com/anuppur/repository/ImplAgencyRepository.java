package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.ImplementationAgency;

public interface ImplAgencyRepository extends JpaRepository<ImplementationAgency, Long> {

	List<ImplementationAgency> findByEnabled(Short isEnabled);

	//ImplementationAgency findByImplAgencyname(String implAgencyname);

	Page<ImplementationAgency> findByImplAgencynameContainingAndEnabled(Pageable pageable, String searchParameter,
			short s);

	Page<ImplementationAgency> findByEnabled(Pageable pageable, short s);

	long countByEnabled(short s);

	ImplementationAgency findByImplAgencynameAndEnabled(String implementationAgencyNameE, short s);
	
	ImplementationAgency findByImplAgencyname(String implementationAgencyNameE);

	List<ImplementationAgency> findByImplAgencyTypeAndEnabled(Long implAgencyTypeId, short s);

	boolean existsByimplAgencyname(String implementationAgencyNameE);

	Page<ImplementationAgency> findByimplAgencynameContainingAndEnabled(Pageable pageable, String searchBoxVal,
			Short enabled);

	//boolean implAgencyname(String implementationAgencyNameE);

	
	ImplementationAgency findByImplementationAgencyId(Long implementationAgency);

	boolean existsByimplAgencynameAndEnabled(String implementationAgencyNameE, Short s);
	
}
