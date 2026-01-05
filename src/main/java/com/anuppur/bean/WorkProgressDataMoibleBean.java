package com.anuppur.bean;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WorkProgressDataMoibleBean {
   
	private String workRemakrsM;
	
	
	 
	private Long documentId;
	private Integer indexWS;
	
	private String responsedata;
	
	private int size;
	
	private Long workStatusId;
	
	private String workStatusNameE;
	
	
	private String longitude;
	
	private String lattitude;
	
	private String Address;
	
	
	
	private String actionTakenDelay;
	
	private String subDelayReason;
	
	private String workremarks;
	
	private List<String>  remarks;
	
	private Integer perc;
	
	private Long workSubDelayReasonId;
	
	private String workSubDelayReason;
	
	
	
	
	
	
    
     
	public String getWorkRemakrsM() {
		return workRemakrsM;
	}

	public void setWorkRemakrsM(String workRemakrsM) {
		this.workRemakrsM = workRemakrsM;
	}

	public Integer getPerc() {
		return perc;
	}

	public void setPerc(Integer perc) {
		this.perc = perc;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	
	
	public String getWorkremarks() {
		return workremarks;
	}

	public void setWorkremarks(String workremarks) {
		this.workremarks = workremarks;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getResponsedata() {
		return responsedata;
	}

	public void setResponsedata(String responsedata) {
		this.responsedata = responsedata;
	}

	public Integer getIndexWS() {
		return indexWS;
	}

	public void setIndexWS(Integer indexWS) {
		this.indexWS = indexWS;
	}

	
	private List<MultipartFile>  UploadFile;
	
	
	
	
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

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public List<MultipartFile> getUploadFile() {
		return UploadFile;
	}

	public void setUploadFile(List<MultipartFile> uploadFile) {
		UploadFile = uploadFile;
	}


	private MultipartFile file;
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	private String documentName;
	private Short enabled;
	private Date createdDate;
	private Long workSubStatusId;
	private String workSubStatusNameE;
	private String reasonDelay;
	public String getReasonDelay() {
		return reasonDelay;
	}

	public void setReasonDelay(String reasonDelay) {
		this.reasonDelay = reasonDelay;
	}

	private Long workId;
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

	public String getSubDelayReason() {
		return subDelayReason;
	}

	public void setSubDelayReason(String subDelayReason) {
		this.subDelayReason = subDelayReason;
	}

	public Long getWorkSubDelayReasonId() {
		return workSubDelayReasonId;
	}

	public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
		this.workSubDelayReasonId = workSubDelayReasonId;
	}

	public String getWorkSubDelayReason() {
		return workSubDelayReason;
	}

	public void setWorkSubDelayReason(String workSubDelayReason) {
		this.workSubDelayReason = workSubDelayReason;
	}

	private Long documentUploadWorkImage;

	private String workUploadImageUrl;







	public Long getDocumentUploadWorkImage() {
		return documentUploadWorkImage;
	}

	public void setDocumentUploadWorkImage(Long documentUploadWorkImage) {
		this.documentUploadWorkImage = documentUploadWorkImage;
	}

	public String getWorkUploadImageUrl() {
		return workUploadImageUrl;
	}

	public void setWorkUploadImageUrl(String workUploadImageUrl) {
		this.workUploadImageUrl = workUploadImageUrl;
	}


	
	

}
