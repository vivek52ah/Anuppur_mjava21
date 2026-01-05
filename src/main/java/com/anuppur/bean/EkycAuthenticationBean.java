package com.anuppur.bean;

public class EkycAuthenticationBean {

	private String aadhaarNumber;
	private String otp;
	private String txn;
	private String username;

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
