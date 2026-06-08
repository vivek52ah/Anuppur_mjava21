package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_category_subtype")
public class SubCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    @Id
	    @Basic(optional = false)
	   
	    @Column(name = "category_subtype_id")
	    private Long categorySubTypeId;
	  
	    
	    @Column(name="category_subtype_name_e")
		private String categorySubTypeNameE;
	    
	    @Column(name="category_subtype_name_h")
	  	private String categorySubTypeNameH;
	    
	    private short enabled;
	    
	    
		/*
		 * @JoinColumn(name = "WORK_TYPE_ID", referencedColumnName = "WORK_TYPE_ID")
		 * 
		 * @ManyToOne private WorkType workType;
		 */
	    
	    
	    @JoinColumn(name = "work_category_id", referencedColumnName = "work_category_id")
		@ManyToOne
		private WorkCategory workCategory;
	    
	    
	    public SubCategory(Long id) {
			this.categorySubTypeId=id;
		}
		


		public SubCategory() {
			super();
			// TODO Auto-generated constructor stub
		}



		public Long getCategorySubTypeId() {
			return categorySubTypeId;
		}


		public void setCategorySubTypeId(Long categorySubTypeId) {
			this.categorySubTypeId = categorySubTypeId;
		}


		public String getCategorySubTypeNameE() {
			return categorySubTypeNameE;
		}


		public void setCategorySubTypeNameE(String categorySubTypeNameE) {
			this.categorySubTypeNameE = categorySubTypeNameE;
		}


		public String getCategorySubTypeNameH() {
			return categorySubTypeNameH;
		}


		public void setCategorySubTypeNameH(String categorySubTypeNameH) {
			this.categorySubTypeNameH = categorySubTypeNameH;
		}


		public short getEnabled() {
			return enabled;
		}


		public void setEnabled(short enabled) {
			this.enabled = enabled;
		}


		/*
		 * public WorkType getWorkType() { return workType; }
		 * 
		 * 
		 * public void setWorkType(WorkType workType) { this.workType = workType; }
		 */

		public WorkCategory getWorkCategory() {
			return workCategory;
		}


		public void setWorkCategory(WorkCategory workCategory) {
			this.workCategory = workCategory;
		}
}
