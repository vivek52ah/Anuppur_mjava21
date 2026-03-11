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
@Table(name = "mst_department")
public class DepartmentMaster implements Serializable{
	
	
	
		
		private static final long serialVersionUID = 1L;
		@Id
		@Basic(optional = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private Long Id;
		
		@Column(name = "name")
		private String name;
		
		@Column(name = "enabled")
		private Short enabled;

		public final Long getId() {
			return Id;
		}

		public final void setId(Long id) {
			Id = id;
		}

		public final String getName() {
			return name;
		}

		public final void setName(String name) {
			this.name = name;
		}

		public final Short getEnabled() {
			return enabled;
		}

		public final void setEnabled(Short enabled) {
			this.enabled = enabled;
		}
		
		

}
