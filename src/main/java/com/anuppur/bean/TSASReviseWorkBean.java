package com.anuppur.bean;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class TSASReviseWorkBean {
	
	private Integer index;
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	private Long id;
	private Long workId;
	private Long tsAsId;
	private String revisedStatus;
	private String tsAsSataus;
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

	private String rvOrderNo;
	private String rvOrderDate;
	private BigDecimal rvAmt;
	private MultipartFile rvDocumentUpload;
	private Long rvFileId;
	
	private String fileName;
	private Long workRequestStatusId;
	private Long flag;
	
	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	private String typeDoc;

	public String getTypeDoc() {
		return typeDoc;
	}

	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

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

	

	private String rvRemarks;
	
	private String workStatus;
	private String status;

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

	

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
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

	public String getRvRemarks() {
		return rvRemarks;
	}

	public void setRvRemarks(String rvRemarks) {
		this.rvRemarks = rvRemarks;
	}

	public String getTsAsSataus() {
		return tsAsSataus;
	}

	public void setTsAsSataus(String tsAsSataus) {
		this.tsAsSataus = tsAsSataus;
	}

	

}
