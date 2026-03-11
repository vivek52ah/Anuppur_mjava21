package com.anuppur.bean;



public class DepartmentMasterBean {

	private Long Id;
	
	
	private String name;
	
	
	private Short enabled;


	public final Long getId() {
		return Id;
	}


	public final void setId(Long id) {
		Id = id;
	}


	public final String getName() {
		return name;
	}


	public final void setName(String name) {
		this.name = name;
	}


	public final Short getEnabled() {
		return enabled;
	}


	public final void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
	
	
}
