package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ExpensesDataBean {
	
	private Long expId;
	
	private int index;
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private Long workId;
	
     private Long month;
	
	private Long status;
	
	private Long year;
	
	private BigDecimal expensessUptoMarch;
	
	private BigDecimal expensessCurrentFy;
	
	private BigDecimal totalExpensess;
	
	private Long total ;
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	private String createdDate;

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public BigDecimal getExpensessUptoMarch() {
		return expensessUptoMarch;
	}

	public void setExpensessUptoMarch(BigDecimal expensessUptoMarch) {
		this.expensessUptoMarch = expensessUptoMarch;
	}

	public BigDecimal getExpensessCurrentFy() {
		return expensessCurrentFy;
	}

	public void setExpensessCurrentFy(BigDecimal expensessCurrentFy) {
		this.expensessCurrentFy = expensessCurrentFy;
	}

	public BigDecimal getTotalExpensess() {
		return totalExpensess;
	}

	public void setTotalExpensess(BigDecimal totalExpensess) {
		this.totalExpensess = totalExpensess;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String createdBy;


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

}
