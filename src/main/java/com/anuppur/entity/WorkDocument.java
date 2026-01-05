package com.anuppur.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_work_document")
public class WorkDocument extends Auditable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@JoinColumn(name = "work_id", referencedColumnName = "id")
    @ManyToOne
	private Work work;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "name_of_doc")
	private String nameOfDocs;
	
	@Column(name = "no_of_doc")
	private Long noOfDocs;
	
	@Column(name = "date_of_doc")
	private Date dateOfDocs;
	
	@Column(name = "is_deleted")
	private Short isDeleted;
	
	public WorkDocument() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getNameOfDocs() {
		return nameOfDocs;
	}

	public void setNameOfDocs(String nameOfDocs) {
		this.nameOfDocs = nameOfDocs;
	}



	

	public Long getNoOfDocs() {
		return noOfDocs;
	}

	public void setNoOfDocs(Long noOfDocs) {
		this.noOfDocs = noOfDocs;
	}

	public Date getDateOfDocs() {
		return dateOfDocs;
	}

	public void setDateOfDocs(Date dateOfDocs) {
		this.dateOfDocs = dateOfDocs;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

}
