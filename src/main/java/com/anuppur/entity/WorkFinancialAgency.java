package com.anuppur.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getFinancialHeadId() {
		return financialHeadId;
	}

	public final void setFinancialHeadId(Long financialHeadId) {
		this.financialHeadId = financialHeadId;
	}

	public final Double getCost() {
		return cost;
	}

	public final void setCost(Double cost) {
		this.cost = cost;
	}

	public final Long getWorkId() {
		return workId;
	}

	public final void setWorkId(Long workId) {
		this.workId = workId;
	}

	public final Double getExpenditure() {
		return expenditure;
	}

	public final void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	public final Double getTotalCost() {
		return totalCost;
	}

	public final void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
    
    
}
