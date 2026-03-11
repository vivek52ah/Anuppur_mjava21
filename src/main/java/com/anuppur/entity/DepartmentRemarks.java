package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "department_remarks")
public class DepartmentRemarks extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Id
	private Long id;

	@Column(name = "department_remark_name")
	private String departmentRemarkName;

	@Column(name = "department_master_id")
	private Long depertmentMasterId;

	@Column(name = "work_id")
	private Long workId;

	@JoinColumn(name = "document_id", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload DocumentUpload;

	@Column(name = "enabled")
	private Short enabled;

	@Column(name = "created_time")
	private String createdTime;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getDepartmentRemarkName() {
		return departmentRemarkName;
	}

	public final void setDepartmentRemarkName(String departmentRemarkName) {
		this.departmentRemarkName = departmentRemarkName;
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

	public final DocumentUpload getDocumentUpload() {
		return DocumentUpload;
	}

	public final void setDocumentUpload(DocumentUpload documentUpload) {
		DocumentUpload = documentUpload;
	}

	public final Short getEnabled() {
		return enabled;
	}

	public final void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public String getCreated_time() {
		return createdTime;
	}

	public void setCreated_time(String created_time) {
		this.createdTime = created_time;
	}

}
