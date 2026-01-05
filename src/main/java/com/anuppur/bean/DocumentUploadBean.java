/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.bean;

import java.sql.Blob;

public class DocumentUploadBean {

	private Long documentId;

	private Blob documentContent;

	private String documentName;

	private String documentType;

	private String documentDesc;

	private Short enabled;

	private String documentUploadPath;
	
	
	private Long workId;
	
	private String typeWork;

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

	public String getDocumentDesc() {
		return documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
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

}
