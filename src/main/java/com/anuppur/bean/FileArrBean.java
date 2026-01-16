package com.anuppur.bean;

public class FileArrBean {

	private Long noOfDocs;

	private String remarks;

	private String otherDocDate;

	private String[] fileArr;

	public Long getNoOfDocs() {
		return noOfDocs;
	}

	public void setNoOfDocs(Long noOfDocs) {
		this.noOfDocs = noOfDocs;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOtherDocDate() {
		return otherDocDate;
	}

	public void setOtherDocDate(String otherDocDate) {
		this.otherDocDate = otherDocDate;
	}

	public String[] getFileArr() {
		return fileArr;
	}

	public void setFileArr(String[] fileArr) {
		this.fileArr = fileArr;
	}

}
