/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "mst_impl_agency_type")
public class ImplementationAgencyType implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "impl_agency_type")
	private String implAgencyType;


	public ImplementationAgencyType() {
	}

	public ImplementationAgencyType(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImplAgencyType() {
		return implAgencyType;
	}

	public void setImplAgencyType(String implAgencyType) {
		this.implAgencyType = implAgencyType;
	}

}
