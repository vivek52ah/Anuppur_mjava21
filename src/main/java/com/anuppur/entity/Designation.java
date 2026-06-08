package com.anuppur.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_designation")
public class Designation {
	
	
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     
     
     @Column(name = "designation_name_e")
     private String designationNameEnglish;
     
     @Column(name="designation_name_h")
     private String designationNameHindi;
     
     @Column(name = "enabled")
     private Short enabled;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDesignationNameEnglish() {
		return designationNameEnglish;
	}


	public void setDesignationNameEnglish(String designationNameEnglish) {
		this.designationNameEnglish = designationNameEnglish;
	}


	public String getDesignationNameHindi() {
		return designationNameHindi;
	}


	public void setDesignationNameHindi(String designationNameHindi) {
		this.designationNameHindi = designationNameHindi;
	}


	public Short getEnabled() {
		return enabled;
	}


	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
     
     
}
