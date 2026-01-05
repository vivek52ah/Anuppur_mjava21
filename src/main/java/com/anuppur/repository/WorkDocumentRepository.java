package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Work;
import com.anuppur.entity.WorkDocument;

public interface WorkDocumentRepository  extends JpaRepository<WorkDocument, Long> {
    
	WorkDocument findByWork(Work work);
	List<WorkDocument> findByWorkId(Long long1);
	List<WorkDocument> findByWorkIdAndIsDeleted(Long id, short s);

	
}
