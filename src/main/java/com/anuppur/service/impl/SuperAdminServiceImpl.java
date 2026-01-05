package com.anuppur.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.OfficeTypeBean;
import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.UserTypeBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Designation;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.LegislativeConstituency;
import com.anuppur.entity.OfficeType;
import com.anuppur.entity.Role;
import com.anuppur.entity.UserOfficeTypeMapping;
import com.anuppur.entity.UserType;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.json.UserJson;
import com.anuppur.repository.CCRepository;
import com.anuppur.repository.DesignationRepository;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.OfficeTypeRepository;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.SchemeRepository;
import com.anuppur.repository.TSASWorkRepository;
import com.anuppur.repository.UserOfficeTypeMappingRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.UserTypeRepository;
import com.anuppur.repository.WorkProgressRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.repository.WorkTenderRepository;
import com.anuppur.service.NotificationService;
import com.anuppur.service.SuperAdminService;
import com.anuppur.util.DMSUtil;
import com.anuppur.util.SHAHashingUtil;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

	public static final Logger logger = LoggerFactory.getLogger(SuperAdminServiceImpl.class);

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository; 
	
	@Autowired
	public UserTypeRepository userTypeRepository; 
	
	@Autowired
	private NotificationService notificationService;
	
	
	@Autowired
	public UserOfficeTypeMappingRepository userOfficeTypeMappingRepository; 
	
	@Autowired
	public ImplAgencyRepository implAgencyRepository;
	

	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	public OfficeTypeRepository officeTypeRepository; 

	@Autowired
	public SchemeRepository schemeRepository;
	
	@Autowired
	public WorkRepository workRepository;
	
	@Autowired
	public TSASWorkRepository tsasWorkRepository;
	
	@Autowired
	public WorkTenderRepository workTenderRepository;
	
	@Autowired
	public WorkProgressRepository workProgressRepository;
	
	@Autowired
	public CCRepository ccRepository;
	






	private String []statusDeletedPendingVerification = new String []{DMSConstants.STATUS_DELETED,
			DMSConstants.STATUS_PENDING_VERIFICATION};

	@Override
	public UserJson getAllUsers(Pageable pageable, UserBean be,String searchParameter,String mobileNo, String status, String username, String emailId){
		
		User user = DMSUtil.getUserDetail();
		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		District district = userEntity.getDistrict();
		
		UserJson userJson = null;
		if("".equals(username)) {
			username = null;
		}
		if("".equals(emailId)) {
			emailId = null;
		}
		if("".equals(status)) {
			status = null;
		}
		if("".equals(mobileNo)) {
			mobileNo = null;
		}
		
		try{
			Page<Users> users = null;
			
			
			List<Long> designationIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
			
			if(be.getRolee().equals("ROLE_SYSTEM_ADMIN")) {
				
				
				
				if(!StringUtils.isEmpty(searchParameter) && (!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)))
					users = userRepository.findByUsernameContainingAndStatusAndUsernameAndEmailIdAndDesignationIDIn(pageable,  
							status, username, emailId,designationIds);
				else if(!StringUtils.isEmpty(searchParameter))
					users = userRepository.findByFirstnameContainingAndStatusNotInAndDesignationIDIn(pageable, searchParameter, statusDeletedPendingVerification,designationIds);
					//users = userRepository.findByUsernameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
				else if(!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)) {
					if(status==null) {
						users = userRepository.findByEmailIdAndStatusNotInAndDesignationIDIn(pageable, emailId, statusDeletedPendingVerification,designationIds);
					}else
						users = userRepository.findByStatusAndUsernameAndEmailIdAndDesignationIDIn(pageable,  status, username, emailId,designationIds);
				}
				else
					users = userRepository.findByStatusNotInAndDesignationIDInAndCreatedBy(pageable, statusDeletedPendingVerification,designationIds,be.getUsername());
				
			}
			
			
			
			
			if(be.getRolee().equals("ROLE_DEPARTMENT")) {
				
				
				if(!StringUtils.isEmpty(searchParameter) && (!StringUtils.isEmpty(mobileNo)  && (!StringUtils.isEmpty(emailId)))) {
					 
					users = userRepository.findByStatusNotInAndFirstnameAndEmailIdAndMobileNoAndDesignationIDAndCreatedBy(pageable,  
							DMSConstants.STATUS_DELETED, searchParameter,emailId,mobileNo,1L,be.getUsername());
				}
				else if(!StringUtils.isEmpty(mobileNo) ) {
					users = userRepository.findByStatusNotInAndMobileNoAndDesignationIDAndCreatedBy(pageable,  
							DMSConstants.STATUS_DELETED,mobileNo,1L,be.getUsername());
				}
				
				
			else if(!StringUtils.isEmpty(searchParameter) && (!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)))
					users = userRepository.findByStatusAndUsernameAndEmailIdAndDesignationIDAndCreatedBy(pageable,  
							status, username, emailId,1L,be.getUsername());
				else if(!StringUtils.isEmpty(searchParameter))
					users = userRepository.findByFirstnameContainingAndStatusNotInAndDesignationIDAndCreatedBy(pageable, searchParameter, statusDeletedPendingVerification,1L,be.getUsername());
					//users = userRepository.findByUsernameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
				else if(!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)) {
					if(status==null) {
						users = userRepository.findByEmailIdAndStatusNotInAndDesignationIDAndCreatedBy(pageable, emailId, statusDeletedPendingVerification,1L,be.getUsername());
					}else
						users = userRepository.findByStatusAndUsernameAndEmailIdAndDesignationIDAndCreatedBy(pageable,  status, username, emailId,1L,be.getUsername());
				}
				
				else
					users = userRepository.findByStatusNotInAndDesignationIDInAndCreatedBy(pageable, statusDeletedPendingVerification,designationIds,be.getUsername());
				
				
			}
			
			
			
			
			
			/*
			if(searchParameter!= null && !searchParameter.isEmpty())
				users = userRepository.findByNameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
			else
				users = userRepository.findByStatusNotIn(pageable, statusDeletedPendingVerification);*/
			
			if(users!=null){
				List<Users> entityList = users.getContent();
				List<UserBean> beanList = new ArrayList<>();
				if(entityList!= null && !entityList.isEmpty()){
					
					int index = pageable.getPageNumber()*pageable.getPageSize();
					List<Users> dmusers  =   userRepository.findByDesignationIDAndStatusNotIn(3L, "Deleted");
					
					/*
					 * for(Users use : dmusers) { if(be.getRolee().equals("ROLE_SYSTEM_ADMIN") &&
					 * pageable.getPageNumber()==0) { UserBean bean = convertUserEntityToBean(use);
					 * bean.setIndex(++index); beanList.add(bean);} }
					 */
					
					for(Users entity : entityList){
						
						UserBean bean = convertUserEntityToBean(entity);
						bean.setIndex(++index);
						
						
						
						beanList.add(bean);
					}
					
				}
				
				userJson = new UserJson();
				
				long filteredCount = users.getTotalElements(); // Filtered count from JPA Page object
				userJson.setiTotalRecords(filteredCount);
				userJson.setiTotalDisplayRecords(filteredCount);
				
				//userJson.setiTotalDisplayRecords(users.getTotalElements());
				//userJson.setiTotalRecords(userRepository.countByStatusNotIn(statusDeletedPendingVerification));
				userJson.setAaData(beanList);
			}
			return userJson;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return userJson;
		}
	}
	
	@Override
	public UserBean fetchUserDetails(Long id){
		
		try{
			Users entity = userRepository.findOne(id);
		
			return convertUserEntityToBean(entity);
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}
	
	@Override
	public List<RoleBean> fetchRoles(){
		
		try{
			List<Role> roles = roleRepository.findAll();
			
			List<RoleBean> beanList = new ArrayList<>();
			for(Role role : roles){
				beanList.add(convertRoleEntityToBean(role));
			}
			return beanList;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
		
	}
	
	
	
//	private String[] workStatusVerification = new String[] { DMSConstants.WORK_STATUS_Work_Created,
//			DMSConstants.WORK_STATUS_AA_Issued, DMSConstants.WORK_STATUS_Tender_Called_date,
//			DMSConstants.WORK_STATUS_Tender_Received, DMSConstants.WORK_STATUS_LoA_Issued,
//			DMSConstants.WORK_STATUS_Work_Order_Issued, DMSConstants.WORK_STATUS_Not_Started,
//			DMSConstants.WORK_STATUS_In_Progress, DMSConstants.WORK_STATUS_Completed,
//			DMSConstants.WORK_STATUS_Handed_Over, DMSConstants.WORK_STATUS_CC_Uploaded,
//			DMSConstants.WORK_STATUS_Re_Tender };
	
	//private String workStatusVerification = "Tender Received,LoA Issued";
	
	
	@Override
	public List<UserTypeBean> fetchUserType(){
		
		try{
			List<UserType> userTypes = userTypeRepository.findAll();
			
			List<UserTypeBean> beanList = new ArrayList<>();
			for(UserType userType : userTypes){
				beanList.add(convertUserTypeEntityToBean(userType));
			}
			return beanList;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
		
	}
	
	
	@Override
	public List<OfficeTypeBean> fetchOfficeType(){
		
		try{
			List<OfficeType> officeTypes = officeTypeRepository.findAll();
			
			List<OfficeTypeBean> beanList = new ArrayList<>();
			for(OfficeType officeType : officeTypes){
				beanList.add(convertOfficeTypeEntityToBean(officeType));
			}
			return beanList;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
		
	}
	
	
	
	
	
	@Override
	public List<OfficeTypeBean> fetchOfficeTypesByUserType(Long userTypeId) {
		try {
			List<UserOfficeTypeMapping> userOfficeTypeMappingList = userOfficeTypeMappingRepository
					.findByUserTypeAndEnabled(new UserType(userTypeId), (short) 1);
			List<Long> officeTypeIds = null;
			if (userOfficeTypeMappingList != null && !userOfficeTypeMappingList.isEmpty()) {
				officeTypeIds = new ArrayList<Long>();
				for (UserOfficeTypeMapping d : userOfficeTypeMappingList) {
					if (d.getOfficeType().getId() != null) {
						officeTypeIds.add(d.getOfficeType().getId());
					}
				}
			}
			List<OfficeTypeBean> beanList = new LinkedList<>();
			if (null != officeTypeIds) {
				List<OfficeType> list = officeTypeRepository.findByIdIn(officeTypeIds);
				if (null != list && !list.isEmpty()) {
					for (OfficeType officeType : list) {
						beanList.add(convertOfficeTypeEntityToBean(officeType));
					}
				}
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}
	
	
	
	
	@Override
	public List<RoleBean> fetchRoleTypesByUserOfficeType(Long userTypeId,Long officeTypeId) {
		try {
			List<UserOfficeTypeMapping> userOfficeTypeMappingList = userOfficeTypeMappingRepository
					.findByUserTypeAndOfficeTypeAndEnabled(new UserType(userTypeId),new OfficeType(officeTypeId), (short) 1);
			List<String> rolecods = null;
			if (userOfficeTypeMappingList != null && !userOfficeTypeMappingList.isEmpty()) {
				rolecods = new ArrayList<String>();
				for (UserOfficeTypeMapping d : userOfficeTypeMappingList) {
					if (d.getRole().getRoleCode() != null) {
						rolecods.add(d.getRole().getRoleCode());
					}
				}
			}
			List<RoleBean> beanList = new LinkedList<>();
			if (null != rolecods) {
				
				List<Role> list=roleRepository.findAll(rolecods);
				if (null != list && !list.isEmpty()) {
					for (Role role : list) {
						beanList.add(convertRoleEntityToBean(role));
					}
				}
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}
	
	@Override
	public String addUser(UserBean bean){
		
		try{
			
			Users entity =null;
			 entity = userRepository.findByUsernameAndStatusNotIn(bean.getEmailId(), DMSConstants.STATUS_DELETED);
			if(entity!= null){
				return "User with given Email Already exist!";
				
				
			}else{
			 entity = userRepository.findByMobileNoAndStatusNotIn(bean.getMobileNo(), DMSConstants.STATUS_DELETED);
			 if (entity !=null) {
				 return "User with given Mobile Number Already exist!";
			 }else {
				    entity = new Users();
					convertUserBeanToEntity(entity, bean);
					
					Set<Role> roles = new HashSet<>();
				//	roles.add(roleRepository.findOne(bean.getRole().getRoleCode()));
					if(bean.getDesignationId() == 1L) {
						roles.add(roleRepository.findOne(new Role("ROLE_AREA_OFFICER").getRoleCode()));
						
							entity.setStatus(DMSConstants.STATUS_PENDING);
						    entity.setDepartmentName( userRepository.findByUsernameAndStatus(DMSUtil.getUserDetail().getUsername(),"Active").getDepartmentName() );
						
					}
					if(bean.getDesignationId() == 2L) {
						roles.add(roleRepository.findOne(new Role("ROLE_DEPARTMENT").getRoleCode()));
					}
					if(bean.getDesignationId() == 3L) {
						roles.add(roleRepository.findOne(new Role("ROLE_DM").getRoleCode()));
						List<Users> DMUser=userRepository.findByDesignationIDAndStatus(3L, "Active");
						 if(DMUser != null
								 && !DMUser.isEmpty()) {
							 return "ALready An Active DM Available";
						 }
					}
						 if(bean.getDesignationId() == 5L) {
								roles.add(roleRepository.findOne(new Role("ROLE_CEO").getRoleCode()));
								List<Users> DMUsers=userRepository.findByDesignationIDAndStatus(5L, "Active");
								 if(DMUsers != null
										 && !DMUsers.isEmpty()) {
									 return "ALready An Active CEO Available";
								 }
					
					}
					entity.setRoles(roles);
					 
					String verificationRandomString = SHAHashingUtil
							.encryptPassword(SHAHashingUtil.generatePassword())
							.toString();
					
					entity.setVerificationRandomString(verificationRandomString);
					/*
					 * Set<UserType> usertypes = new HashSet<>();
					 * usertypes.add(userTypeRepository.findById(bean.getUserTypes().getUserTypeId()
					 * ));
					 * 
					 * entity.setUserTypes(usertypes);
					 * 
					 * Set<OfficeType> officetypes = new HashSet<>();
					 * officetypes.add(officeTypeRepository.findById(bean.getOfficeType().
					 * getOfficeTypeId())); entity.setOfficeTypes(officetypes);
					 */
					//entity.setCreatedBy(DMSUtil.getUserDetail().getUsername());
					
					 userRepository.save(entity);
					
					notificationService.sendUserRegistrationMessage(bean);
					
					
					/*
					 * String emailLink = verifyServiceUrl + "?id=" + entity.getId() +
					 * "&verificationStr=" + verificationRandomString;
					 * 
					 * // sending email for verification
					 * notificationService.sendRegistrationNotification( user.getEmailId(),
					 * user.getMobileNo(), emailLink);
					 */
					
					
					
					return null;
				 
			 }
				
			}
		}catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}
	
	@Override
	public String editUser(UserBean bean, String websiteURL){
		
		try{
			logger.info("edit me i");
			Users entity = userRepository.findOne(bean.getId());
			if(!entity.getEmailId().equals(bean.getEmailId())){//if EmailId has changed
				//check whether already exist
				Users user = userRepository.findByUsernameAndStatusNot(bean.getEmailId(), DMSConstants.STATUS_DELETED);
				if(user!= null){
					//already present
					return "User with given Email Already exist!";
				}
				user =  userRepository.findByMobileNoAndStatusNotIn(bean.getMobileNo(),DMSConstants.STATUS_DELETED);
				if(user!= null) {
					
				}
			}
			if(!entity.getMobileNo().equals(bean.getMobileNo())) {
				Users	user =  userRepository.findByMobileNoAndStatusNotIn(bean.getMobileNo(),DMSConstants.STATUS_DELETED);
				if(user!= null) {
					return "User with mobile no already there ";
				}
			}
			convertUserBeanToEntity(entity, bean);
			
			Set<Role> roles = new HashSet<>();
			if(bean.getDesignationId() == 1L) {
				roles.add(roleRepository.findOne(new Role("ROLE_AREA_OFFICER").getRoleCode()));
				if(!bean.getStatus().isEmpty()) {
					entity.setStatus(bean.getStatus());
				}else {
					entity.setStatus(DMSConstants.STATUS_PENDING);
				}
			}
			if(bean.getDesignationId() == 2L) {
				roles.add(roleRepository.findOne(new Role("ROLE_DEPARTMENT").getRoleCode()));
			}
			if(bean.getDesignationId() == 3L) {
				roles.add(roleRepository.findOne(new Role("ROLE_DM").getRoleCode()));
			}
			entity.setRoles(roles);
			
			userRepository.save(entity);
			
			// When status change from Pending Activation to Active - User activation happens
//			if(bean.getOldStatus()!= null) {
//				if (bean.getOldStatus().equals(
//						DMFConstants.STATUS_PENDING_ACTIVATION)
//						&& !bean.getOldStatus().equals(bean.getStatus())) {
//					
//					notificationService.sendAccountActivationNotification(
//							entity.getEmailId(), entity.getMobileNo(), websiteURL, "");
//				}
//			}
			return null;
			
		}catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}
	
	@Override
	public String deleteUser(Long id){
		
		try{
			Users entity = userRepository.findOne(id);
			if(entity!=null){
				entity.setStatus(DMSConstants.STATUS_DELETED);
				userRepository.save(entity);
			}
			return null;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}
	@Override
	public UserBean convertUserEntityToBean(Users user){
		
		UserBean bean = new UserBean();
		
		if(user!=null){
			bean.setId(user.getId());
			bean.setDesignationId(user.getDesignationID());
			//bean.setName(user.getName());
			bean.setUsername(user.getUsername());
			bean.setEmailId(user.getEmailId());
			bean.setMobileNo(user.getMobileNo());
			bean.setFirstName(user.getFirstname());
			bean.setLastName(user.getLastname());
			//bean.setOfficialEmailId(user.getOfficialEmailId());
			//bean.setOfficialPhone(user.getOfficialPhone());
			
			bean.setDepartmentName(user.getDepartmentName());
			
			bean.setStatus(user.getStatus());
			bean.setOldStatus(user.getStatus());
			
			Set<Role> roles = user.getRoles();
			if(roles!=null && !roles.isEmpty()){
				for (Role role : roles){
					bean.setRole(convertRoleEntityToBean(role));
				}
			}else{
				bean.setRole(new RoleBean("",""));
			}
			bean.setRolee(bean.getRole().getRoleCode());
			bean.setDistrictId(user.getDistrict()!=null ? user.getDistrict().getDistrictId() : 0);
			bean.setDistrictName(user.getDistrict()!=null ? user.getDistrict().getDistrictName() : "-");
			bean.setDivisionId(user.getDivision()!=null ? user.getDivision().getDivisionId() : 0);
			bean.setDivisionName(user.getDivision()!=null ? user.getDivision().getDivisionName() : "-");
			
			bean.setUserTypeId(user.getUserType()!=null ? user.getUserType().getId() : 0);
			bean.setUserTypeName(user.getUserType()!=null ? user.getUserType().getUserType() :"-");
			
			bean.setOfficeTypeId(user.getOfficeType()!=null ? user.getOfficeType().getId() : 0);
			bean.setOfficeTypeName(user.getOfficeType()!=null ? user.getOfficeType().getOfficeTypeName() :"-");
			
			bean.setImplementationAgencyId(user.getImplementationAgency()!=null ? user.getImplementationAgency().getImplementationAgencyId() : 0);
			bean.setImplementationAgencyName(user.getImplementationAgency()!=null ? user.getImplementationAgency().getImplAgencyname() :"-");
			//System.err.println(user.getCreatedBy() + "asdasdasdasdas");
			/*
			 * if(userRepository.findByUsername(user.getCreatedBy())!=null) {
			 * 
			 * bean.setDepartmentName(userRepository.findByUsername(user.getCreatedBy()).
			 * getDepartmentName()); }
			 */		
			if(user.getDesignationID()!=null) {
			if(user.getDesignationID() == 3L) {
				bean.setDepartmentName("Collectorate");
				
			}
			Designation designation = designationRepository.findOne(user.getDesignationID());
			if(designation != null) {
			bean.setDesignationName(designation.getDesignationNameEnglish());
			}
			}
		}
		return bean;
	}
	
	private Users convertUserBeanToEntity(Users entity, UserBean bean) throws DMSBusinessException{
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		
		if(entity!=null && bean!=null){
			if(bean.getId()!=null){
				entity.setId(bean.getId());
			}
	        //entity.setDesignationID(bean);
			//entity.setName(bean.getName());
			entity.setFirstname(bean.getFirstName());
			entity.setLastname(bean.getLastName());
			entity.setUsername(bean.getEmailId());
			if(!StringUtils.isEmpty(bean.getPassword())){
				entity.setPassword(passwordEncoder.encode(SHAHashingUtil.encryptPassword(bean.getPassword())));
			}
			entity.setEmailId(bean.getEmailId());
			entity.setMobileNo(bean.getMobileNo());
			entity.setDesignationID(bean.getDesignationId());
			entity.setDepartmentName(bean.getDepartmentName());
			//entity.setOfficialEmailId(bean.getOfficialEmailId());
			//entity.setOfficialPhone(bean.getOfficialPhone());
			
			
			if(!StringUtils.isEmpty(bean.getStatus())){
				entity.setStatus(bean.getStatus());
			}else{
				entity.setStatus(DMSConstants.STATUS_ACTIVE);
			}
			
			if(bean.getDistrictId()!=null && bean.getDistrictId()!=0)
				entity.setDistrict(new District(bean.getDistrictId()));
			
			if(bean.getDivisionId()!=null && bean.getDivisionId()!=0)
				entity.setDivision(new Division(bean.getDivisionId()));
			
			if(bean.getImplementationAgencyId()!=null && bean.getImplementationAgencyId()!=0)
				entity.setImplementationAgency(new ImplementationAgency(bean.getImplementationAgencyId()));
			
			if(bean.getUserTypeId()!=null && bean.getUserTypeId()!=0)
				entity.setUserType(new UserType(bean.getUserTypeId()));
			
			if(bean.getOfficeTypeId()!=null && bean.getOfficeTypeId()!=0)
				entity.setOfficeType(new OfficeType(bean.getOfficeTypeId()));
		}
		return entity;
	}
	
	private RoleBean convertRoleEntityToBean(Role role){

		RoleBean bean = new RoleBean();

		if(role!=null){
			bean.setRoleCode(role.getRoleCode());
			bean.setRoleName(role.getRoleName());
		}
		return bean;
	}
	
	
	private UserTypeBean convertUserTypeEntityToBean(UserType userType){

		UserTypeBean bean = new UserTypeBean();

		if(userType!=null){
			
			bean.setUserTypeId(userType.getId());
			bean.setUserType(userType.getUserType());
		}
		return bean;
	}
	
	
	private OfficeTypeBean convertOfficeTypeEntityToBean(OfficeType officeType){

		OfficeTypeBean bean = new OfficeTypeBean();

		if(officeType!=null){
			bean.setOfficeTypeId(officeType.getId());
			bean.setOfficeTypeName(officeType.getOfficeTypeName());
		}
		return bean;
	}
	
	
	
	@Override
	public List<ImplAgencyBean> fetchConstructionAgency(){
		
		try{
			List<ImplementationAgency> agencies =implAgencyRepository.findByEnabled((short) 1);
			
			List<ImplAgencyBean> beanList = new ArrayList<>();
			for(ImplementationAgency agency : agencies){
				beanList.add(convertAgencyEntityToBean(agency));
			}
			return beanList;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
		
	}
	
	
	private ImplAgencyBean convertAgencyEntityToBean(ImplementationAgency implementationAgency){

		ImplAgencyBean bean = new ImplAgencyBean();

		if(implementationAgency!=null){
			
			bean.setImplementationAgencyId(implementationAgency.getImplementationAgencyId());
			bean.setImplementationAgencyNameE(implementationAgency.getImplAgencyname());
		}
		return bean;
	}
	
	
	@Override
	public UserJson getAllAgencyusers(Pageable pageable, String searchParameter, String status, String username, String emailId){
		
		User user = DMSUtil.getUserDetail();
		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		District district = userEntity.getDistrict();
		
		UserJson userJson = null;
		if("".equals(username)) {
			username = null;
		}
		if("".equals(emailId)) {
			emailId = null;
		}
		if("".equals(status)) {
			status = null;
		}
		
		try{
			Page<Users> users = null;
			if(!StringUtils.isEmpty(searchParameter) && (!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId))) {
				users = userRepository.findByUsernameContainingAndStatusAndUsernameAndEmailIdNew(pageable,  
						status, username, emailId, searchParameter);
			
				logger.info("--------------------------111111111");
			/*else if(!StringUtils.isEmpty(searchParameter))
				users = userRepository.findByFirstnameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
				//users = userRepository.findByUsernameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
			else if(!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)) {
				if(status==null)*/ 
				/*	users = userRepository.findByEmailIdAndStatusNotIn(pageable, emailId, statusDeletedPendingVerification);
				}else
					users = userRepository.findByStatusAndUsernameAndEmailId(pageable,  status, username, emailId);
		*/	
			}  else {
				logger.info("--------------------------22222222222222222222");
				users = userRepository.findByStatusNotInNew(pageable, status, username, emailId, searchParameter);
		  
			}
			
			/*
			if(searchParameter!= null && !searchParameter.isEmpty())
				users = userRepository.findByNameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
			else
				users = userRepository.findByStatusNotIn(pageable, statusDeletedPendingVerification);*/
			
			if(users!=null){
				List<Users> entityList = users.getContent();
				List<UserBean> beanList = new ArrayList<>();
				if(entityList!= null && !entityList.isEmpty()){
					
					int index = pageable.getPageNumber()*pageable.getPageSize();
					for(Users entity : entityList){
						
						UserBean bean = convertUserEntityToBean(entity);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				userJson = new UserJson();
				userJson.setiTotalDisplayRecords(users.getTotalElements());
				userJson.setiTotalRecords(userRepository.countByStatusNotIn(statusDeletedPendingVerification));
				userJson.setAaData(beanList);
			}
			return userJson;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return userJson;
		}
	}
	
	@Override
	public List<Designation> fetchDesignation(UserBean bean) {
		
		try {
			
			if(bean.getRolee().equals("ROLE_DEPARTMENT")) {
			return  Arrays.asList(designationRepository.findOne(1L));
		    }
		    if(bean.getRolee().equals("ROLE_SYSTEM_ADMIN")) {
		    	logger.info( designationRepository.findOne(2L).getDesignationNameEnglish()+" "+designationRepository.findOne(3L) .getDesignationNameEnglish());
			return  Arrays.asList(designationRepository.findOne(2L),designationRepository.findOne(3L), designationRepository.findOne(5L));
		
		    }
			return null;
		} catch (Exception e) {
			
			logger.error("Error Fetching Designation",e);
			return null;
		}
		
				 
	}
	@Override
	public UserJson fetchUserListForApproval(Pageable pageable, UserBean be,String searchParameter, String status, String username, String emailId) {
		User user = DMSUtil.getUserDetail();
		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		District district = userEntity.getDistrict();
		
		UserJson userJson = null;
		if("".equals(username)) {
			username = null;
		}
		if("".equals(emailId)) {
			emailId = null;
		}
		if("".equals(status)) {
			status = null;
		}
		
		try{
			Page<Users> users = null;
			
			
			
				
				if(!StringUtils.isEmpty(searchParameter) && (!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)))
					users = userRepository.findByUsernameContainingAndStatusAndUsernameAndEmailIdAndDesignationID(pageable,  
							status, username, emailId,1L);
				else if(!StringUtils.isEmpty(searchParameter))
					users = userRepository.findByFirstnameContainingAndDesignationID(pageable, searchParameter, 1L);
					//users = userRepository.findByUsernameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
				else if(!StringUtils.isEmpty(status) || !StringUtils.isEmpty(username) || !StringUtils.isEmpty(emailId)) {
					if(status==null) {
						users = userRepository.findByEmailIdAndDesignationID(pageable, emailId,1L);
					}else
						users = userRepository.findByStatusAndUsernameAndEmailIdAndDesignationID(pageable,  status, username, emailId,1L);
				}
				else {
					users = userRepository.findByDesignationID(pageable,1L);}
				
				
			
			
			
			
			
			
			/*
			if(searchParameter!= null && !searchParameter.isEmpty())
				users = userRepository.findByNameContainingAndStatusNotIn(pageable, searchParameter, statusDeletedPendingVerification);
			else
				users = userRepository.findByStatusNotIn(pageable, statusDeletedPendingVerification);*/
			
			if(users!=null){
				List<Users> entityList = users.getContent();
				List<UserBean> beanList = new ArrayList<>();
				if(entityList!= null && !entityList.isEmpty()){
					
					int index = pageable.getPageNumber()*pageable.getPageSize();
					for(Users entity : entityList){
						
						UserBean bean = convertUserEntityToBean(entity);
						bean.setIndex(++index);
					logger.info("ada"+ entity.getCreatedBy());
						beanList.add(bean);
					}
				}
				userJson = new UserJson();
				userJson.setiTotalDisplayRecords(users.getTotalElements());
				userJson.setiTotalRecords(userRepository.countByDesignationID(1L));
				userJson.setAaData(beanList);
			}
			return userJson;
		}
		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return userJson;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
