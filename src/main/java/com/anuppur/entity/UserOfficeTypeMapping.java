package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="mst_user_office_type_role_mapping")
public class UserOfficeTypeMapping implements Serializable{
	
	  private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    private Long id;
	    
	    private Short enabled;
	    
	    
	    
	    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id")
		@ManyToOne
		private UserType userType;
	    
	    @JoinColumn(name = "office_type_id", referencedColumnName = "office_type_id")
	   	@ManyToOne
	   	private OfficeType officeType;
	    
	    @JoinColumn(name = "role_code", referencedColumnName = "role_code")
	   	@ManyToOne
	   	private Role role;

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

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

		public UserType getUserType() {
			return userType;
		}

		public void setUserType(UserType userType) {
			this.userType = userType;
		}

		public OfficeType getOfficeType() {
			return officeType;
		}

		public void setOfficeType(OfficeType officeType) {
			this.officeType = officeType;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	

}
