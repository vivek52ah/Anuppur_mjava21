package com.anuppur.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity   
@Table(name = "document_upload_details")
public class DocumentUpload implements Serializable {
	  private static final long serialVersionUID = 1L;
		@Id
		@Basic(optional = false)
		@Column(name = "id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long documentId;
	    
	    @Lob
		@Basic(fetch = FetchType.LAZY)
		@Column(name = "document_content")
		private Blob documentContent;
	    
	    @Column(name = "document_name")
		private String documentName;
	    
		@Column(name = "document_type")
		private String documentType;
		
		@Column(name = "document_desc")
		private String documentDesc;
	    
	    @Column(name = "enabled")
	    private Short enabled;
	    
	    @Column(name = "document_upload_path")
		private String documentUploadPath;
	    
		@Column(name = "created_date")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createdDate;
		

		
		  @Column(name="work_id") private Long workId;
		  
		  @Column(name="type_work") private String typeWork;
		 
	    
		public Long getWorkId() {
			return workId;
		}

		public void setWorkId(Long workId) {
			this.workId = workId;
		}

		public String getTypeWork() {
			return typeWork;
		}

		public void setTypeWork(String typeWork) {
			this.typeWork = typeWork;
		}

		@PrePersist
		protected void onCreate() {
			this.createdDate = new Date();
		}
	    
	    public DocumentUpload() {
	    }

	    public DocumentUpload(Long documentId) {
	        this.documentId = documentId;
	    }
	    
	    public DocumentUpload(Blob documentContent, String documentName,
				String documentType, Short enabled) {
			super();
			this.documentContent = documentContent;
			this.documentName = documentName;
			this.documentType = documentType;
			this.enabled = enabled;
		}

		public Long getDocumentId() {
			return documentId;
		}

		public void setDocumentId(Long documentId) {
			this.documentId = documentId;
		}

		public Blob getDocumentContent() {
			return documentContent;
		}

		public void setDocumentContent(Blob documentContent) {
			this.documentContent = documentContent;
		}

		public String getDocumentName() {
			return documentName;
		}

		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}

		public String getDocumentType() {
			return documentType;
		}

		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}

		public Short getEnabled() {
			return enabled;
		}

		public void setEnabled(Short enabled) {
			this.enabled = enabled;
		}

		public String getDocumentUploadPath() {
			return documentUploadPath;
		}

		public void setDocumentUploadPath(String documentUploadPath) {
			this.documentUploadPath = documentUploadPath;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getDocumentDesc() {
			return documentDesc;
		}

		public void setDocumentDesc(String documentDesc) {
			this.documentDesc = documentDesc;
		}
		

}
