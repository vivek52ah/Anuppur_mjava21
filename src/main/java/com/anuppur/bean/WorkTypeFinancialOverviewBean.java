package com.anuppur.bean;

import java.math.BigDecimal;

public class WorkTypeFinancialOverviewBean {

	private String workTypeName;
	private BigDecimal contractAmount;
	private BigDecimal expenditureAmount;

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getExpenditureAmount() {
		return expenditureAmount;
	}

	public void setExpenditureAmount(BigDecimal expenditureAmount) {
		this.expenditureAmount = expenditureAmount;
	}
}
