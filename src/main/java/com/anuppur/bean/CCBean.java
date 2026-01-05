package com.anuppur.bean;

import org.springframework.web.multipart.MultipartFile;

public class CCBean {

	private Integer index;

	private Long id;

	private Long workId;
	
	private String workNo; 

	private String ccNo;

	private String ccDate;

	private MultipartFile uploadCC;

	private String workStatus;

	private Long workStatusId;

	private Long ccFileId;

	private Long workRequestStatusId;
	
	private String dateHandOver;
	
	private Long paymentStatus;
	
	
	
	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDateHandOver() {
		return dateHandOver;
	}

	public void setDateHandOver(String dateHandOver) {
		this.dateHandOver = dateHandOver;
	}

	public String getHandoverRemarks() {
		return handoverRemarks;
	}

	public void setHandoverRemarks(String handoverRemarks) {
		this.handoverRemarks = handoverRemarks;
	}

	private String handoverRemarks;

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public Long getCcFileId() {
		return ccFileId;
	}

	public void setCcFileId(Long ccFileId) {
		this.ccFileId = ccFileId;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	private String remarks;

	private String status;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

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

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	
}
