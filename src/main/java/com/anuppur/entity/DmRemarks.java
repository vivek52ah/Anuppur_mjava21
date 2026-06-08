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
@Table(name = "dm_remarks")
public class DmRemarks  extends Auditable{

	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
    @Column(name = "created_time")
	private String createdTime;
    
    
    
	
    public String getCreated_time() {
		return createdTime;
	}

	public void setCreated_time(String created_time) {
		this.createdTime = created_time;
	}

	@Column(name = "remark")
    private String remark;

    @Column(name = "work_id")
    private Long workId;

    @JoinColumn(name = "document_id", referencedColumnName = "id")
	@OneToOne
    private  DocumentUpload  DocumentUpload;

    
    @Column(name="enabled")
    private Short enabled;
    
    @Column(name = "department_remarks")
    private String departmentRemarks;
    
    @Column(name = "department_master_id")
    private Long depertmentMasterId;
    
    
    public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public DocumentUpload getDocumentUpload() {
		return DocumentUpload;
	}

	public void setDocumentUpload(DocumentUpload documentUpload) {
		DocumentUpload = documentUpload;
	}

	public String getRemark() {
        return remark;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public  String getDepartmentRemarks() {
		return departmentRemarks;
	}

	public  void setDepartmentRemarks(String departmentRemarks) {
		this.departmentRemarks = departmentRemarks;
	}

	public  Long getDepertmentMasterId() {
		return depertmentMasterId;
	}

	public  void setDepertmentMasterId(Long depertmentMasterId) {
		this.depertmentMasterId = depertmentMasterId;
	}
	
	
}
