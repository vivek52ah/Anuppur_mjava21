package com.anuppur.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.ChangePasswordBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.District;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.DistrictRepository;
import com.anuppur.repository.LegislativeConsRepository;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.CommonService;
import com.anuppur.service.NotificationService;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;
import com.anuppur.util.SHAHashingUtil;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationService notificationService;

	/*
	 * @Override public Users findByUserName(String userName) { return
	 * userRepository.findByUsernameAndStatus(userName, DMSConstants.STATUS_ACTIVE);
	 * }
	 * 
	 * @Override public Users findByEmailId(String emailId) { return
	 * userRepository.findByEmailId(emailId); }
	 */

	@Override
	public Users findByEmailIdAndStatus(String emailId, String status) {
		return userRepository.findByEmailIdAndStatus(emailId, status);
	}

	@Override
	public UserBean fetchUserDetailsByUserName(String userName) {
		try {
			Users entity = userRepository.findByUsernameAndStatus(userName, DMSConstants.STATUS_ACTIVE);
			if (entity == null) {
				entity = userRepository.findByEmailIdAndStatus(userName, DMSConstants.STATUS_ACTIVE);
			}
			UserBean bean = convertUserEntityToBean(entity);
			DistrictBean districtBean = new DistrictBean();

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public UserBean convertUserEntityToBean(Users user) {

		UserBean bean = new UserBean();

		if (user != null) {
			bean.setId(user.getId());
			bean.setEmailId(user.getEmailId());
			bean.setFirstName(user.getFirstname());
			bean.setLastName(user.getLastname());
				bean.setDesignationId(user.getDesignationID());
			bean.setUsername(user.getUsername());

			bean.setMobileNo(user.getMobileNo());

			if (null != user.getStatus()) {
				bean.setStatus(user.getStatus());
				bean.setOldStatus(user.getStatus());
			}

			List<String> roleList = new ArrayList<>();
			Set<Role> roles = user.getRoles();
			String loggedInUserRole = null;
			int index = 0;
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {

					roleList.add(role.getRoleCode());
					if (index == 0) {
						loggedInUserRole = role.getRoleCode();
					}
					index++;
				}
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getAuthorities() != null) {
				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
				if (null != authorities) {
					for (GrantedAuthority authority : authorities) {
						String loggedInUserRole1 = authority.getAuthority();
						// bean.setRole(loggedInUserRole1);
						bean.setLoggedInUserRole(loggedInUserRole1);
					}
				}
			}

			bean.setRolee(loggedInUserRole);

			bean.setRolelist(roleList);
				bean.setDepartmentName(user.getDepartmentName());
				bean.setPassword(user.getPassword());

		}
		return bean;
	}

	/*
	 * @Override public void changePassword(ChangePasswordBean changePassword,
	 * String userName) throws DMSBusinessException {
	 * 
	 * try{ Users userEntity = userRepository.findByUsernameAndStatus(userName,
	 * DMSConstants.STATUS_ACTIVE);
	 * 
	 * if(userEntity!=null){ userEntity.setPassword(changePassword.getPassword());
	 * userRepository.save(userEntity); } }catch (Exception e) {
	 * logger.error("An exception occurred.", e); } }
	 */

	/*
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class) public String
	 * registerNewAccount(UserBean userBean, String verifyServiceUrl) throws
	 * DMSBusinessException{
	 * 
	 * try { Users user = userRepository.findByEmailIdAndStatusNot(
	 * userBean.getEmailId(), DMSConstants.STATUS_DELETED); if (user != null) {
	 * return "User with given Email Already exist!"; } else { user = new Users();
	 * convertUserBeanToEntity(user, userBean);
	 * 
	 * String verificationRandomString = SHAHashingUtil
	 * .encryptPassword(SHAHashingUtil.generatePassword()) .toString();
	 * 
	 * user.setVerificationRandomString(verificationRandomString);
	 * 
	 * userRepository.save(user);
	 * 
	 * String emailLink = verifyServiceUrl + "?id=" + user.getId() +
	 * "&verificationStr=" + verificationRandomString;
	 * 
	 * // sending email for verification
	 * 
	 * notificationService.sendRegistrationNotification( user.getEmailId(),
	 * user.getMobileNo(), emailLink);
	 * 
	 * return null; } } catch (Exception e) { logger.error("An exception occurred.",
	 * e); throw new DMSBusinessException(e.getMessage()); //return
	 * DMFConstants.ERROR_SAVING_DATA; } }
	 */

	private Users convertPAUserBeanToEntity(Users entity, UserBean bean) throws DMSBusinessException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (entity != null && bean != null) {
			if (!StringUtils.isEmpty(bean.getPassword())) {
				entity.setPassword(passwordEncoder.encode(bean.getPassword()));
			}
			entity.setUsername(bean.getEmailId());
			entity.setUsername(bean.getFirstName() + " " + bean.getLastName());
			entity.setEmailId(bean.getEmailId());
			entity.setMobileNo(bean.getMobileNo());
			entity.setDistrict(new District(bean.getDistrictId()));
			entity.setStatus(DMSConstants.STATUS_ACTIVE);// during
		}
		return entity;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class) public String
	 * registerExistingAccount(UserBean userBean, String verifyServiceUrl) throws
	 * DMSBusinessException {
	 * 
	 * try { Users user =
	 * userRepository.findByEmailIdAndStatusNot(userBean.getEmailId(),
	 * DMSConstants.STATUS_DELETED);
	 * 
	 * Users userAlreadyExists =
	 * userRepository.findByUsernameAndStatusNot(userBean.getEmailId(),
	 * DMSConstants.STATUS_DELETED);
	 * 
	 * if (user != null) { return "User with given Email Already exist!"; } else if
	 * (userAlreadyExists != null) { return "Entrepreneur with Account No. " +
	 * userBean.getEmailId() + " already registered!"; } else { user = new Users();
	 * convertUserBeanToEntity(user, userBean);
	 * 
	 * String verificationRandomString =
	 * SHAHashingUtil.encryptPassword(SHAHashingUtil.generatePassword())
	 * .toString();
	 * 
	 * user.setVerificationRandomString(verificationRandomString);
	 * 
	 * userRepository.save(user);
	 * 
	 * String emailLink = verifyServiceUrl + "?id=" + user.getId() +
	 * "&verificationStr=" + verificationRandomString;
	 * 
	 * // sending email for verification
	 * 
	 * notificationService.sendRegistrationNotification( user.getEmailId(),
	 * user.getMobileNo(), emailLink);
	 * 
	 * return null; } } catch (Exception e) { logger.error("An exception occurred.",
	 * e); throw new DMSBusinessException(e.getMessage()); // return
	 * DMFConstants.ERROR_SAVING_DATA; } }
	 */

	private Users convertUserBeanToEntity(Users entity, UserBean bean) throws DMSBusinessException {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (entity != null && bean != null) {
			// entity.setUsername(bean.getFactoryAddress().getEmail());
			if (!StringUtils.isEmpty(bean.getPassword())) {
				entity.setPassword(passwordEncoder.encode(bean.getPassword()));
			}
			// entity.setName(bean.getName());
			entity.setEmailId(bean.getEmailId());
			entity.setMobileNo(bean.getMobileNo());

			/*
			 * if (!StringUtils.isEmpty(bean.getStatus())) {
			 * entity.setStatus(bean.getStatus()); } else {
			 */
			entity.setStatus(DMSConstants.STATUS_PENDING_VERIFICATION);// during
																		// Entrepreneur signup
			// }
		}
		return entity;
	}

	@Override
	public String verifyEmail(Long id, String verificationStr) {

		try {

			Users user = userRepository.findByIdAndVerificationRandomString(id, verificationStr);
			if (user != null) {
				String status = user.getStatus();
				if (!status.equals(DMSConstants.STATUS_PENDING_VERIFICATION)) {// Already
																				// Verified
					return "Already Verified";
				}
				user.setStatus(DMSConstants.STATUS_PENDING_ACTIVATION);
				userRepository.save(user);

				// sending email after verification
				//notificationService.sendEmailVerificationNotification(user.getEmailId(), user.getMobileNo());

				return "Email Verified";
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public Users findByMobileNoAndStatus(String emailId, String status) {
		return userRepository.findByMobileNoAndStatus(emailId, status);
	}

	@Override
	public void changePassword(ChangePasswordBean changePassword, String userName) throws DMSBusinessException {
		// TODO Auto-generated method stub
		try{
			Users userEntity = userRepository.findByUsernameAndStatus(userName, DMSConstants.STATUS_ACTIVE);
			
			if(userEntity!=null){
				userEntity.setPassword(changePassword.getPassword());
				userEntity.setLastPasswordUpdatedOn((new Date()));	//added by aman for password expired
				userRepository.save(userEntity);
			}
		}catch (Exception e) {
			logger.error("An exception occurred.", e);
		}
	}

	@Override
	public String registerNewAccount(UserBean entrepreneurBean, String verifyServiceUrl) throws DMSBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerExistingAccount(UserBean userBean, String verifyServiceUrl) throws DMSBusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Users findByUserName(String userName) {
		Users user = userRepository.findByUsernameAndStatus(userName, DMSConstants.STATUS_ACTIVE);
		if (user == null) {
			user = userRepository.findByEmailIdAndStatus(userName, DMSConstants.STATUS_ACTIVE);
		}
		return user;
	}

	@Override
	public Users findByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}
	

}
