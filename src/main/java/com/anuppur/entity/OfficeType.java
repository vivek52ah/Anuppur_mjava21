package com.anuppur.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_office_type")
public class OfficeType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="office_type_id")
	private Long id;
	
	
	@Column(name="office_type_name")
	private String officeTypeName;

	private short enabled;
	
	
	public OfficeType() {
    }
    public OfficeType(Long id) {
    	this.id=id;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfficeTypeName() {
		return officeTypeName;
	}

	public void setOfficeTypeName(String officeTypeName) {
		this.officeTypeName = officeTypeName;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	
	
}
