package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



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
