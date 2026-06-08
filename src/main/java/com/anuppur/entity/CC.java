package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="t_work_cc")
public class CC extends Auditable  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * @Column(name="work_id") private Long workId;
	 */
	
	
	@JoinColumn(name = "work_id", referencedColumnName = "id")
    @OneToOne
	private Work work;

	@Column(name="cc_no")
	private String ccNo;
	
	@Column(name="cc_date")
	private String ccDate;
	
	@JoinColumn(name = "upload_cc", referencedColumnName = "ID")
	@OneToOne
	private DocumentUpload documentUploadCC;
 
	/*
	 * @Column(name="work_status") private String workStatus;
	 */
	
	@Column(name="work_status_id")
	private Long workStatusId;
	
	
	@Column(name="date_hand_over")
	private String dateHandOver;
	
	@Column(name="handover_remarks")
	private String handoverRemarks;
	
	@Column(name="payment_status")
	private Long paymentStatus;
	
	
	
	public Long getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Long paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDateHandOver() {
		return dateHandOver;
	}

	public void setDateHandOver(String dateHandOver) {
		this.dateHandOver = dateHandOver;
	}

	public String getHandoverRemarks() {
		return handoverRemarks;
	}

	public void setHandoverRemarks(String handoverRemarks) {
		this.handoverRemarks = handoverRemarks;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}
	
	
	@Column(name="work_request_status_id")
	private Long workRequestStatusId;

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	private String remarks;
	
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

	public String getCcNo() {
		return ccNo;
	}

	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}

	public String getCcDate() {
		return ccDate;
	}

	public void setCcDate(String ccDate) {
		this.ccDate = ccDate;
	}

	public DocumentUpload getDocumentUploadCC() {
		return documentUploadCC;
	}

	public void setDocumentUploadCC(DocumentUpload documentUploadCC) {
		this.documentUploadCC = documentUploadCC;
	}

	/*
	 * public String getWorkStatus() { return workStatus; }
	 * 
	 * public void setWorkStatus(String workStatus) { this.workStatus = workStatus;
	 * }
	 */
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

	
	
}
