package com.anuppur.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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

	public  Long getId() {
		return id;
	}

	public  void setId(Long id) {
		this.id = id;
	}

	public  String getDepartmentRemarkName() {
		return departmentRemarkName;
	}

	public  void setDepartmentRemarkName(String departmentRemarkName) {
		this.departmentRemarkName = departmentRemarkName;
	}

	public  Long getDepertmentMasterId() {
		return depertmentMasterId;
	}

	public  void setDepertmentMasterId(Long depertmentMasterId) {
		this.depertmentMasterId = depertmentMasterId;
	}

	public  Long getWorkId() {
		return workId;
	}

	public  void setWorkId(Long workId) {
		this.workId = workId;
	}

	public  DocumentUpload getDocumentUpload() {
		return DocumentUpload;
	}

	public  void setDocumentUpload(DocumentUpload documentUpload) {
		DocumentUpload = documentUpload;
	}

	public  Short getEnabled() {
		return enabled;
	}

	public  void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public String getCreated_time() {
		return createdTime;
	}

	public void setCreated_time(String created_time) {
		this.createdTime = created_time;
	}

}
