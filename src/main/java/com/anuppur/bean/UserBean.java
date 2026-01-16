package com.anuppur.bean;

import java.util.List;

public class UserBean {

	private Long id;

	private Integer index;

	private String username;

	private String password;

	private String confirmPassword;

	// private String name;

	private String emailId;

	private String mobileNo;

	
	
	private String departmentName;
	
	private String contactNo;

	private String status;

	private String oldStatus;

	private RoleBean role;

	private UserTypeBean userTypes;

	private OfficeTypeBean officeType;

	private String loggedInUserRole;

	private String rolee;

	private List<String> rolelist;

	
	private Long designationId;
	
	private String designationName;
	
	
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public List<String> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<String> rolelist) {
		this.rolelist = rolelist;
	}

	public String getRolee() {
		return rolee;
	}

	public void setRolee(String rolee) {
		this.rolee = rolee;
	}

	public String getLoggedInUserRole() {
		return loggedInUserRole;
	}

	public void setLoggedInUserRole(String loggedInUserRole) {
		this.loggedInUserRole = loggedInUserRole;
	}

	private Long districtId;

	private Long implementationAgencyId;

	private Long userTypeId;

	private String userTypeName;

	private String officeTypeName;

	private String implementationAgencyName;

	private Long officeTypeId;

	private String districtName;

	private String firstName;

	private String lastName;

	private Long lcId;

	private Long citizenReqId;
	private String mobNo;
	private String lcName;

	// private String officialEmailId;
	// private String officialPhone;

	private Long divisionId;

	private String divisionName;
	
	private String assignDate;

	public final String getAssignDate() {
		return assignDate;
	}

	public final void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String state) {
		this.status = state;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getLcId() {
		return lcId;
	}

	public void setLcId(Long lcId) {
		this.lcId = lcId;
	}

	public Long getCitizenReqId() {
		return citizenReqId;
	}

	public void setCitizenReqId(Long citizenReqId) {
		this.citizenReqId = citizenReqId;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getLcName() {
		return lcName;
	}

	public void setLcName(String lcName) {
		this.lcName = lcName;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public UserTypeBean getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(UserTypeBean userTypes) {
		this.userTypes = userTypes;
	}

	public Long getImplementationAgencyId() {
		return implementationAgencyId;
	}

	public void setImplementationAgencyId(Long implementationAgencyId) {
		this.implementationAgencyId = implementationAgencyId;
	}

	public OfficeTypeBean getOfficeType() {
		return officeType;
	}

	public void setOfficeType(OfficeTypeBean officeType) {
		this.officeType = officeType;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getOfficeTypeId() {
		return officeTypeId;
	}

	public void setOfficeTypeId(Long officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getOfficeTypeName() {
		return officeTypeName;
	}

	public void setOfficeTypeName(String officeTypeName) {
		this.officeTypeName = officeTypeName;
	}

	public String getImplementationAgencyName() {
		return implementationAgencyName;
	}

	public void setImplementationAgencyName(String implementationAgencyName) {
		this.implementationAgencyName = implementationAgencyName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	

	/*
	 * public String getOfficialEmailId() { return officialEmailId; }
	 * 
	 * public void setOfficialEmailId(String officialEmailId) { this.officialEmailId
	 * = officialEmailId; }
	 * 
	 * public String getOfficialPhone() { return officialPhone; }
	 * 
	 * public void setOfficialPhone(String officialPhone) { this.officialPhone =
	 * officialPhone; }
	 */

}
