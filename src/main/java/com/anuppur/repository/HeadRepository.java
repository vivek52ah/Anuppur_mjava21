package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Head;


public interface HeadRepository extends JpaRepository<Head, Long> {
	
	List<Head> findByEnabled(Short isEnabled);

	Head findByHeadNameAndEnabled(String headName, Short isEnabled);

	Page<Head> findByEnabled(Short enabled, Pageable pageable);

	Page<Head> findByHeadNameContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	long countByEnabled(short s);

//	List<Head> findByEnabledOrderById(Short enabled);
	
	List<Head> findByEnabledOrderByHeadNameDesc(Short isEnabled);

	Page<Head> findByEnabled(Pageable pageable, short s);

}
