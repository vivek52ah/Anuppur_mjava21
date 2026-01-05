package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "mst_financial_head")
public class FinancialHead {

	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name ="financial_head_name")
	private String financialHeadName;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFinancialHeadName() {
		return financialHeadName;
	}


	public void setFinancialHeadName(String financialHeadName) {
		this.financialHeadName = financialHeadName;
	}
	
	
	
}
