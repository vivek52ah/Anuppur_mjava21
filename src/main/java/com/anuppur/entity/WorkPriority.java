package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



	@Entity  
	@Table(name = "mst_work_priority")
	public class WorkPriority implements Serializable {
		
		private static final long serialVersionUID = 1L;
		@Id
		@Basic(optional = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private Long Id;
	
		@Column(name = "work_priority_name")
		private String workPriorityName;

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getWorkPriorityName() {
			return workPriorityName;
		}

		public void setWorkPriorityName(String workPriorityName) {
			this.workPriorityName = workPriorityName;
		}
	
		
		
}
