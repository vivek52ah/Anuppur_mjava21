package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.Schemes;
import com.anuppur.entity.WorkHead;

public interface SchemeRepository extends JpaRepository<Schemes, Long> {

	List<Schemes> findByEnabled(Short isEnabled);
	
	
	@Query(value = "SELECT * FROM mst_schemes WHERE scheme_name = :schemeName LIMIT 1", nativeQuery = true)
	Schemes findBySchemeName(@Param("schemeName") Long schemeName);

	Schemes findBySchemeNameAndEnabled(String schemeName, Short isEnabled);

	Page<Schemes> findByEnabled(Short enabled, Pageable pageable);

	Page<Schemes> findBySchemeNameContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	long countByEnabled(short s);

//	List<Schemes> findByEnabledOrderById(Short enabled);
	
	List<Schemes> findByEnabledOrderBySchemeNameDesc(Short isEnabled);
	
//	Page<Schemes> findByEnabled(Pageable pageable, short s);   

}
