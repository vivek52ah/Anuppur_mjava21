package com.anuppur.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anuppur.bean.ForgotPasswordBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Users;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.UserService;
import com.anuppur.validator.ForgotPasswordValidator;

@Controller
public class ForgotPasswordController {

	public static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	ForgotPasswordValidator forgotPasswordValidator;

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String viewForgotPassword(HttpServletRequest request, Model model) {

		logger.info("Displaying Forgot password page");
		model.addAttribute("forgotPasswordBean", new ForgotPasswordBean());
		return "forgotpassword";

	}

	/*
	 * @RequestMapping(value = "verifyotp", method = RequestMethod.GET) public
	 * String viewVerifyOtp(HttpServletRequest request, Model model) {
	 * 
	 * logger.info("Displaying Verify Otp Page");
	 * model.addAttribute("forgotPasswordBean", new ForgotPasswordBean()); return
	 * "verifyotp";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "resetpasswordbyotp1", method = RequestMethod.GET)
	 * public String viewResetPasswordByOtp1(HttpServletRequest request, Model
	 * model) {
	 * 
	 * logger.info("Displaying Forgot password page");
	 * model.addAttribute("forgotPasswordBean", new ForgotPasswordBean()); return
	 * "resetpasswordbyotp1";
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "resetpasswordbyotp", method = RequestMethod.GET)
	 * public String viewResetPasswordByOtp(HttpServletRequest request, Model model)
	 * {
	 * 
	 * logger.info("Displaying Forgot password page");
	 * model.addAttribute("forgotPasswordBean", new ForgotPasswordBean()); return
	 * "resetpasswordbyotp";
	 * 
	 * }
	 * 
	 */	/*
	 * @RequestMapping(value = "resetpassword", method = RequestMethod.GET) public
	 * String resetPassword(@Valid ForgotPasswordBean forgotPasswordBean,
	 * BindingResult bindingResult, Model model, HttpServletRequest request) throws
	 * DMSBusinessException {
	 * 
	 * logger.info("Resetting password");
	 * 
	 * //String captchaText = request.getParameter("captchaText"); String emailId =
	 * request.getParameter("emailId"); HttpSession session = request.getSession();
	 * 
	 * //String captcha = (String) session.getAttribute(DMSConstants.CAPTCHA_RESET);
	 * 
	 * 
	 * if (captcha == null || (captcha != null && !captcha.equals(captchaText))) {
	 * 
	 * logger.error("Wrong Captcha Text!"); model.addAttribute("error",
	 * "Wrong Captcha Text!"); return "forgotpassword"; }
	 * 
	 * System.out.println("forgotPasswordBean"+ forgotPasswordBean);
	 * 
	 * 
	 * forgotPasswordValidator.validate(forgotPasswordBean, bindingResult);
	 * 
	 * if (bindingResult.hasErrors()) { model.addAttribute("forgotPasswordBean",
	 * forgotPasswordBean); logger.info("Resetting password binding results");
	 * return "forgotpassword";
	 * 
	 * }
	 * 
	 * 
	 * userService.resetPassword(forgotPasswordBean.getEmailId());
	 * System.out.println("forgotPasswordBean.getEmailId()"+
	 * forgotPasswordBean.getEmailId());
	 * 
	 * //userService.resetPassword(emailId);
	 * 
	 * //return "redirect:/login?resetPassword"; return "redirect:/verifyotp"; }
	 */
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetPassword(@Valid @ModelAttribute("forgotPasswordBean")  ForgotPasswordBean forgotPasswordBean, BindingResult bindingResult, Model model,
			HttpServletRequest request) throws DMSBusinessException {

		logger.info("Resetting password");
		
		

		   // Custom validation
//	    forgotPasswordValidator.validate(forgotPasswordBean, bindingResult);

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("forgotPasswordBean", forgotPasswordBean);
	        return "forgotpassword";
	    }
		
		String captchaText = request.getParameter("captchaText");
		
		HttpSession session = request.getSession();

		//String captcha = (String) session.getAttribute(DMSConstants.CAPTCHA_RESET);
		String captcha = "123456";
		
		Users userEntity = userRepository.findByMobileNoAndStatusNot(forgotPasswordBean.getMobileNo(), DMSConstants.STATUS_DELETED);
		if(userEntity == null)
		{
			
			model.addAttribute("error", "Please Enter Registered Mobile No!");
			model.addAttribute("forgotPasswordBean", forgotPasswordBean);
			return "forgotpassword";
			
		}

		if (captcha == null || (captcha != null && !captcha.equals(captchaText))) {

			logger.error("Wrong Captcha Text!");
			model.addAttribute("error", "Wrong Captcha Text!");
			return "forgotpassword";
		}
		forgotPasswordValidator.validate(forgotPasswordBean, bindingResult);

		/*
		 * if (bindingResult.hasErrors()) { model.addAttribute("forgotPasswordBean",
		 * forgotPasswordBean);
		 * System.out.println(" Manoj tttttttttttt bindingResult.hasErrors()"); return
		 * "forgotpassword"; }
		 */
		//userService.resetPassword(forgotPasswordBean.getEmailId());
		userService.resetPassword(forgotPasswordBean.getMobileNo());
		
		//System.out.println(" Manoj Test forgotPasswordBean.getMobileNo()" + forgotPasswordBean.getMobileNo());

		return "redirect:/login?resetPassword";
	}

}
