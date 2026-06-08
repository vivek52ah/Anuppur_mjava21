package com.anuppur.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;

@Entity  
@Table(name = "users")
public class Users extends Auditable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	 @Column(name = "username")
    private String username;
    
    @Column(name = "first_name")
    private String firstname;
    
    @Column(name = "last_name")
    private String lastname;
    
    private String password;
    
   // private String name;
    
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	
	@JoinColumn(name = "district_id", referencedColumnName = "id")
    @OneToOne
    private District district;
	
	@Column(name = "department_name")
	private  String departmentName;
	
	@JoinColumn(name = "agency_id", referencedColumnName = "id")
    @OneToOne
    private ImplementationAgency implementationAgency;
	
	
	@Column(name="created_by" , updatable = false)
	@CreatedBy
	private String createdBy;
	
	@JoinColumn(name = "user_type_id")
    @OneToOne
    private UserType userType;
	
	
	@JoinColumn(name = "office_type_id")
    @OneToOne
    private OfficeType officeType;
	
	
	@Column(name="designation_id")
	private Long designationID;
	
	/*
	 * @Column(name = "official_email_id") private String officialEmailId;
	 * 
	 * @Column(name = "official_phone") private String officialPhone;
	 */
    
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_code"))
	private Set<Role> role;
	  
	  
	

		/*
		 * @ManyToMany(cascade = CascadeType.ALL)
		 * 
		 * @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "ID"),
		 * inverseJoinColumns = @JoinColumn (name = "USER_TYPE_ID")) private
		 * Set<UserType> userTypes;
		 * 
		 * 
		 * @ManyToMany(cascade = CascadeType.ALL)
		 * 
		 * @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "ID"),
		 * inverseJoinColumns = @JoinColumn (name = "OFFICE_TYPE_ID")) private
		 * Set<OfficeType> officeTypes;
		 * 
		 */
	 
		/*
		 * @ManyToMany(cascade = CascadeType.ALL)
		 * 
		 * @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "ID")},
		 * inverseJoinColumns = { @JoinColumn (name = "ROLE_CODE"),@JoinColumn (name =
		 * "USER_TYPE_ID"),@JoinColumn (name = "OFFICE_TYPE_ID")})
		 */
	  
	 
    
    private String status;
    
	
	 @Column(name = "verification_random_str") private String
	 verificationRandomString;
	
	
  
    
    @JoinColumn(name = "division_id", referencedColumnName = "id")
   	@OneToOne
   	private Division division;
    
    public ImplementationAgency getImplementationAgency() {
		return implementationAgency;
	}

	public void setImplementationAgency(ImplementationAgency implementationAgency) {
		this.implementationAgency = implementationAgency;
	}

	public Users() {
		super();
	}
	

	public Long getDesignationID() {
		return designationID;
	}

	public void setDesignationID(Long designationID) {
		this.designationID = designationID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
	
	  public Set<Role> getRoles() { return role; }
	  
	 public void setRoles(Set<Role> role) { this.role = role; }
	 
	 
	  
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String state) {
		this.status = state;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public String getVerificationRandomString() {
		return verificationRandomString;
	}

	public void setVerificationRandomString(String verificationRandomString) {
		this.verificationRandomString = verificationRandomString;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/*
	 * public String getOfficialEmailId() { return officialEmailId; }
	 * 
	 * public void setOfficialEmailId(String officialEmailId) { this.officialEmailId
	 * = officialEmailId; }
	 * 
	 * public String getOfficialPhone() { return officialPhone; }
	 * 
	 * public void setOfficialPhone(String officialPhone) { this.officialPhone =
	 * officialPhone; }
	 */
	@Column(name = "last_password_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPasswordUpdatedOn;

	
	public Date getLastPasswordUpdatedOn() {
		return lastPasswordUpdatedOn;
	}

	public void setLastPasswordUpdatedOn(Date lastPasswordUpdatedOn) {
		this.lastPasswordUpdatedOn = lastPasswordUpdatedOn;
	}
	
	
}
