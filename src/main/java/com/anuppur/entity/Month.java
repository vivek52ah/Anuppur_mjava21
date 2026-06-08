package com.anuppur.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mst_month")
public class Month {

	 private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "id")
	    private Long Id;

	    @Column(name = "month_name")
	    private String monthName;

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getMonthName() {
			return monthName;
		}

		public void setMonthName(String monthName) {
			this.monthName = monthName;
		}

		
	    
	    
}
