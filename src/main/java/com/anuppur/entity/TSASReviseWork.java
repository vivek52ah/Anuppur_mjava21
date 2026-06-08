package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_work_ts_revised")
public class TSASReviseWork  extends Auditable  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	/*
	  @Column(name = "work_id")
	   private Long workId;
	 */
	
	@ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;
	
	@Column(name = "ts_as_id")
	private Long tsAsId;
	
	@Column(name = "revised_status")
	private String revisedStatus;
	
	@Column(name ="type_doc")
	private String typeDoc;
	
	@JoinColumn(name = "rv_document_upload", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadRevised;
	
	@Column(name="rv_remarks")
	private String rvRemarks;
	
	@Column(name="rv_order_no")
	private String rvNo;
	
	@Column(name="rv_order_date")
	private String rvDate;
	
	@Column(name="rv_amt")
	private BigDecimal rvAmt;
	
	/*
	 * @Column(name="work_request_status_id") private Long workRequestStatusId;
	 */
	
	

	/*
	 * @Column(name="work_status") private String workStatus;
	 */
	
	@Column(name="ts_as_status")
	private String tsAsSataus;
	
	private String status;
	
	public DocumentUpload getDocumentUploadRevised() {
		return documentUploadRevised;
	}

	public void setDocumentUploadRevised(DocumentUpload documentUploadRevised) {
		this.documentUploadRevised = documentUploadRevised;
	}

	public String getRvRemarks() {
		return rvRemarks;
	}

	public void setRvRemarks(String rvRemarks) {
		this.rvRemarks = rvRemarks;
	}

	public String getRvNo() {
		return rvNo;
	}

	public void setRvNo(String rvNo) {
		this.rvNo = rvNo;
	}

	public String getRvDate() {
		return rvDate;
	}

	public void setRvDate(String rvDate) {
		this.rvDate = rvDate;
	}

	public BigDecimal getRvAmt() {
		return rvAmt;
	}

	public void setRvAmt(BigDecimal rvAmt) {
		this.rvAmt = rvAmt;
	}

	public String getTypeDoc() {
		return typeDoc;
	}

	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

	public TSASReviseWork() {
		super();
		// TODO Auto-generated constructor stub
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
	
	/*
	 * public Long getWorkRequestStatusId() { return workRequestStatusId; }
	 * 
	 * public void setWorkRequestStatusId(Long workRequestStatusId) {
	 * this.workRequestStatusId = workRequestStatusId; }
	 */



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

	/*
	 * public String getWorkStatus() { return workStatus; }
	 * 
	 * public void setWorkStatus(String workStatus) { this.workStatus = workStatus;
	 * }
	 */

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTsAsSataus() {
		return tsAsSataus;
	}

	public void setTsAsSataus(String tsAsSataus) {
		this.tsAsSataus = tsAsSataus;
	}

	
}
