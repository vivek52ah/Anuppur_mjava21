package com.anuppur.bean;

import org.springframework.web.multipart.MultipartFile;

public class DepartmentRemarksBean {

	private Long id;

	private Long depertmentMasterId;

	private Long workId;

	private Short enabled;

	private Integer index;

	private String createdDate;

	private String remark;

	private MultipartFile dmattachment;

	private Long documentId;

	private String documentPath;

	private String createBy;

	private String role;

	private String roleCode;

	private String departmentRemarkName;

	private String departmentName;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getDepertmentMasterId() {
		return depertmentMasterId;
	}

	public final void setDepertmentMasterId(Long depertmentMasterId) {
		this.depertmentMasterId = depertmentMasterId;
	}

	public final Long getWorkId() {
		return workId;
	}

	public final void setWorkId(Long workId) {
		this.workId = workId;
	}

	public final Short getEnabled() {
		return enabled;
	}

	public final void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public final Integer getIndex() {
		return index;
	}

	public final void setIndex(Integer index) {
		this.index = index;
	}

	public final String getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public final String getRemark() {
		return remark;
	}

	public final void setRemark(String remark) {
		this.remark = remark;
	}

	public final MultipartFile getDmattachment() {
		return dmattachment;
	}

	public final void setDmattachment(MultipartFile dmattachment) {
		this.dmattachment = dmattachment;
	}

	public final Long getDocumentId() {
		return documentId;
	}

	public final void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public final String getDocumentPath() {
		return documentPath;
	}

	public final void setDocumentPath(String documentPath) {
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

	public final String getDepartmentRemarkName() {
		return departmentRemarkName;
	}

	public final void setDepartmentRemarkName(String departmentRemarkName) {
		this.departmentRemarkName = departmentRemarkName;
	}

	public final String getDepartmentName() {
		return departmentName;
	}

	public final void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
}
