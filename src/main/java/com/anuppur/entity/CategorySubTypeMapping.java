package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_category_subcategory_mapping")
public class CategorySubTypeMapping {
	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    private Long id;
	    
	    
	    public CategorySubTypeMapping() {
			super();
			// TODO Auto-generated constructor stub
		}

		private Short enabled;
	    
	    
	    @JoinColumn(name = "work_category_id", referencedColumnName = "work_category_id")
		@ManyToOne
		private WorkCategory workCategory;
	    
	    @JoinColumn(name = "category_subtype_id", referencedColumnName = "category_subtype_id")
		@ManyToOne
		private SubCategory subCategory;

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

		

		public SubCategory getSubCategory() {
			return subCategory;
		}

		public void setSubCategory(SubCategory subCategory) {
			this.subCategory = subCategory;
		}

		public WorkCategory getWorkCategory() {
			return workCategory;
		}

		public void setWorkCategory(WorkCategory workCategory) {
			this.workCategory = workCategory;
		}

}
