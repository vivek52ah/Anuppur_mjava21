package com.anuppur.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



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
