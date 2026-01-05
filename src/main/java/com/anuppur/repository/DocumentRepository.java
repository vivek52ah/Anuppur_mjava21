package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.DocumentUpload;



public interface DocumentRepository  extends JpaRepository<DocumentUpload, Long> {

	DocumentUpload findByDocumentName(String string);
   
	
	List<DocumentUpload> findByDocumentId(Long id);


	DocumentUpload findByWorkId(Long id, short s);


}
