package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;



@Entity 
@Table(name="expenses_cost")
public class ExpensesData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long expId;
	
	
	@Column(name = "work_id")
	private Long workId;
	

	private Long month;
	
	private Long status;
	
	private Long year;
	
	
	@Column(name="expensess_upto_march")
	private BigDecimal expensessUptoMarch;
	
	@Column(name="expensess_current_fy")
	private BigDecimal expensessCurrentFy;
	
	@Column(name="total_expensess")
	private BigDecimal totalExpensess;
	
	
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;



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



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


}
