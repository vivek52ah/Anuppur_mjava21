package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_work_type_category_mapping")
public class WorkCategoryTypeMapping {

	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    private Long id;
	    
	    private Short enabled;
	    
	    
	    @JoinColumn(name = "work_type_id", referencedColumnName = "work_type_id")
		@ManyToOne
		private WorkType workType;
	    
	    @JoinColumn(name = "work_category_id", referencedColumnName = "work_category_id")
		@ManyToOne
		private WorkCategory workCategory;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Short getEnabled() {
			return enabled;
		}

		public void setEnabled(Short enabled) {
			this.enabled = enabled;
		}

		public WorkType getWorkType() {
			return workType;
		}

		public void setWorkType(WorkType workType) {
			this.workType = workType;
		}

		public WorkCategory getWorkCategory() {
			return workCategory;
		}

		public void setWorkCategory(WorkCategory workCategory) {
			this.workCategory = workCategory;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    
}
