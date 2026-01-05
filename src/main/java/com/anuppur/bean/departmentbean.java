package com.anuppur.bean;

public class departmentbean {

	
	Long id ;
	
	String depName;


	public departmentbean(Long id, String depName) {
	
		this.id = id;
		this.depName = depName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	
}
