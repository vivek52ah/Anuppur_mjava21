package com.anuppur.bean;

import org.springframework.web.multipart.MultipartFile;

public class DmRemarksBean {

	private Long id;

	private Integer index;

	private String createdDate;

	private String remark;

	private Long workId;

	private MultipartFile dmattachment;

	private Long documentId;

	private String documentPath;
	
	private String createBy;
	
	private String role;
	
	private String roleCode;
	
	 private String departmentRemarks;
	 
	 private Long depertmentMasterId;
	 
	 private String departmentName;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public final Integer getIndex() {
		return index;
	}

	public final void setIndex(Integer index) {
		this.index = index;
	}

	public MultipartFile getDmattachment() {
		return dmattachment;
	}

	public void setDmattachment(MultipartFile dmattachment) {
		this.dmattachment = dmattachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public final String getCreateBy() {
		return createBy;
	}

	public final void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public final String getRole() {
		return role;
	}

	public final void setRole(String role) {
		this.role = role;
	}

	public final String getRoleCode() {
		return roleCode;
	}

	public final void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public final String getDepartmentRemarks() {
		return departmentRemarks;
	}

	public final void setDepartmentRemarks(String departmentRemarks) {
		this.departmentRemarks = departmentRemarks;
	}

	public final Long getDepertmentMasterId() {
		return depertmentMasterId;
	}

	public final void setDepertmentMasterId(Long depertmentMasterId) {
		this.depertmentMasterId = depertmentMasterId;
	}

	public final String getDepartmentName() {
		return departmentName;
	}

	public final void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
}
