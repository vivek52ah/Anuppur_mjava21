package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "t_work_tender")
public class WorkTender extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/*
	 * @Column(name="WORK_ID") private Long workId;
	 */

	@JoinColumn(name = "work_id", referencedColumnName = "id")
	@OneToOne
	private Work work;

	@Column(name = "work_status")
	private Long workStatus;

	@Column(name = "work_order_date")
	private String workOrderDate;

	@Column(name = "tender_percentage")
	private BigDecimal tenderPercentage;

	@Column(name = "sor_year")
	private Long sorYear ;

	@Column(name = "security_amt_status")
	private String secureAmtStatus;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "end_date")
	private String endDate;

	public Long getSorYear() {
		return sorYear;
	}

	public void setSorYear(Long sorYear) {
		this.sorYear = sorYear;
	}

	@Column(name = "contract_amount")
	private BigDecimal contractAmount;

	@Column(name = "pac_amount")
	private BigDecimal pacAmount;

	public BigDecimal getPacAmount() {
		return pacAmount;
	}

	public void setPacAmount(BigDecimal pacAmount) {
		this.pacAmount = pacAmount;
	}

	@Column(name = "contract_tenure")
	private BigDecimal contractTenure;

	@Column(name = "work_completion_date")
	private String workCompletionDate;

	@Column(name = "remarks")
	private String remarks;

	@JoinColumn(name = "pac", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUpload;
	
	@JoinColumn(name = "loi", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadLoi;
	
	@JoinColumn(name = "upload_agreement", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadUa;

	@Column(name = "status")
	private String status;

	@Column(name = "work_request_status_id")
	private Long workRequestStatusId;

	@Column(name = "rate_status")
	private String rateStatus;

	@Column(name = "tender_call_date")
	private String tenderCalledDate;

	@Column(name = "e_tender_number")
	private String eTenderNo;

	@Column(name = "tender_received_date")
	private String tenderReceivedDate;

	@Column(name = "re_tender_date")
	private String reTenderDate;

	@Column(name = "loa_issued_date")
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

	public String getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
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

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public Long getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public DocumentUpload getDocumentUpload() {
		return documentUpload;
	}

	public void setDocumentUpload(DocumentUpload documentUpload) {
		this.documentUpload = documentUpload;
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

	public DocumentUpload getDocumentUploadLoi() {
		return documentUploadLoi;
	}

	public void setDocumentUploadLoi(DocumentUpload documentUploadLoi) {
		this.documentUploadLoi = documentUploadLoi;
	}

	public DocumentUpload getDocumentUploadUa() {
		return documentUploadUa;
	}

	public void setDocumentUploadUa(DocumentUpload documentUploadUa) {
		this.documentUploadUa = documentUploadUa;
	}

	

}
