package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="t_work_progress")
public class WorkProgress extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	/*
	  @Column(name="WORK_ID")
	   private Long workId;
	 */
	
	@JoinColumn(name = "work_id", referencedColumnName = "id")
    @OneToOne
	private Work work;
	
	@JoinColumn(name="work_status_id", referencedColumnName="id", nullable=false)
	private Long workStatusId;
	
	/*
	 * @Column(name="work_status") private String workStatus;
	 */
	
	@JoinColumn(name="work_sub_status_id", referencedColumnName="work_sub_status_id", nullable=false)
	private Integer workSubStatusId;
	
	
	@JoinColumn(name="work_sub_delay_reason_id", referencedColumnName="work_sub_delay_reason_id", nullable=false)
//	private Long workSubDelayReasonId;
	private Long workSubDelayReasonId;

	
	
	@Column(name="work_sub_status_name_e")
	private String workSubStatusNameE;
	
	
	@Column(name="other_reason_delay")
	private String otherReasonDelay;
	
	@Column(name="action_taken_delay")
	private String actionTakenDelay;
	
	
	@Column(name="stipulated_date_completion")
	private String stipulatedDateCompleted;
	
	
	@Column(name="likely_date_completion")
	private String likelyDateCompleted;
	
	
	@Column(name="date_completion")
	private String dateCompletion;
	
	
	@Column(name="date_hand_over")
	private String dateHandOver;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="expensess_upto_march")
	private BigDecimal expensessUptoMarch;
	
	@Column(name="expensess_current_fy")
	private BigDecimal expensessCurrentFy;
	
	@Column(name="total_expensess")
	private BigDecimal totalExpensess;
	
	@Column(name="status")
	private String status;
	
	@Column(name="physical_perc")
	private Integer perc;
	
	
	@Column(name="work_request_status_id")
	private Long workRequestStatusId;

	
	@Column(name = "month")
	private Long month;

	@Column(name = "financial_year")
	private Long financialYear;

	@Column(name = "monthly_progress")
	private String monthlyProgress;
	
	public Integer getPerc() {
		return perc;
	}

	public void setPerc(Integer perc) {
		this.perc = perc;
	}

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}
	
	
	

	@JoinColumn(name="progress_document_upload", referencedColumnName="id")
	@OneToOne
	private DocumentUpload progressDocumentUpload;

	
	@Column(name = "workremarksm")
	private String workremarksM;
	
	
	
	
	
	
	public String getWorkremarksM() {
		return workremarksM;
	}

	public void setWorkremarksM(String workremarksM) {
		this.workremarksM = workremarksM;
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

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	/*
	 * public String getWorkStatus() { return workStatus; }
	 * 
	 * public void setWorkStatus(String workStatus) { this.workStatus = workStatus;
	 * }
	 */

	public Integer getWorkSubStatusId() {
		return workSubStatusId;
	}

	public void setWorkSubStatusId(Integer workSubStatusId) {
		this.workSubStatusId = workSubStatusId;
	}

	/*
	 * public String getWorkSubStatusNameE() { return workSubStatusNameE; }
	 * 
	 * public void setWorkSubStatusNameE(String workSubStatusNameE) {
	 * this.workSubStatusNameE = workSubStatusNameE; }
	 */

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

	public DocumentUpload getProgressDocumentUpload() {
		return progressDocumentUpload;
	}

	public void setProgressDocumentUpload(DocumentUpload progressDocumentUpload) {
		this.progressDocumentUpload = progressDocumentUpload;
	}

	public Long getWorkSubDelayReasonId() {
		return workSubDelayReasonId;
	}

	public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
		this.workSubDelayReasonId = workSubDelayReasonId;
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

//	public WorkSubDelayReson getWorkSubDelayReasonId() {
//		return workSubDelayReasonId;
//	}
//
//	public void setWorkSubDelayReasonId(WorkSubDelayReson workSubDelayReasonId) {
//		this.workSubDelayReasonId = workSubDelayReasonId;
//}
	
	
	
	

}
