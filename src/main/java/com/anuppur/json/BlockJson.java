package com.anuppur.json;

import java.util.List;

import com.anuppur.bean.BlockBean;

//import com.anuppur.bean.SchemeBean;

public class BlockJson {

	long iTotalRecords;

    long iTotalDisplayRecords;

    String sEcho;

    String sColumns;
    
    List<BlockBean> aaData;

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

	public List<BlockBean> getAaData() {
		return aaData;
	}

	public void setAaData(List<BlockBean> aaData) {
		this.aaData = aaData;
	}
    
    
}
