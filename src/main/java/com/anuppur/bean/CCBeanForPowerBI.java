package com.anuppur.bean;

import org.springframework.web.multipart.MultipartFile;

public class CCBeanForPowerBI {

	private Long id;

	private Long workId;

	private String ccNo;

	private String ccDate;

	private MultipartFile uploadCC;

	private String workStatus;

	private Long workStatusId;

	private Long ccFileId;

	private Long workRequestStatusId;
	
	private String dateHandOver;
	
	private Long paymentStatus;
	
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
	
	private String remarks;
	
	private String status;
	
	private String handRemarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getCcNo() {
		return ccNo;
	}

	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}

	public String getCcDate() {
		return ccDate;
	}

	public void setCcDate(String ccDate) {
		this.ccDate = ccDate;
	}

	public MultipartFile getUploadCC() {
		return uploadCC;
	}

	public void setUploadCC(MultipartFile uploadCC) {
		this.uploadCC = uploadCC;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Long getCcFileId() {
		return ccFileId;
	}

	public void setCcFileId(Long ccFileId) {
		this.ccFileId = ccFileId;
	}

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public String getDateHandOver() {
		return dateHandOver;
	}

	public void setDateHandOver(String dateHandOver) {
		this.dateHandOver = dateHandOver;
	}

	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHandRemarks() {
		return handRemarks;
	}

	public void setHandRemarks(String handRemarks) {
		this.handRemarks = handRemarks;
	}
	
}
