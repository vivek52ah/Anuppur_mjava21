package com.anuppur.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_physical_perc_new")
public class PhysicalPercentageNew {
	
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
	
	
	private Integer percentage;
	
	
	@JoinColumn(name = "work_sub_status_id")
    @OneToOne
	private WorkSubStatus workSubStatus;


	public WorkSubStatus getWorkSubStatus() {
		return workSubStatus;
	}


	public void setWorkSubStatus(WorkSubStatus workSubStatus) {
		this.workSubStatus = workSubStatus;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getPercentage() {
		return percentage;
	}


	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}



	

}
