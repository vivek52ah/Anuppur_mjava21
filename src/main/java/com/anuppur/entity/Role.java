package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "mst_role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "role_code")
	private String roleCode;
	
	@Column(name = "role_name")
    private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Role(String roleCode) {
		super();
		this.roleCode = roleCode;
	}

	public Role() {
		super();
	}
}
