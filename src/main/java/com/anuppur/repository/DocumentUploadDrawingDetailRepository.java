package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.DocumentUploadDrawingDetail;


public interface DocumentUploadDrawingDetailRepository extends JpaRepository<DocumentUploadDrawingDetail, Long>{

	DocumentUploadDrawingDetail findByWorkId(Long id);

	List<DocumentUploadDrawingDetail> findAllByWorkId(Long workId);

}
