package com.anuppur.service.impl;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anuppur.bean.EmailBean;
import com.anuppur.bean.SMSBean;
import com.anuppur.bean.TemplateBean;
import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Notification;
import com.anuppur.entity.TemplateEntity;
import com.anuppur.entity.Users;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.NotificationRepository;
import com.anuppur.repository.TemplateRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.ConverterService;
import com.anuppur.service.NotificationService;
import com.anuppur.util.AccountUtil;
import com.anuppur.util.DMSUtil;
import com.anuppur.util.EmailServiceUtil;
import com.anuppur.util.SMSUtil;

@Service
public class NotificationServiceImpl implements NotificationService {

	public static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private static final String USER_SMS_REGISTRATION = "user.sms.registration";

	private static final String USER_SMS_REGISTRATION_VER = "user.sms.registration.ver";

	private static final String USER_EMAILSUBJECT_REGISTRATION = "user.emailsubject.registration";

	private static final String USER_EMAILBODY_REGISTRATION = "user.emailbody.registration";

	private static final String USER_SMS_VERIFICATION = "user.sms.verification";

	private static final String USER_EMAILSUBJECT_VERIFICATION = "user.emailsubject.verification";

	private static final String USER_EMAILBODY_VERIFICATION = "user.emailbody.verification";

	private static final String USER_SMS_ACTIVATION = "user.sms.activation";

	private static final String USER_EMAILSUBJECT_ACTIVATION = "user.emailsubject.activation";

	private static final String USER_EMAILBODY_ACTIVATION = "user.emailbody.activation";

	private static final String USER_EMAILBODY_PWD_RESET = "user.emailbody.forgotpassword";

	private static final String USER_EMAILSUBJECT_PWD_RESET = "user.emailsubject.forgotpassword";

	private static final String USER_EMAILBODY_EMAIL_VERFICATION_OTP = "user.emailbody.emailVerificationOtp";

	private static final String USER_EMAILSUBJECT_EMAIL_VERFICATION_OTP = "user.emailsubject.emailVerificationOtp";

	private static final String USER_SMS_MOBILE_VERFICATION_OTP = "user.sms.mobileVerificationOtp";

	private static final String USER_SMS_REGISTRATION_MESSAGE = "user.sms.registration.message";
	 private static final SecureRandom rnd = new SecureRandom();

	public Map<String, TemplateBean> template;

	private int notificationEMAIL = 0;
	// private SMSUtil smsUtil;

	@Autowired
	SMSUtil smsUtil;

	@Autowired
	EmailServiceUtil emailServiceUtil;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	ConverterService converterService;

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	private EmailServiceImpl emailService;

	public EmailServiceImpl getEmailService() {
		return emailService;
	}

	@Autowired
	UserRepository userRepository;

	@Override
	public void sendUserRegistrationMessage(UserBean userBean) {

		Runnable runnable = new Runnable() {

			public void run() {
				try {
					if (null != userBean) {
						String name = null;
						if (null != userBean.getFirstName()) {
							name = userBean.getFirstName();
						}
						String[] params = { name };

						SMSBean smsBean = new SMSBean();

						smsBean.setMobileNumber(userBean.getMobileNo());

						PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

						String password = passwordEncoder.encode(userBean.getPassword());

						String randomNum = getRandomNumberString();
						if (userBean.getFirstName() != null) {
							smsBean.setTemplateId("1007811636036843388");
							// smsBean.setTemplateId("1007866859185576582");
							// smsBean.setTemplateId("1007006215941549961");
							/*
							 * smsBean.setSmsText(DMSUtil.getMessage(DMSConstants.NOTIFICATION_FILE,
							 * USER_SMS_REGISTRATION_MESSAGE, params));
							 */
								smsBean.setSmsText("Your account is created! Use username: "+userBean.getEmailId()+" and password: "+ userBean.getPassword()+ " to log in. Please change your password after logging in.");
							/*smsBean.setSmsText("Dear " + userBean.getFirstName() + " " + userBean.getLastName()
									+ ", Your Login Credentials to login in Anuppur Civil Works Management are User Name "
									+ userBean.getEmailId() + " and Password " + userBean.getPassword()
									+ ".From MPNHM");*/
							// smsBean.setSmsText("HRMIS Login Details is: User Name- YOUSRA - 12345");
						}

						else {

							Users users = userRepository.findByMobileNo(smsBean.getMobileNumber());

							if (users != null) {
								smsBean.setTemplateId("1007325299582187076");
								// smsBean.setTemplateId("1007866859185576581");
								// smsBean.setTemplateId("1007006215941549961");
								// smsBean.setSmsText("DHS new password is : " + userBean.getPassword());
								smsBean.setSmsText("Your account password has changed! Use username:" + users.getEmailId()+" and password: "+ userBean.getPassword()+" to log in. Please change your password after logging in.");
								/*
								 * smsBean.setSmsText("Dear " + users.getFirstname() + " " + users.getLastname()
								 * + " Your New Password to login in Anuppur Civil Works Management System is  "
								 * + userBean.getPassword() + ".From MPNHM");
								 */
								// smsBean.setSmsText("HRMIS Login Details is: User Name- YOUSRA - 12345");
							}

						}

						// smsBean.setSmsText("Dear Yousra shafiq, Your One Time password is 123456 for
						// login. - SARTHAK Lite");

						

						smsUtil.sendSingleUnicodeSMSCDAC(smsBean);
						// smsUtil.fetchSamagraData();

					}

					// smsUtil.sendSMS(smsBean);

				} catch (DMSBusinessException e) { // TODO Auto-generated catch block
	
					logger.error("ERROR SENDING MESSAGE",e);

				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	public static String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		//Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	@Override
	public void sendNotification(String emailId, String otp, String key, Map<String, String> tokenMap, Locale locale)
			throws Exception {

		new Thread(() -> {

			try {

				long starttime = System.currentTimeMillis();
				long endtime = 0;
				StringBuilder qString = new StringBuilder("");
				double timetaken = 0.0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String smsResponse = "";

				Notification notification = new Notification();
				notification.setMessageType(2);
				String msgText = "";
				try {
					TemplateBean templateBean = template.get(key);
					if (templateBean == null) {
						TemplateEntity templateEntity = templateRepository.findByKey(key);// ex.
																							// key="CITIZEN_NOTIFICATION"
						if (templateEntity != null)
							templateBean = converterService.convertTemplateEToB(templateEntity, locale);

						msgText = locale.getLanguage().equals(new Locale(DMSConstants.LOCALE_HI).getLanguage())
								? templateBean.getMessageTextHi()
								: templateBean.getMessageTextEn();

					}

					if (templateBean != null) {
						notification.setEmail(emailId);
						notification.setEmailSubject(templateBean.getSubject());
						notification.setMessage(AccountUtil.replaceTokens(msgText, tokenMap));
						notification.setScheduleDate(new Date());

						EmailBean emailBean = new EmailBean();
						emailBean.setRecipients(emailId);
						emailBean.setBody(AccountUtil.replaceTokens(msgText, tokenMap));
						emailBean.setSubject(templateBean.getSubject());
						emailBean.setHTML(true);

						if (notificationEMAIL == 1)
							emailServiceUtil.sendEmailmessage(emailBean);

						if (otp != null && !otp.equals(""))
							notification.setOtp(otp);

						notification.setEmailStatus("Success");
						notification.setSendDate(new Date());
						notification.setUpdateDate(new Date());
						notificationRepository.save(notification);
					} else {
						throw new Exception("No Template Found!");
					}
				} catch (Exception e) {
					notification.setEmailStatus("Fail");
					notification.setUpdateDate(new Date());
					notificationRepository.save(notification);
					// throw e;
				}

				endtime = System.currentTimeMillis();
				timetaken = (double) (endtime - starttime) / 1000;
				Date date1 = new Date();

			} catch (Exception e) {

			}

		}).start();

	}

	@Override
	public void sendOtpNotificationEmail(String emailId, String otp) throws DMSBusinessException {

		String[] paramsEmail = { otp };

		EmailBean emailBean = new EmailBean();
		emailBean.setRecipients(emailId);
		emailBean.setBody(
				DMSUtil.getMessage(DMSConstants.NOTIFICATION_FILE, USER_EMAILBODY_EMAIL_VERFICATION_OTP, paramsEmail));
		emailBean.setSubject(
				DMSUtil.getMessage(DMSConstants.NOTIFICATION_FILE, USER_EMAILSUBJECT_EMAIL_VERFICATION_OTP, null));
		emailBean.setHTML(true);

		getEmailService().sendEmailmessage(emailBean);
	}
	
	@Override
	public void sendNotificationOnMail(String emailId, UserBean userbean) {
		//String[] paramsEmail = { otp };

			Users user =  userRepository.findByEmailId(emailId);
		EmailBean emailBean = new EmailBean();
		emailBean.setRecipients(emailId);
		
		

		emailBean.setBody("Your account has been created successfully. Below are your login details:\r\n"
				+ "Username: "+emailId+"\r\n"
				+ "Password: " + userbean.getPassword()+"\r\n"
				+ "Please log in to your account as soon as possible. For your security, we recommend changing your password once you log in");
		emailBean.setSubject("Notification "
			);
		emailBean.setHTML(true);
		 logger.info( emailBean.getBody());
		// System.out.println(emailBean.getBody()+231231);
   try {
	  
	   getEmailService().sendEmailmessage(emailBean);
	   
} catch (Exception e) {
	// TODO: handle exception
	
	logger.error("ERROR SENDING MAIL",e);
}
		
	}
	
	
	@Override
	public void sendNotificationOnMailResetPassword(String emailId, UserBean userbean) {
		//String[] paramsEmail = { otp };

			Users user =  userRepository.findByEmailId(emailId);
		EmailBean emailBean = new EmailBean();
		emailBean.setRecipients(emailId);
		
		

		emailBean.setBody( " Your account password has changed successfully. Below are your login details:\r\n"
				+ "Username: "+emailId+"\r\n"
				+ "Password:"+userbean.getPassword()+"\r\n"
				+ "Please log in to your account as soon as possible. For your security, we recommend changing your password once you log in.");
		emailBean.setSubject("Notification "
			);
		emailBean.setHTML(true);
		 logger.info( emailBean.getBody());
		// System.out.println(emailBean.getBody()+231231);
   try {
	  
	   getEmailService().sendEmailmessage(emailBean);
	   
} catch (Exception e) {
	// TODO: handle exception
	//e.printStackTrace();
	logger.error("error Sending MAil",e);
}
		
	}
	
	
	
	
	
	
	
	
	
	
}
