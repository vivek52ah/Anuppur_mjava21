package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class TSASWorkRevisedBeanForPowerBI {

	private Long id;
	private Long workId;
	private Long tsAsId;
	private String revisedStatus;
	
	
	private String rvOrderNo;
	private String rvOrderDate;
	private BigDecimal rvAmt;
	private MultipartFile rvDocumentUpload;
	private Long rvFileId;
	
	//private String fileName;
	private Long workRequestStatusId;
//	private Long flag;
	
	private String rvRemarks;
	
	private String workStatus;
	private String status;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
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
	public Long getTsAsId() {
		return tsAsId;
	}
	public void setTsAsId(Long tsAsId) {
		this.tsAsId = tsAsId;
	}
	public String getRevisedStatus() {
		return revisedStatus;
	}
	public void setRevisedStatus(String revisedStatus) {
		this.revisedStatus = revisedStatus;
	}
	public String getRvOrderNo() {
		return rvOrderNo;
	}
	public void setRvOrderNo(String rvOrderNo) {
		this.rvOrderNo = rvOrderNo;
	}
	public String getRvOrderDate() {
		return rvOrderDate;
	}
	public void setRvOrderDate(String rvOrderDate) {
		this.rvOrderDate = rvOrderDate;
	}
	public BigDecimal getRvAmt() {
		return rvAmt;
	}
	public void setRvAmt(BigDecimal rvAmt) {
		this.rvAmt = rvAmt;
	}
	public MultipartFile getRvDocumentUpload() {
		return rvDocumentUpload;
	}
	public void setRvDocumentUpload(MultipartFile rvDocumentUpload) {
		this.rvDocumentUpload = rvDocumentUpload;
	}
	public Long getRvFileId() {
		return rvFileId;
	}
	public void setRvFileId(Long rvFileId) {
		this.rvFileId = rvFileId;
	}
	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}
	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}
	public String getRvRemarks() {
		return rvRemarks;
	}
	public void setRvRemarks(String rvRemarks) {
		this.rvRemarks = rvRemarks;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	
}
