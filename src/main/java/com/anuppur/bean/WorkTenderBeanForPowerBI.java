package com.anuppur.bean;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class WorkTenderBeanForPowerBI {

	private Long id;
	private Long workId;
	private String workOrderDate;
	private MultipartFile pac;
	private BigDecimal tenderPercentage;
	private BigDecimal contractAmount;
	private BigDecimal contractTenure;
	private String workCompletionDate;
	private String remarks;
	private String status;
	private Long workStatus;
	private Long workRequestStatusId;
	private String rateStatus;
	private BigDecimal pacAmount;
	private Long sorYear;
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
	public String getWorkOrderDate() {
		return workOrderDate;
	}
	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
	public MultipartFile getPac() {
		return pac;
	}
	public void setPac(MultipartFile pac) {
		this.pac = pac;
	}
	public BigDecimal getTenderPercentage() {
		return tenderPercentage;
	}
	public void setTenderPercentage(BigDecimal tenderPercentage) {
		this.tenderPercentage = tenderPercentage;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getContractTenure() {
		return contractTenure;
	}
	public void setContractTenure(BigDecimal contractTenure) {
		this.contractTenure = contractTenure;
	}
	public String getWorkCompletionDate() {
		return workCompletionDate;
	}
	public void setWorkCompletionDate(String workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
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
	public Long getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}
	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}
	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}
	public String getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}
	public BigDecimal getPacAmount() {
		return pacAmount;
	}
	public void setPacAmount(BigDecimal pacAmount) {
		this.pacAmount = pacAmount;
	}
	public Long getSorYear() {
		return sorYear;
	}
	public void setSorYear(Long sorYear) {
		this.sorYear = sorYear;
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
