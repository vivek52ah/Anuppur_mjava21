/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity  
@Table(name = "mst_implementation_agency")
public class ImplementationAgency implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long implementationAgencyId;

	@Column(name = "impl_agency_name")
	private String implAgencyname;

	@Column(name = "enabled")
	private Short enabled;
	
	@Column(name = "impl_agency_type")
	private Long implAgencyType;
	
	@Column(name = "created_date")
	private String  createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	
	/*
	 * @JoinColumn(name = "AGENCY_ID", referencedColumnName = "ID")
	 * 
	 * @ManyToOne private ImplementationAgency implementationAgency;
	 */
	 
	
	public ImplementationAgency(Long implementationAgencyId ) {
		this.implementationAgencyId=implementationAgencyId;
	}
	public ImplementationAgency() {
	}

	
	public String getImplAgencyname() {
		return implAgencyname;
	}

	public void setImplAgencyname(String implAgencyname) {
		this.implAgencyname = implAgencyname;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Long getImplAgencyType() {
		return implAgencyType;
	}

	public void setImplAgencyType(Long implAgencyType) {
		this.implAgencyType = implAgencyType;
	}


	public Long getImplementationAgencyId() {
		return implementationAgencyId;
	}


	public void setImplementationAgencyId(Long implementationAgencyId) {
		this.implementationAgencyId = implementationAgencyId;
	}

	/*
	 * public ImplementationAgency getImplementationAgency() { return
	 * implementationAgency; }
	 * 
	 * public void setImplementationAgency(ImplementationAgency
	 * implementationAgency) { this.implementationAgency = implementationAgency; }
	 */
	


	
	public String getCreatedBy() {
		return createdBy;
	}


	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
