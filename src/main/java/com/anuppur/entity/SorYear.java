package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity 
@Table(name = "mst_sor_year")
public class SorYear implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "sor_year")
	    private String sorYear;
	    
	    @Column(name = "enabled")
	    private Short enabled;
	    
	    
	    @Column(name = "created_date")
		private String  createdDate;
		
		@Column(name = "created_by")
		private String createdBy;
		
		
		

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSorYear() {
			return sorYear;
		}

		public void setSorYear(String sorYear) {
			this.sorYear = sorYear;
		}

		public Short getEnabled() {
			return enabled;
		}

		public void setEnabled(Short enabled) {
			this.enabled = enabled;
		}
	    
	    

}
