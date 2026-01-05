package com.anuppur.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "work_geo_location")
public class WorkGeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="work_id")
    private Long workId;

    @Column(name="at_project_location")
    private Boolean atProjectionLocation;

    @Column(name="currentpoint")
    private String currentPoint; // Stored as "latitude,longitude"

    @Column(name="Address")
    private String address;

    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(String currentPoint) {
		this.currentPoint = currentPoint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

    
  
}
