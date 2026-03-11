package com.anuppur.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity   
@Table(name = "document_upload_workprogress_details")
public class DocumentUploadWorkProgress  implements Serializable{
  
	
	 private static final long serialVersionUID = 1L;
		@Id
		@Basic(optional = false)
		@Column(name = "id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long documentId;
		
		 @Column(name = "document_name")
		private String documentName;
		 
		 
		 @Column(name = "enabled")
		    private Short enabled;
		 
			@Column(name = "created_date")
			@Temporal(TemporalType.TIMESTAMP)
			private Date createdDate;
			
			@JoinColumn(name="work_sub_status_id", referencedColumnName="work_sub_status_id", nullable=false)
			private Long workSubStatusId;
			
			@Column(name="work_sub_status_name_e")
			private String workSubStatusNameE;
			
			@Column(name="work_status_id")
			private Long workStatusId;
			
			@Column(name="work_status_name_e")
			private String workStatusNameE;
			
			@Column(name="action_taken_delay")
			private String actionTakenDelay;
			
			private String remarks;
			
			@Column(name="physical_perc")
			private Integer perc;
			
			@Column(name = "address")
			private String address;
			
			@Column(name = "longitude")
			private String longitude;
			
			@Column(name = "lattitude")
			private String lattitude;
			
			
			
			
			
			
			
			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getLongitude() {
				return longitude;
			}

			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}

			public String getLattitude() {
				return lattitude;
			}

			public void setLattitude(String lattitude) {
				this.lattitude = lattitude;
			}

			public Integer getPerc() {
				return perc;
			}

			public void setPerc(Integer perc) {
				this.perc = perc;
			}

			public String getRemarks() {
				return remarks;
			}

			public void setRemarks(String remarks) {
				this.remarks = remarks;
			}

			public Long getWorkStatusId() {
				return workStatusId;
			}

			public void setWorkStatusId(Long workStatusId) {
				this.workStatusId = workStatusId;
			}

			public String getWorkStatusNameE() {
				return workStatusNameE;
			}

			public void setWorkStatusNameE(String workStatusNameE) {
				this.workStatusNameE = workStatusNameE;
			}

			public String getActionTakenDelay() {
				return actionTakenDelay;
			}

			public void setActionTakenDelay(String actionTakenDelay) {
				this.actionTakenDelay = actionTakenDelay;
			}
			
			@JoinColumn(name="work_sub_delay_reason_id", referencedColumnName="work_sub_delay_reason_id", nullable=false)
//			private Long workSubDelayReasonId;
			private Long workSubDelayReasonId;

			@Column(name="work_id")
			private Long workId;
			
			@Column(name="work_pro_id")
			private Long workProId;
			
			

			public Long getDocumentId() {
				return documentId;
			}

			public void setDocumentId(Long documentId) {
				this.documentId = documentId;
			}

			public String getDocumentName() {
				return documentName;
			}

			public void setDocumentName(String documentName) {
				this.documentName = documentName;
			}

			public Short getEnabled() {
				return enabled;
			}

			public void setEnabled(Short enabled) {
				this.enabled = enabled;
			}

			public Date getCreatedDate() {
				return createdDate;
			}

			public void setCreatedDate(Date createdDate) {
				this.createdDate = createdDate;
			}

			
			public Long getWorkSubStatusId() {
				return workSubStatusId;
			}

			public void setWorkSubStatusId(Long workSubStatusId) {
				this.workSubStatusId = workSubStatusId;
			}

			public String getWorkSubStatusNameE() {
				return workSubStatusNameE;
			}

			public void setWorkSubStatusNameE(String workSubStatusNameE) {
				this.workSubStatusNameE = workSubStatusNameE;
			}

			public Long getWorkId() {
				return workId;
			}

			public void setWorkId(Long workId) {
				this.workId = workId;
			}

			public Long getWorkProId() {
				return workProId;
			}

			public void setWorkProId(Long workProId) {
				this.workProId = workProId;
			}

			public Long getWorkSubDelayReasonId() {
				return workSubDelayReasonId;
			}

			public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
				this.workSubDelayReasonId = workSubDelayReasonId;
			}

			

			
}
