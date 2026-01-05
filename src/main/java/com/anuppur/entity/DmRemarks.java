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
}
