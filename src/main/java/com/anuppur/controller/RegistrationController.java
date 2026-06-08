package com.anuppur.controller;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anuppur.bean.EkycAuthenticationBean;
import com.anuppur.bean.UserBean;
import com.anuppur.entity.District;
import com.anuppur.entity.LegislativeConstituency;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.DistrictRepository;
import com.anuppur.repository.LegislativeConsRepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.CommonService;
import com.anuppur.service.NotificationService;
import com.anuppur.service.UserService;

@Controller
public class RegistrationController {

	public static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private LegislativeConsRepository legislativeConsRepository;

	@Value("${applicationDeploymentServerName}")
	private String applicationDeploymentServerName;

	@RequestMapping(value = "registerNewAccount", method = RequestMethod.GET)
	public ModelAndView viewNewAccountRegistrationForm(HttpServletRequest request, Model model,
			HttpServletResponse response) {

		logger.info("Displaying New Account Registration Form");

		// Cache Control
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);

		ModelAndView modelAndView = new ModelAndView("registerNewAccount");
		return modelAndView;
	}

	@RequestMapping(value = "registerExistingAccount", method = RequestMethod.GET)
	public ModelAndView viewExistingAccountRegistrationForm(HttpServletRequest request, Model model,
			HttpServletResponse response) {

		logger.info("Displaying Existing Account Registration Form");

		// Cache Control
		String headerValue = CacheControl.maxAge(10, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);

		ModelAndView modelAndView = new ModelAndView("registerExistingAccount");
		return modelAndView;
	}

	@RequestMapping(value = "verifyEmail", method = RequestMethod.GET)
	public String verifyEmail(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "verificationStr", required = true) String verificationStr) {

		String status = userService.verifyEmail(id, verificationStr);

		if (status != null) {
			if (status.equals("Already Verified")) {
				return "redirect:/login?alreadyVerified";
			}
			return "redirect:/login?verificationSuccess";
		} else
			return "redirect:/login?verificationError";
	}

	@RequestMapping(value = "/checkEmailIdRegistered", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject checkEmailIdRegistered(@RequestParam(value = "emailId", required = true) String emailId) {
		ResponseObject responseObject = new ResponseObject();
		long userId = commonService.isUserByEmailIdExists(emailId);
		responseObject.setId(userId);
		return responseObject;
	}

	@RequestMapping(value = "/generateotpforemailid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject generateOtpForEmailId(@RequestBody String emailId) throws DMSBusinessException {
		ResponseObject response = new ResponseObject();
		generateAndSendOTPEmail(emailId);
		return response;
	}

	private void generateAndSendOTPEmail(String emailId) throws DMSBusinessException {

		SecureRandom secureRandom = new SecureRandom();

	    // Generate a secure 6-digit OTP
	    StringBuilder otp = new StringBuilder();
	    for (int i = 0; i < 6; i++) {
	        int digit = secureRandom.nextInt(9) + 1; // Generate digits from 1 to 9
	        otp.append(digit);
	    }
	}

	@RequestMapping(value = "/authenticateotpforemailid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject authenticateOtpForEmailId(EkycAuthenticationBean ekycAuthenticationBean) {
		ResponseObject responseObject = new ResponseObject();

		Integer otpFromDb = commonService.getOtpByEmailIdMobileNo(ekycAuthenticationBean.getAadhaarNumber());
		if (null != otpFromDb && otpFromDb == Integer.parseInt(ekycAuthenticationBean.getOtp())) {
			responseObject.setId(1L);
		} else {
			responseObject.setId(0L);
		}

		return responseObject;
	}

	@RequestMapping(value = "/generateotpformobileno", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject generateOtpForMobileNo(@RequestBody UserBean bean) throws DMSBusinessException {
		ResponseObject response = new ResponseObject();
		generateAndSendOTPMobile(bean.getMobileNo(), bean.getFirstName(), bean.getLastName(), bean.getDistrictId(),
				bean.getLcId());
		return response;
	}

	private void generateAndSendOTPMobile(String mobileNo, String firstName, String lastName, Long districtId,
			Long lcId) throws DMSBusinessException {

		// String randomPassword = RandomStringUtils.randomAlphanumeric(6);
		SecureRandom secureRandom = new SecureRandom();
	    int otp = 100000 + secureRandom.nextInt(900000);

		commonService.saveOrUpdateOtp(mobileNo, otp);
		String fullName = firstName + " " + lastName;
		District district = districtRepository.findById(districtId).orElse(null);
		LegislativeConstituency lc = legislativeConsRepository.findById(lcId).orElse(null);

		/*
		 * notificationService.sendOtpNotificationMobileNo(mobileNo, otp, fullName,
		 * district.getDistrictName(), lc.getConstituencyName());
		 */
	}

	@RequestMapping(value = "/authenticateotpformobileno", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject authenticateOtpForMobileNo(EkycAuthenticationBean ekycAuthenticationBean) {
		ResponseObject responseObject = new ResponseObject();

		Integer otpFromDb = commonService.getOtpByEmailIdMobileNo(ekycAuthenticationBean.getAadhaarNumber());
		if (null != otpFromDb && otpFromDb == Integer.parseInt(ekycAuthenticationBean.getOtp())) {
			responseObject.setId(1L);
		} else {
			responseObject.setId(0L);
		}

		return responseObject;
	}

	public String getApplicationDeploymentServerName() {
		return applicationDeploymentServerName;
	}

	public void setApplicationDeploymentServerName(String applicationDeploymentServerName) {
		this.applicationDeploymentServerName = applicationDeploymentServerName;
	}
}