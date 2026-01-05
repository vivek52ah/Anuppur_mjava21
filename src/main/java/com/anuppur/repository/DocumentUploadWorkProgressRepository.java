package com.anuppur.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadWorkProgress;



public interface DocumentUploadWorkProgressRepository extends JpaRepository<DocumentUploadWorkProgress, Long> {
	
	DocumentUploadWorkProgress findByDocumentName(String string);
	
	List<DocumentUploadWorkProgress> findByDocumentId(Long id);
	
	DocumentUploadWorkProgress findByWorkId(Long id, short s);

	List<DocumentUploadWorkProgress> findByWorkId(Long workId);

	long countByEnabled(short s);

	List<DocumentUploadWorkProgress> findByWorkIdAndCreatedDate(Long workId, Date date);

	

}
