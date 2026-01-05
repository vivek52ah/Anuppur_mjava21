package com.anuppur.json;

public class DataJson {

	private String id;
	
	private String role;
	
	private Long userId;
	
	private String xml;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String toString() {
		return "id: "+ id + ", xml: "+ xml +", role: " +role+", userId: "+userId;

	}
}
