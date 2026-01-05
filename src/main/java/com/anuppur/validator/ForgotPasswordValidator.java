package com.anuppur.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.ForgotPasswordBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Users;
import com.anuppur.service.UserService;

@Component
public class ForgotPasswordValidator implements Validator{

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ForgotPasswordBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ForgotPasswordBean forgotpasswordBean = (ForgotPasswordBean) target;

		/*if(!StringUtils.isEmpty(forgotpasswordBean.getEmailId())) {
			EmailValidator  emailValidator = EmailValidator.getInstance();

			if(!emailValidator.isValid(forgotpasswordBean.getEmailId())) {
			errors.rejectValue("emailId", "Pattern.forgotPasswordBean.emailId");	
			}

		}*/
		

		//if(!StringUtils.isEmpty(forgotpasswordBean.getEmailId())) {
		
		if(!StringUtils.isEmpty(forgotpasswordBean.getMobileNo())) {

			 Users user = userService.findByMobileNoAndStatus(forgotpasswordBean.getMobileNo(), DMSConstants.STATUS_ACTIVE);
			//Users user = userService.findByEmailId(forgotpasswordBean.getEmailId());
			
			

			if (user == null) {
				errors.rejectValue("emailId", "Pattern.forgotPasswordBean.mobile");
			}
		}
	}
}
