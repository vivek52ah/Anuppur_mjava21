package com.anuppur.json;

import java.util.List;

import com.anuppur.bean.SorYearBean;
import com.anuppur.bean.WorkSubDelayResonBean;
import com.anuppur.entity.WorkSubDelayReson;

public class SdrJson {
	
	
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

	public List<WorkSubDelayResonBean> getAaData() {
		return aaData;
	}

	public void setAaData(List<WorkSubDelayResonBean> aaData) {
		this.aaData = aaData;
	}

	long iTotalRecords;

    long iTotalDisplayRecords;

    String sEcho;

    String sColumns;
    
    List<WorkSubDelayResonBean> aaData;

}
