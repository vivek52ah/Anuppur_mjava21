package com.anuppur.service;


import java.util.Locale;
import java.util.Map;

import com.anuppur.bean.UserBean;
import com.anuppur.exception.DMSBusinessException;



public interface NotificationService {

	void sendUserRegistrationMessage(UserBean userBean);

	void sendNotification(String emailId, String otp, String key, Map<String, String> tokenMap, Locale locale)
			throws Exception;
	
	
	void sendNotificationOnMail(String emailId , UserBean userbean);
	
	
	public void sendOtpNotificationEmail(String emailId, String otp) throws DMSBusinessException;

	void sendPasswordResetOtp(String emailId, String mobileNo, String otp);

	void sendNotificationOnMailResetPassword(String emailId, UserBean userbean);

	

}
