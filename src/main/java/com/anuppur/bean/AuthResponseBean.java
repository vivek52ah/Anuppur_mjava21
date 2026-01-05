package com.anuppur.bean;

import java.util.List;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STIconSetType;

public class AuthResponseBean {
	private String userId;
	private Long statusCode;
	private String statusDesc;
	private String loggedInUserRole;
	private Integer userIdInt;
	private String name;
	private String designation;
	private String mobileNo;
	private String emailAddress;
	private Long id;
	private String jwttoken;
	private String departmentNAme;
	private String jsessionid;
	public String getUserId() {
		return userId;
	}
	

	public String getDepartmentNAme() {
		return departmentNAme;
	}


	public void setDepartmentNAme(String departmentNAme) {
		this.departmentNAme = departmentNAme;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public Long getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Long statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getLoggedInUserRole() {
		return loggedInUserRole;
	}

	public void setLoggedInUserRole(String loggedInUserRole) {
		this.loggedInUserRole = loggedInUserRole;
	}


	public Integer getUserIdInt() {
		return userIdInt;
	}

	public void setUserIdInt(Integer userIdInt) {
		this.userIdInt = userIdInt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	
	
	
}
