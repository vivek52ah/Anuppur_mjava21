package com.anuppur.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_work_financial_agency")
public class WorkFinancialAgency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long financialHeadId;

	private Double cost;

	private Long workId;

	private Double expenditure;

	private Double totalCost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFinancialHeadId() {
		return financialHeadId;
	}

	public void setFinancialHeadId(Long financialHeadId) {
		this.financialHeadId = financialHeadId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

}
