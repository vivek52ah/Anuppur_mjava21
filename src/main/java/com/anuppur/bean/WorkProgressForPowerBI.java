package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.List;

import com.anuppur.entity.DocumentUploadWorkProgress;

public class WorkProgressForPowerBI {
	
	private Long id;
	private Long workId;
	private Long workStatusId;
	private Long workSubDelayReasonId;
	private String workStatus;
	private Integer workSubStatusId;
//	private Integer size;
	private Integer perc;
//	private Long documentUploadWorkImage;
//	private String workUploadImageUrl;
//	private List<DocumentUploadWorkProgress> listDocument;
	
	private String workSubStatusNameE;
	private String otherReasonDelay;
	private String actionTakenDelay;
	private String stipulatedDateCompleted;
	private String likelyDateCompleted;
	private String dateCompletion;
	private String dateHandOver;
	private String remarks;
	private BigDecimal expensessUptoMarch;
	private BigDecimal expensessCurrentFy;
	private BigDecimal totalExpensess;
	private String status;
	private Long workRequestStatusId;
	private String createBy;
	private String createDate;
	private String modifiyBy;
	private String modifiyDate;
	
	
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
	public Long getWorkStatusId() {
		return workStatusId;
	}
	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}                                                                                                                                            
	public Long getWorkSubDelayReasonId() {
		return workSubDelayReasonId;
	}
	public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
		this.workSubDelayReasonId = workSubDelayReasonId;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public Integer getWorkSubStatusId() {
		return workSubStatusId;
	}
	public void setWorkSubStatusId(Integer workSubStatusId) {
		this.workSubStatusId = workSubStatusId;
	}

	public Integer getPerc() {
		return perc;
	}
	public void setPerc(Integer perc) {
		this.perc = perc;
	}

	/*
	 * public Long getDocumentUploadWorkImage() { return documentUploadWorkImage; }
	 * public void setDocumentUploadWorkImage(Long documentUploadWorkImage) {
	 * this.documentUploadWorkImage = documentUploadWorkImage; } public String
	 * getWorkUploadImageUrl() { return workUploadImageUrl; } public void
	 * setWorkUploadImageUrl(String workUploadImageUrl) { this.workUploadImageUrl =
	 * workUploadImageUrl; } public List<DocumentUploadWorkProgress>
	 * getListDocument() { return listDocument; } public void
	 * setListDocument(List<DocumentUploadWorkProgress> listDocument) {
	 * this.listDocument = listDocument; }
	 */
	public String getWorkSubStatusNameE() {
		return workSubStatusNameE;
	}
	public void setWorkSubStatusNameE(String workSubStatusNameE) {
		this.workSubStatusNameE = workSubStatusNameE;
	}
	public String getOtherReasonDelay() {
		return otherReasonDelay;
	}
	public void setOtherReasonDelay(String otherReasonDelay) {
		this.otherReasonDelay = otherReasonDelay;
	}
	public String getActionTakenDelay() {
		return actionTakenDelay;
	}
	public void setActionTakenDelay(String actionTakenDelay) {
		this.actionTakenDelay = actionTakenDelay;
	}
	public String getStipulatedDateCompleted() {
		return stipulatedDateCompleted;
	}
	public void setStipulatedDateCompleted(String stipulatedDateCompleted) {
		this.stipulatedDateCompleted = stipulatedDateCompleted;
	}
	public String getLikelyDateCompleted() {
		return likelyDateCompleted;
	}
	public void setLikelyDateCompleted(String likelyDateCompleted) {
		this.likelyDateCompleted = likelyDateCompleted;
	}
	public String getDateCompletion() {
		return dateCompletion;
	}
	public void setDateCompletion(String dateCompletion) {
		this.dateCompletion = dateCompletion;
	}
	public String getDateHandOver() {
		return dateHandOver;
	}
	public void setDateHandOver(String dateHandOver) {
		this.dateHandOver = dateHandOver;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getExpensessUptoMarch() {
		return expensessUptoMarch;
	}
	public void setExpensessUptoMarch(BigDecimal expensessUptoMarch) {
		this.expensessUptoMarch = expensessUptoMarch;
	}
	public BigDecimal getExpensessCurrentFy() {
		return expensessCurrentFy;
	}
	public void setExpensessCurrentFy(BigDecimal expensessCurrentFy) {
		this.expensessCurrentFy = expensessCurrentFy;
	}
	public BigDecimal getTotalExpensess() {
		return totalExpensess;
	}
	public void setTotalExpensess(BigDecimal totalExpensess) {
		this.totalExpensess = totalExpensess;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}
	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifiyBy() {
		return modifiyBy;
	}
	public void setModifiyBy(String modifiyBy) {
		this.modifiyBy = modifiyBy;
	}
	public String getModifiyDate() {
		return modifiyDate;
	}
	public void setModifiyDate(String modifiyDate) {
		this.modifiyDate = modifiyDate;
	}
	
	

}
