package com.anuppur.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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