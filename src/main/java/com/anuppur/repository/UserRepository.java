package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.District;
import com.anuppur.entity.Users;


public interface UserRepository  extends JpaRepository<Users, Long> {
    
	Users findByUsername(String username);
	
	Users findByEmailId(String emailId);
	
	Users findByEmailIdAndStatus(String emailId, String status);
	
	Users findByUsernameAndStatus(String username, String status);
	
	Users findByUsernameAndStatusNot(String username, String status);
	
	Users findByMobileNoAndStatusNot(String mobileNo, String status);
	
	Page<Users> findByUsernameContainingAndStatusNotInAndDistrict(Pageable pageable, String username, String[] status, District district);
	
	Page<Users> findByStatusNotInAndDistrict(Pageable pageable, String[] status, District district);

	@Query("SELECT DISTINCT u FROM Users u JOIN u.role r WHERE "
			+ "r.roleCode = 'ROLE_AREA_OFFICER' "
			+ "AND u.status NOT IN :excludedStatuses "
			+ "AND u.departmentName = :departmentName "
			+ "AND (:district IS NULL OR u.district = :district) "
			+ "AND (:status IS NULL OR u.status = :status) "
			+ "AND (:username IS NULL OR u.username LIKE CONCAT('%', :username, '%')) "
			+ "AND (:emailId IS NULL OR u.emailId = :emailId) "
			+ "AND (:mobileNo IS NULL OR u.mobileNo = :mobileNo) "
			+ "AND (:searchParameter IS NULL OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchParameter, '%'))) ")
	Page<Users> findDepartmentAreaOfficers(
			Pageable pageable,
			@Param("excludedStatuses") String[] excludedStatuses,
			@Param("departmentName") String departmentName,
			@Param("district") District district,
			@Param("status") String status,
			@Param("username") String username,
			@Param("emailId") String emailId,
			@Param("mobileNo") String mobileNo,
			@Param("searchParameter") String searchParameter);
	
	long countByStatusNotInAndDistrict(String[] status,District district);
	
	Users findByEmailIdAndStatusNot(String emailId, String status);
	
	//Users findByIdAndVerificationRandomString(Long id, String verificationRandomString);

	
	@Query("from Users u where (u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE( :emailId, u.emailId)))  ")
	Page<Users> findByUsernameContainingAndStatusAndUsernameAndEmailId(Pageable pageable, 
			 @Param("status")String status, 
			@Param("username")String username, @Param("emailId")String emailId);

	@Query("from Users u where u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE(:emailId, u.emailId)) ")
	Page<Users> findByStatusAndUsernameAndEmailId(Pageable pageable, 
			@Param("status")String status, @Param("username")String username, @Param("emailId")String emailId);

	
	@Query("from Users u where u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE(:emailId, u.emailId)) and (u.designationID = COALESCE(:designationId, u.designationID) ) ")
	Page<Users> findByStatusAndUsernameAndEmailId(Pageable pageable, 
			@Param("status")String status, @Param("username")String username, @Param("emailId")String emailId,@Param("designationId") Long designationId);

	@Query("from Users u where u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE(:emailId, u.emailId)) and (u.designationID = COALESCE(:designationId, u.designationID) ) ")
	Page<Users> findByStatusAndUsernameAndEmailIdAndDesignationIDIn(Pageable pageable, 
			@Param("status")String status, @Param("username")String username, @Param("emailId")String emailId,@Param("designationId") List<Long> designationIds);

	
	@Query("from Users u where u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE(:emailId, u.emailId)) and (u.designationID = COALESCE(:designationId, u.designationID) )  and (u.createdBy = COALESCE(:createdBy, u.createdBy) )")
	Page<Users> findByStatusAndUsernameAndEmailIdAndDesignationIDAndCreatedBy(Pageable pageable, 
			@Param("status")String status, @Param("username")String username, @Param("emailId")String emailId,@Param("designationId") Long designationId,@Param("createdBy") String createdBy);

	
	
	Page<Users> findByEmailIdAndStatusNotInAndDistrict(Pageable pageable, String emailId,
			String[] statusDeletedPendingVerification,District district);

	Users findByMobileNoAndStatus(String emailId, String statusActive);

	Page<Users> findByStatusNotIn(Pageable pageable, String[] statusDeletedPendingVerification);

	Page<Users> findByEmailIdAndStatusNotIn(Pageable pageable, String emailId,
			String[] statusDeletedPendingVerification);

	Page<Users> findByUsernameContainingAndStatusNotIn(Pageable pageable, String searchParameter,
			String[] statusDeletedPendingVerification);
	
	Page<Users> findByFirstnameContainingAndStatusNotIn(Pageable pageable, String searchParameter,
			String[] statusDeletedPendingVerification);

	long countByStatusNotIn(String[] statusDeletedPendingVerification);

	Users findByIdAndVerificationRandomString(Long id, String verificationStr);

	Users findByMobileNo(String mobileNumber);

	Users findByUsernameAndStatusNotContainingIgnoreCase(String username, String statusDeleted);

	
	  @Query("SELECT u FROM Users u JOIN u.role r WHERE ("
	            + "(u.status = COALESCE(:status, u.status)) "
	            + "and (u.username = COALESCE(:username, u.username)) "
	            + "and (u.emailId = COALESCE(:emailId, u.emailId)) "
	            + "and r.roleCode IN ('ROLE_SAU', 'ROLE_DISTRICT', 'ROLE_DEPARTMENT') )"
	            + "or (u.firstname = COALESCE(:firstname, u.firstname)) ")
	  
	    Page<Users> findByUsernameContainingAndStatusAndUsernameAndEmailIdNew(Pageable pageable, 
	            @Param("status") String status, 
	            @Param("username") String username, 
	            @Param("emailId") String emailId,
	            @Param("firstname") String firstname);

	  @Query("SELECT u FROM Users u JOIN u.role r WHERE ("
	            + "(u.status = COALESCE(:status, u.status)) "
	            + "and (u.username = COALESCE(:username, u.username)) "
	            + "and (u.emailId = COALESCE(:emailId, u.emailId))  "
	            + "and r.roleCode IN ('ROLE_SAU', 'ROLE_DISTRICT' , 'ROLE_DEPARTMENT') ) "
	            + "or (u.firstname = COALESCE(:firstname, u.firstname)) ")
	  Page<Users> findByStatusNotInNew(Pageable pageable, @Param("status") String status, 
	            @Param("username") String username, 
	            @Param("emailId") String emailId,
	            @Param("firstname") String firstname);

	List<Users> findByImplementationAgency(Users userId);
	@Query("from Users u where (u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE( :emailId, u.emailId)) and (u.designationID = COALESCE(:designationId,u.designationID))) ")
	Page<Users> findByUsernameContainingAndStatusAndUsernameAndEmailIdAndDesignationIDIn(Pageable pageable, 
			 @Param("status")String status, 
			@Param("username")String username, @Param("emailId")String emailId,@Param("designationId") List<Long> designationIds);

	Page<Users> findByFirstnameContainingAndStatusNotInAndDesignationIDIn(Pageable pageable, String searchParameter,
			String[] statusDeletedPendingVerification, List<Long> designationIds);

	Page<Users> findByEmailIdAndStatusNotInAndDesignationIDIn(Pageable pageable, String emailId,
			String[] statusDeletedPendingVerification, List<Long> designationIds);

	Page<Users> findByStatusNotInAndDesignationID(Pageable pageable, String[] statusDeletedPendingVerification, long l);

	Page<Users> findByFirstnameContainingAndStatusAndDesignationID(Pageable pageable, String searchParameter,
			String statusPending, Long l);

	Page<Users> findByStatusAndDesignationID(Pageable pageable, String statusPending, Long l);

	Page<Users> findByEmailIdAndStatusAndDesignationID(Pageable pageable, String emailId, String statusPending, Long l);

	Page<Users> findByStatusNotInAndDesignationIDInAndCreatedBy(Pageable pageable,
			String[] statusDeletedPendingVerification, List<Long> designationIds, String username);

	Page<Users> findByEmailIdAndStatusNotInAndDesignationIDAndCreatedBy(Pageable pageable, String emailId,
			String[] statusDeletedPendingVerification, Long l, String username);

	Page<Users> findByFirstnameContainingAndStatusNotInAndDesignationIDAndCreatedBy(Pageable pageable,
			String searchParameter, String[] statusDeletedPendingVerification, Long l, String username);

	List<Users> findByDesignationIDAndStatus(long ld, String string);

	Page<Users> findByFirstnameContainingAndDesignationID(Pageable pageable, String searchParameter, Long l);

	Page<Users> findByDesignationID(Pageable pageable, Long l);

	Page<Users> findByEmailIdAndDesignationID(Pageable pageable, String emailId, Long l);

	Long countByDesignationID(Long l);
	
	
	@Query("SELECT u FROM Users u WHERE " +
		       "(:status IS NULL OR u.status = :status) " +
		       "AND (:excludedStatuses IS NULL OR u.status NOT IN :excludedStatuses) " +
		       "AND (:username IS NULL OR u.username LIKE CONCAT('%', :username, '%')) " +
		       "AND (:emailId IS NULL OR u.emailId = :emailId) " +
		       "AND (:designationId IS NULL OR u.designationID = :designationId) " +
		       "AND (:searchParameter IS NULL OR u.firstname LIKE CONCAT('%', :searchParameter, '%')) " +
		       "AND (:createdBy IS NULL OR u.createdBy = :createdBy)")
		List<Users> findUsersByDynamicFilters(
		    @Param("status") String status,
		    @Param("excludedStatuses") List<String> excludedStatuses,
		    @Param("username") String username,
		    @Param("emailId") String emailId,
		    @Param("designationId") Long designationId,
		    @Param("searchParameter") String searchParameter,
		    @Param("createdBy") String createdBy);

	@Query("from Users u where u.username = :username and u.status != :status")
	Users findByUsernameAndStatusNotIn(@Param("username") String username, @Param("status") String status);

	@Query("from Users u where u.mobileNo = :mobileNo and u.status != :status")
	Users findByMobileNoAndStatusNotIn(@Param("mobileNo") String mobileNo, @Param("status") String status);

	
	Page<Users> findByStatusNotAndUsernameAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotAndUsernameAndEmailIdAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, Long l, String username);

	Page<Users> findByStatusNotAndUsernameAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotAndFirstnameAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotAndFirstnameAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotAndFirstnameAndEmailIdAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, Long l, String username);

	Page<Users> findByStatusNotAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable, String statusDeleted,
			String mobileNo, Long l, String username);

	List<Users> findByDesignationIDAndStatusNot(Long l, String string);

	@Query(value = "SELECT created_by FROM dhs_anuppur.users where id=:userAssignee",nativeQuery = true)
	String findByUserAssinee(@Param("userAssignee")Long  userAssignee);

	
	// create by 
	@Query("from Users u where (u.status = COALESCE(:status,u.status) "
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE( :emailId, u.emailId)) and (u.designationID = COALESCE(:designationId,u.designationID))) ")
	Page<Users> findByUsernameContainingAndStatusAndUsernameAndEmailIdAndDesignationID(Pageable pageable, String status,
			String username, String emailId, long designationId);

	Page<Users> findByStatusAndUsernameAndEmailIdAndDesignationID(Pageable pageable, String status, String username,
			String emailId, long l);
	
}
