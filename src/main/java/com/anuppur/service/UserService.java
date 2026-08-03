package com.anuppur.service;

import com.anuppur.bean.ChangePasswordBean;
import com.anuppur.bean.UserBean;
import com.anuppur.entity.Users;
import com.anuppur.exception.DMSBusinessException;

public interface UserService {

	Users findByUserName(String userName);
	
	Users findByEmailId(String emaildId);
	
	void changePassword(ChangePasswordBean changePassword, String userName)
			throws DMSBusinessException;

	String registerNewAccount(UserBean entrepreneurBean, String verifyServiceUrl) throws DMSBusinessException;

	String verifyEmail(Long id, String verificationStr);


	Users findByEmailIdAndStatus(String emailId, String status);

	String registerExistingAccount(UserBean userBean, String verifyServiceUrl) throws DMSBusinessException;
	
	Users findByMobileNoAndStatus(String emailId, String statusActive);
	
	
	UserBean fetchUserDetailsByUserName(String userName);
	
	public UserBean convertUserEntityToBean(Users user);
}
