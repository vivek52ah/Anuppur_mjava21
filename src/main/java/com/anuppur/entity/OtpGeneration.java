package com.anuppur.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "otp_generation")
public class OtpGeneration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "email_id_mobile_no")
	private String emailIdMobileNo;

	@Column(name = "otp")
	private Integer otp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	public String getEmailIdMobileNo() {
		return emailIdMobileNo;
	}

	public void setEmailIdMobileNo(String emailIdMobileNo) {
		this.emailIdMobileNo = emailIdMobileNo;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}