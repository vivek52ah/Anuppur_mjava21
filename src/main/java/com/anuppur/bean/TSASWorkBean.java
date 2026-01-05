package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class TSASWorkBean {

	private Long id;
	private Long workId;
	private String tsNo;
	private String tsDate;
	private BigDecimal tsAmt;
	private MultipartFile tsDocumentUpload;
	private Long tsFileId;
	private Long asFileId;
	private String fileName;
	private Long workRequestStatusId;
	private String tsAsSataus;
	private String createBy;
	private String createDate;
	private String modifiyBy;
	private String modifiyDate;
	private String tsRemarks;
	private String asNo;
	private String asDate;
	private BigDecimal asAmt;
	private MultipartFile asDocumentUpload;
	private String asRemarks;
	private Long workStatus;
	private String status;

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getAsFileId() {
		return asFileId;
	}

	public void setAsFileId(Long asFileId) {
		this.asFileId = asFileId;
	}

	public Long getTsFileId() {
		return tsFileId;
	}

	public void setTsFileId(Long tsFileId) {
		this.tsFileId = tsFileId;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTsNo() {
		return tsNo;
	}

	public void setTsNo(String tsNo) {
		this.tsNo = tsNo;
	}

	public BigDecimal getTsAmt() {
		return tsAmt;
	}

	public void setTsAmt(BigDecimal tsAmt) {
		this.tsAmt = tsAmt;
	}

	public String getTsRemarks() {
		return tsRemarks;
	}

	public void setTsRemarks(String tsRemarks) {
		this.tsRemarks = tsRemarks;
	}

	public String getAsNo() {
		return asNo;
	}

	public void setAsNo(String asNo) {
		this.asNo = asNo;
	}

	public String getTsDate() {
		return tsDate;
	}

	public void setTsDate(String tsDate) {
		this.tsDate = tsDate;
	}

	public String getAsDate() {
		return asDate;
	}

	public void setAsDate(String asDate) {
		this.asDate = asDate;
	}

	public BigDecimal getAsAmt() {
		return asAmt;
	}

	public void setAsAmt(BigDecimal asAmt) {
		this.asAmt = asAmt;
	}

	public String getAsRemarks() {
		return asRemarks;
	}

	public void setAsRemarks(String asRemarks) {
		this.asRemarks = asRemarks;
	}

	public Long getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}

	public MultipartFile getTsDocumentUpload() {
		return tsDocumentUpload;
	}

	public void setTsDocumentUpload(MultipartFile tsDocumentUpload) {
		this.tsDocumentUpload = tsDocumentUpload;
	}

	public MultipartFile getAsDocumentUpload() {
		return asDocumentUpload;
	}

	public void setAsDocumentUpload(MultipartFile asDocumentUpload) {
		this.asDocumentUpload = asDocumentUpload;
	}

	public String getTsAsSataus() {
		return tsAsSataus;
	}

	public void setTsAsSataus(String tsAsSataus) {
		this.tsAsSataus = tsAsSataus;
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
