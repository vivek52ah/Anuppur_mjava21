package com.anuppur.json;

import java.util.List;

public class ImageJson {
	
	private String id;
	
    private String role;
	
	private String userId;
	
	private List<String> imageArray;
	
	private List<String> imageNameArray;
	
	private String stage;
	
	private String latitude;
	
	private String longitude;
	
	private String dateTimestamp;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getImageArray() {
		return imageArray;
	}

	public void setImageArray(List<String> imageArray) {
		this.imageArray = imageArray;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(String dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public List<String> getImageNameArray() {
		return imageNameArray;
	}

	public void setImageNameArray(List<String> imageNameArray) {
		this.imageNameArray = imageNameArray;
	}
}
