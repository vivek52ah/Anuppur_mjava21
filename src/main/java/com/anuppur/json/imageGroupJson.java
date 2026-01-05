package com.anuppur.json;

import java.util.List;

import com.anuppur.bean.ImageGroupBean;
import com.anuppur.bean.WorkHeadBean;

public class imageGroupJson {
	long iTotalRecords;

    long iTotalDisplayRecords;

    String sEcho;

    String sColumns;
    
    List<ImageGroupBean> aaData;

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

	public List<ImageGroupBean> getAaData() {
		return aaData;
	}

	public void setAaData(List<ImageGroupBean> aaData) {
		this.aaData = aaData;
	}
    
    
}
