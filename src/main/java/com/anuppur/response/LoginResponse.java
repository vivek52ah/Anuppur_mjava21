package com.anuppur.response;

public class LoginResponse {

	private String code;
	
	private String status;
	
	private String message;
	
	private UserDetailResponse data;
	
	private String error;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public UserDetailResponse getData() {
		return data;
	}

	public void setData(UserDetailResponse data) {
		this.data = data;
	}
	
}
