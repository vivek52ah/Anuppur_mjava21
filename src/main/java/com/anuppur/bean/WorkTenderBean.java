package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class WorkTenderBean {

	private Long id;
	private Long workId;
	private Long workStatusId;
	private Long tenderFileId;
	private Long uLoiId;
	private Long uAId;
	private Long uploadAgreement;
	private Long workRequestStatusId;
	private Long flag;
	private Integer count;

	private String fileStatus;

	private Long drawingId;

	private String drawCreatedBy;
	private String drawingStatus;

	private String secureAmtStatus;

	private String startDate;

	private String endDate;

	private String tenderCalledDate;

	private String tenderReceivedDate;

	private String reTenderDate;

	private String loaIssuedDate;

	public String getTenderCalledDate() {
		return tenderCalledDate;
	}

	public void setTenderCalledDate(String tenderCalledDate) {
		this.tenderCalledDate = tenderCalledDate;
	}

	public String geteTenderNo() {
		return eTenderNo;
	}

	public void seteTenderNo(String eTenderNo) {
		this.eTenderNo = eTenderNo;
	}

	private String eTenderNo;

	public String getDrawingStatus() {
		return drawingStatus;
	}

	public void setDrawingStatus(String drawingStatus) {
		this.drawingStatus = drawingStatus;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(Long drawingId) {
		this.drawingId = drawingId;
	}

	public String getDrawCreatedBy() {
		return drawCreatedBy;
	}

	public void setDrawCreatedBy(String drawCreatedBy) {
		this.drawCreatedBy = drawCreatedBy;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	private Long sorYear = null;

	public Long getSorYear() {
		return sorYear;
	}

	public void setSorYear(Long sorYear) {
		this.sorYear = sorYear;
	}

	private String rateStatus;

	public String getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public Long getTenderFileId() {
		return tenderFileId;
	}

	public void setTenderFileId(Long tenderFileId) {
		this.tenderFileId = tenderFileId;
	}

	private String workStatusNameE;
	private String workOrderDate;
	private BigDecimal tenderPercentage;
	private BigDecimal contractAmount;
	private BigDecimal pacAmount;

	public BigDecimal getPacAmount() {
		return pacAmount;
	}

	public void setPacAmount(BigDecimal pacAmount) {
		this.pacAmount = pacAmount;
	}

	private BigDecimal contractTenure;
	private String workCompletionDate;
	private String remarks;
	private String status;
	private MultipartFile pac;
	private MultipartFile ldtul;
	private MultipartFile uploadAgreementforWork;
	

	public MultipartFile getLdtul() {
		return ldtul;
	}

	public void setLdtul(MultipartFile ldtul) {
		this.ldtul = ldtul;
	}

	public MultipartFile getUploadAgreementforWork() {
		return uploadAgreementforWork;
	}

	public void setUploadAgreementforWork(MultipartFile uploadAgreementforWork) {
		this.uploadAgreementforWork = uploadAgreementforWork;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
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

	public String getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
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

	public MultipartFile getPac() {
		return pac;
	}

	public void setPac(MultipartFile pac) {
		this.pac = pac;
	}

	public String getWorkStatusNameE() {
		return workStatusNameE;
	}

	public void setWorkStatusNameE(String workStatusNameE) {
		this.workStatusNameE = workStatusNameE;
	}

	public String getSecureAmtStatus() {
		return secureAmtStatus;
	}

	public void setSecureAmtStatus(String secureAmtStatus) {
		this.secureAmtStatus = secureAmtStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTenderReceivedDate() {
		return tenderReceivedDate;
	}

	public void setTenderReceivedDate(String tenderReceivedDate) {
		this.tenderReceivedDate = tenderReceivedDate;
	}

	public String getReTenderDate() {
		return reTenderDate;
	}

	public void setReTenderDate(String reTenderDate) {
		this.reTenderDate = reTenderDate;
	}

	public String getLoaIssuedDate() {
		return loaIssuedDate;
	}

	public void setLoaIssuedDate(String loaIssuedDate) {
		this.loaIssuedDate = loaIssuedDate;
	}

	public Long getuLoiId() {
		return uLoiId;
	}

	public void setuLoiId(Long uLoiId) {
		this.uLoiId = uLoiId;
	}

	public Long getUploadAgreement() {
		return uploadAgreement;
	}

	public void setUploadAgreement(Long uploadAgreement) {
		this.uploadAgreement = uploadAgreement;
	}

	public Long getuAId() {
		return uAId;
	}

	public void setuAId(Long uAId) {
		this.uAId = uAId;
	}
	
	
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	private String workName;

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	private String workNo;

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	
	private Long implementationAgency;

	public Long getImplementationAgency() {
		return implementationAgency;
	}

	public void setImplementationAgency(Long implementationAgency) {
		this.implementationAgency = implementationAgency;
	}
	
	private Long financialYear;

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}
	
	
}
