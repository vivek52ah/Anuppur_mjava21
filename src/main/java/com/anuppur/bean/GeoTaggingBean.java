package com.anuppur.bean;

import java.util.List;

import com.anuppur.entity.LocationPoints;

public class GeoTaggingBean {

	 private Long workId;
	 
	 private Boolean atProjectionLocation;
	 
	 private String CurrentLocation;
	 
	 private String Address;
	 
	 private List<GTPoint> points;

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Boolean getAtProjectionLocation() {
		return atProjectionLocation;
	}

	public void setAtProjectionLocation(Boolean atProjectionLocation) {
		this.atProjectionLocation = atProjectionLocation;
	}

	public String getCurrentLocation() {
		return CurrentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		CurrentLocation = currentLocation;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public List<GTPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GTPoint> points) {
		this.points = points;
	}

	
	
	
	 
	 
	 
	 
	 
	
	
	
	
}
