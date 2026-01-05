package com.anuppur.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="mst_physical_perc_upgradation")
public class PhysicalPercentageUpgradation {

		@Id
		@GeneratedValue(strategy =GenerationType.AUTO)
		private Long id;
		
		
		private Integer percentage;
		
		
		@JoinColumn(name = "work_sub_status_id")
	    @OneToOne
		private WorkSubStatus workSubStatus;



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


		public WorkSubStatus getWorkSubStatus() {
			return workSubStatus;
		}


		public void setWorkSubStatus(WorkSubStatus workSubStatus) {
			this.workSubStatus = workSubStatus;
		}


		
}
