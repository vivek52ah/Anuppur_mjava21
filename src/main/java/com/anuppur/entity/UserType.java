package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_user_type")
public class UserType implements Serializable  {
  
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_type_id")
	private Long id;
	
	@Column(name="user_type")
	private String userType;
    
	
	private short enabled;
	
	
	public UserType() {
    }
    public UserType(Long id) {
    	this.id=id;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}


}
