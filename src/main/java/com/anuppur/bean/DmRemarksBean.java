package com.anuppur.bean;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.web.multipart.MultipartFile;

import com.anuppur.entity.DocumentUpload;

public class DmRemarksBean {

	
	private Long id;
	
	private String remark;
	
	 private Integer workId;
   
	 private MultipartFile dmattachment;
	 
	 private Long documentId;
	 
	 private String documentPath;
	 
	 
	 public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	private Integer Index;
	 
	 
	 private String createdDate;
	 
	 
	 

	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getIndex() {
		return Index;
	}

	public void setIndex(Integer index) {
		Index = index;
	}

	public MultipartFile getDmattachment() {
		return dmattachment;
	}

	public void setDmattachment(MultipartFile dmattachment) {
		this.dmattachment = dmattachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	
	 
	 
}
