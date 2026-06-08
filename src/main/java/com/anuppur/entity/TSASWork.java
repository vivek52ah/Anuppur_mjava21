package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.anuppur.entity.DocumentUpload;

import lombok.Data;

@Entity
@Table(name = "t_work_ts")

public  class TSASWork  extends Auditable  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public TSASWork() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	//@Column(name = "work_id")
	//private Long workId;
	@JoinColumn(name = "work_id", referencedColumnName = "id")
    @OneToOne
	private Work work;
	
	@Column(name="ts_no")
	private String tsNo;
	
	@Column(name="ts_date")
	private String tsDate;
	
	@Column(name="ts_amt")
	private BigDecimal tsAmt;
	
	/*
	 * @Column(name = "ts_document_upload") private String tsDocumentUpload;
	 */
	
	@JoinColumn(name = "ts_document_upload", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadTechnical;
	
	@Column(name="ts_remarks")
	private String tsRemarks;
	
	@Column(name="as_no")
	private String asNo;
	
	@Column(name="as_date")
	private String asDate;
	
	@Column(name="as_amt")
	private BigDecimal asAmt;
	
	@Column(name="work_request_status_id")
	private Long workRequestStatusId;
	
	
	@Column(name = "ts_as_status")
	private String tsAsSataus;
	
	/*
	 * @Column(name = "as_document_upload") private String asDocumentUpload;
	 */
	
	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	@JoinColumn(name = "as_document_upload", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadAdministration;
	
	@Column(name="as_remarks")
	private String asRemarks;
	
	@Column(name="work_status")
	private Long workStatus;
	
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DocumentUpload getDocumentUploadTechnical() {
		return documentUploadTechnical;
	}

	public void setDocumentUploadTechnical(DocumentUpload documentUploadTechnical) {
		this.documentUploadTechnical = documentUploadTechnical;
	}

	public DocumentUpload getDocumentUploadAdministration() {
		return documentUploadAdministration;
	}

	public void setDocumentUploadAdministration(DocumentUpload documentUploadAdministration) {
		this.documentUploadAdministration = documentUploadAdministration;
	}

	public String getTsAsSataus() {
		return tsAsSataus;
	}

	public void setTsAsSataus(String tsAsSataus) {
		this.tsAsSataus = tsAsSataus;
	}
	
	
	

}
