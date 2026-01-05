package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="contractor")
public class Contractor extends Auditable implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/*
	 * @Column(name="work_id") 
	 * private Long workId;
	 */
    
	@JoinColumn(name = "work_id", referencedColumnName = "id")
    @OneToOne
	private Work work;
	
	
	
	@Column(name="name")
	private String name;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="email_id")
	private String  emailId;

	@Column(name="firm_name_address")
	private String firmNameAddress;
	
	private String remarks;
	
	private String status;
	
	@Column(name="work_request_status_id")
	private Long workRequestStatusId;
	
	@Column(name="pan")
	private String pan;

	@Column(name="gstin")
	private String gstin;

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirmNameAddress() {
		return firmNameAddress;
	}

	public void setFirmNameAddress(String firmNameAddress) {
		this.firmNameAddress = firmNameAddress;
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

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

}
