package com.anuppur.json;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.anuppur.bean.DocumentUploadWorkProgressBean;

public class WorkProgressImagesJson {
	long iTotalRecords;

    long iTotalDisplayRecords;

    String sEcho;

    String sColumns;
    
    
    List<DocumentUploadWorkProgressBean> aaData;


	public long getiTotalRecords() {
		return iTotalRecords;
	}


	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}


	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}


	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}


	public String getsEcho() {
		return sEcho;
	}


	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}


	public String getsColumns() {
		return sColumns;
	}


	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}


	public List<DocumentUploadWorkProgressBean> getAaData() {
		return aaData;
	}


	public void setAaData(List<DocumentUploadWorkProgressBean> aaData) {
		this.aaData = aaData;
	}


	


	

	
}
