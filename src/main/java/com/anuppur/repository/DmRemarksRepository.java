package com.anuppur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.DmRemarks;

public interface DmRemarksRepository extends JpaRepository<DmRemarks, Long>{

	List<DmRemarks> findByworkId(Long long1);

	
	@Query(value = "SELECT created_time FROM dm_remarks WHERE id = :id", nativeQuery = true)
	String findCreatedDateByWorkId(@Param("id") Long id);


	List<DmRemarks> findByworkIdAndEnabled(Long long1, Short s);


	List<DmRemarks> findByWorkId(Long valueOf);

	@Query(value = "SELECT * FROM dm_remarks WHERE document_id = :documentId AND enabled = 1 LIMIT 1", nativeQuery = true)
	Optional<DmRemarks> findEnabledByDocumentId(@Param("documentId") Long documentId);

	List<DmRemarks> findByEnabled(Short enabled);

	
}
