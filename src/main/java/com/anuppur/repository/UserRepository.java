package com.anuppur.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;

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
			+ "and (u.username = COALESCE(:username,u.username)) and (u.emailId= COALESCE( :emailId, u.emailId))) and(u.designationID = COALESCE(:designationId,u.designationID)) ) ")
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

	Users findByUsernameAndStatusNotIn(String emailId, String statusDeleted);

	Users findByMobileNoAndStatusNotIn(String mobileNo, String statusDeleted);

	
	Page<Users> findByStatusNotInAndUsernameAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotInAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotInAndUsernameAndEmailIdAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, Long l, String username);

	Page<Users> findByStatusNotInAndUsernameAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotInAndFirstnameAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotInAndFirstnameAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String mobileNo, Long l, String username);

	Page<Users> findByStatusNotInAndFirstnameAndEmailIdAndDesignationIDAndCreatedBy(Pageable pageable,
			String statusDeleted, String searchParameter, String emailId, Long l, String username);

	Page<Users> findByStatusNotInAndMobileNoAndDesignationIDAndCreatedBy(Pageable pageable, String statusDeleted,
			String mobileNo, Long l, String username);

	List<Users> findByDesignationIDAndStatusNotIn(Long l, String string);

	@Query(value = "SELECT created_by FROM dhs_anuppur.users where id=:userAssignee",nativeQuery = true)
	String findByUserAssinee(@Param("userAssignee")Long  userAssignee);

	
	// create by 
	Page<Users> findByUsernameContainingAndStatusAndUsernameAndEmailIdAndDesignationID(Pageable pageable, String status,
			String username, String emailId, long l);

	Page<Users> findByStatusAndUsernameAndEmailIdAndDesignationID(Pageable pageable, String status, String username,
			String emailId, long l);
	
}
