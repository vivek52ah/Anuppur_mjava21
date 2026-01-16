package com.anuppur.bean;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;



public class DocumentUploadDrawingDetailBean {
	
	
	private Long id;

	
	private String documentName;
	
	
	
	private MultipartFile drawingFile;
	

	
	private String drawingStatus;
	
	


	public MultipartFile getDrawingFile() {
		return drawingFile;
	}


	public void setDrawingFile(MultipartFile drawingFile) {
		this.drawingFile = drawingFile;
	}


	public String getDrawingStatus() {
		return drawingStatus;
	}


	public void setDrawingStatus(String drawingStatus) {
		this.drawingStatus = drawingStatus;
	}


	public final Long getId() {
		return id;
	}


	public final void setId(Long id) {
		this.id = id;
	}


	public String getDocumentName() {
		return documentName;
	}


	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	

	public Short getEnabled() {
		return enabled;
	}


	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Long getWorkId() {
		return workId;
	}


	public void setWorkId(Long workId) {
		this.workId = workId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	private Short enabled;


	private Date createdDate;

	
	private Long workId;
	
	
	private String status;
	
	
	


}
