
package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "as_generated_count")
public class AsGeneratedCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="last_count")
	private Integer lastCount;
	
	@Column(name="financial_year")
	private String financialYear;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLastCount() {
		return lastCount;
	}

	public void setLastCount(Integer lastCount) {
		this.lastCount = lastCount;
	}

	public AsGeneratedCount(Long id, Integer lastCount) {
		super();
		this.id = id;
		this.lastCount = lastCount;
	}

	public AsGeneratedCount() {
		super();
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	
	
}
