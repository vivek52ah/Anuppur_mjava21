package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.anuppur.entity.DocumentUploadWorkProgress;

public class WorkProgressBean {

	private Long id;
	private Long workId;
	private Long workStatusId;
	private Long workSubDelayReasonId;
	private String workStatus;
	private Integer workSubStatusId;
	private Integer size;
	private Integer perc;
	private Long documentUploadWorkImage;
	private String workUploadImageUrl;
	private List<DocumentUploadWorkProgress> listDocument;
	

	public Long getDocumentUploadWorkImage() {
		return documentUploadWorkImage;
	}

	public void setDocumentUploadWorkImage(Long documentUploadWorkImage) {
		this.documentUploadWorkImage = documentUploadWorkImage;
	}

	public String getWorkUploadImageUrl() {
		return workUploadImageUrl;
	}

	public void setWorkUploadImageUrl(String workUploadImageUrl) {
		this.workUploadImageUrl = workUploadImageUrl;
	}

	public Integer getPerc() {
		return perc;
	}

	public void setPerc(Integer perc) {
		this.perc = perc;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

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

	private Long year;
	
	
	private Long month;

	private Long financialYear;
	
	private String monthName;

	private String financialYearName;

	private String monthlyProgress;

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	private Long total;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	private String status;
	private MultipartFile progressDocumentUpload;

	private Long workRequestStatusId;

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	private Long proFileId;

	public Long getId() {
		return id;
	}

	public Long getProFileId() {
		return proFileId;
	}

	public void setProFileId(Long proFileId) {
		this.proFileId = proFileId;
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

	public MultipartFile getProgressDocumentUpload() {
		return progressDocumentUpload;
	}

	public void setProgressDocumentUpload(MultipartFile progressDocumentUpload) {
		this.progressDocumentUpload = progressDocumentUpload;
	}

	public Long getWorkSubDelayReasonId() {
		return workSubDelayReasonId;
	}

	public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
		this.workSubDelayReasonId = workSubDelayReasonId;
	}

	private Long workProgressCount;

	public Long getWorkProgressCount() {
		return workProgressCount;
	}

	public void setWorkProgressCount(Long workProgressCount) {
		this.workProgressCount = workProgressCount;
	}

	public List<DocumentUploadWorkProgress> getListDocument() {
		return listDocument;
	}

	public void setListDocument(List<DocumentUploadWorkProgress> list) {
		this.listDocument = list;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public String getMonthlyProgress() {
		return monthlyProgress;
	}

	public void setMonthlyProgress(String monthlyProgress) {
		this.monthlyProgress = monthlyProgress;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getFinancialYearName() {
		return financialYearName;
	}

	public void setFinancialYearName(String financialYearName) {
		this.financialYearName = financialYearName;
	}
	
	
}
