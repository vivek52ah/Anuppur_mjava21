//package com.anuppur.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.anuppur.entity.DocumentUpload;
//
//
//
//public interface DocumentRepository  extends JpaRepository<DocumentUpload, Long> {
//
//	DocumentUpload findByDocumentName(String string);
//   
//	
//	List<DocumentUpload> findByDocumentId(Long id);
//
//
//	DocumentUpload findByWorkId(Long id, short s);
//
//
//}
package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.DocumentUpload;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentUpload, Long> {

    DocumentUpload findByDocumentName(String documentName);

    List<DocumentUpload> findByDocumentId(Long id);

    // FIXED
    DocumentUpload findByWorkId(Long id);

    List<DocumentUpload> findAllByWorkId(Long workId);

}
