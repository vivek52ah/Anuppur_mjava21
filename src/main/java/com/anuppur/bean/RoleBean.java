package com.anuppur.bean;

public class RoleBean {

	private String roleCode;

	private String roleName;

	private Integer index;

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

	public RoleBean(String roleCode, String roleName) {
		super();
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

	public RoleBean() {
		super();
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
