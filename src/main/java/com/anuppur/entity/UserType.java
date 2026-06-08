package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
