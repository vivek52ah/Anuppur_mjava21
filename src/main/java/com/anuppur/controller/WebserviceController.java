package com.anuppur.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkCategoryBean;
import com.anuppur.bean.WorkStatusBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.json.DataJson;
import com.anuppur.json.ImageJson;
import com.anuppur.json.LoginJson;
import com.anuppur.response.LoginResponse;
import com.anuppur.response.ResponseObject;
import com.anuppur.response.UserDetailResponse;
import com.anuppur.service.CommonService;
import com.anuppur.service.UserService;
import com.anuppur.template.DevelopmentMonitoringLegacyWork;
import com.anuppur.template.DevelopmentMonitoringNewWork;
import com.anuppur.template.ObjectFactoryLegacyWork;
import com.anuppur.template.ObjectFactoryNewWork;
import com.anuppur.template.DevelopmentMonitoringLegacyWork.Meta;
import com.anuppur.template.DevelopmentMonitoringLegacyWork.Page1;
import com.anuppur.template.DevelopmentMonitoringLegacyWork.Page2;
import com.anuppur.util.DMSUtil;

@RestController
@RequestMapping(value = { "/ws/*" })
public class WebserviceController {
	private User user;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	public static final Logger logger = LoggerFactory.getLogger(WebserviceController.class);

	@RequestMapping(value = "/getLegacyWorkTemplate/{fileName:.+}", method = RequestMethod.GET, produces = "application/xml")
	public ResponseEntity<InputStreamResource> getLegacyWorkTemplate(@PathVariable String fileName) throws IOException {

		ClassPathResource pdfFile = new ClassPathResource("downloads/" + fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xml"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);

		logger.info(fileName + " Sent..");
		return response;
	}

	@RequestMapping(value = "/getNewWorkTemplate/{fileName:.+}", method = RequestMethod.GET, produces = "application/xml")
	public ResponseEntity<InputStreamResource> getNewWorkTemplate(@PathVariable String fileName) throws IOException {

		ClassPathResource pdfFile = new ClassPathResource("downloads/" + fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/xml"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		headers.setContentLength(pdfFile.contentLength());
		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);

		logger.info(fileName + " Sent..");
		return response;
	}

	private DevelopmentMonitoringLegacyWork getLegacyWorkInstanceData(WorkBean workBean) {

		ObjectFactoryLegacyWork ob = new ObjectFactoryLegacyWork();

		DevelopmentMonitoringLegacyWork developmentMonitoring = ob.createDevelopmentMonitoringLegacyWork();

		Page1 page1 = ob.createDevelopmentMonitoringLegacyWorkPage1();

		page1.setDistrict(workBean.getDistrictName());
		page1.setBlock(workBean.getBlockName());

		page1.setWorkNo(workBean.getWorkNo());
		page1.setScheme(workBean.getScheme());
		page1.setYear(workBean.getFinancialYear());

		page1.setWorkPriority(workBean.getWorkPriority());
		page1.setImplementationAgency(workBean.getImplementationAgency());

		page1.setWorkName(workBean.getWorkName());
		page1.setWorkType(workBean.getWorkType());
		page1.setEstimatedAmount(workBean.getEstimatedAmt().toString());
		page1.setAmountReleased(workBean.getAmtReleasedTillDate().toString());

		page1.setStatus(workBean.getStatus());

		developmentMonitoring.setPage1(page1);

		Page2 page2 = ob.createDevelopmentMonitoringLegacyWorkPage2();

		developmentMonitoring.setPage2(page2);

		Meta meta = ob.createDevelopmentMonitoringLegacyWorkMeta();
		meta.setInstanceID("build_DMF_1524226597");

		developmentMonitoring.setMeta(meta);
		return developmentMonitoring;
	}

	private DevelopmentMonitoringNewWork getNewWorkInstanceData(WorkBean workBean) {

		ObjectFactoryNewWork ob = new ObjectFactoryNewWork();

		DevelopmentMonitoringNewWork developmentMonitoring = ob.createDevelopmentMonitoringNewWork();

		com.anuppur.template.DevelopmentMonitoringNewWork.Page1 page1 = ob.createDevelopmentMonitoringNewWorkPage1();

		page1.setDistrict(workBean.getDistrictName());
		page1.setBlock(workBean.getBlockName());

		page1.setWorkNo(workBean.getWorkNo());
		page1.setScheme(workBean.getScheme());
		page1.setYear(workBean.getFinancialYear());

		page1.setWorkPriority(workBean.getWorkPriority());
		page1.setImplementationAgency(workBean.getImplementationAgency());

		page1.setWorkName(workBean.getWorkName());
		page1.setWorkType(workBean.getWorkType());
		page1.setEstimatedAmount(workBean.getEstimatedAmt().toString());
		page1.setAmountReleased(workBean.getAmtReleasedTillDate().toString());

		page1.setStatus(workBean.getStatus());

		developmentMonitoring.setPage1(page1);

		com.anuppur.template.DevelopmentMonitoringNewWork.Page2 page2 = ob.createDevelopmentMonitoringNewWorkPage2();

		developmentMonitoring.setPage2(page2);

		com.anuppur.template.DevelopmentMonitoringNewWork.Meta meta = ob.createDevelopmentMonitoringNewWorkMeta();
		meta.setInstanceID("build_DMF_1523226597");

		developmentMonitoring.setMeta(meta);
		return developmentMonitoring;
	}

	
	@RequestMapping(value = "/saveWorkData", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody Map<String, String> saveWorkData(@RequestBody DataJson dataJson) throws IOException {

		Map<String, String> map = new HashMap<String, String>();

		try {

			logger.info(dataJson + "");
			// commonService.saveWorkData(dataJson);
		} catch (Exception e) {

			map.put("code", "500");
			map.put("message", "Internal Server Error");
			map.put("error", e.getMessage());
			return map;
		}

		map.put("code", "200");
		map.put("message", "SUCCESS");
		map.put("DetailMessage", "Bhavan Inspection Data saved");

		logger.info("Bhavan Inspection Data saved..");

		return map;

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LoginResponse login(@RequestBody LoginJson loginJson) throws IOException {

		LoginResponse loginResponse = new LoginResponse();
		UserDetailResponse userDetailResponse = new UserDetailResponse();
		try {
			userDetailResponse = commonService.getLogin(loginJson);
			loginResponse.setData(userDetailResponse);
			loginResponse.setCode("200");
			loginResponse.setStatus("true");
			loginResponse.setMessage("Login Successfull");
			logger.info("Login Successfull!!!!");
			return loginResponse;

		} catch (Exception e) {

			loginResponse.setCode("500");
			loginResponse.setStatus("false");
			loginResponse.setMessage(e.getMessage());
			loginResponse.setError(e.getMessage());
			logger.error("Error in Fetching Login Details !!" + e.getStackTrace());
			return loginResponse;
		}

	}

	@RequestMapping(value = "fetchSchemes", method = RequestMethod.GET)
	public List<SchemeBean> fetchSchemes(HttpServletRequest request) {
		return commonService.fetchSchemes();
	}

	@RequestMapping(value = "fetchWorkCategories", method = RequestMethod.GET)
	public List<WorkCategoryBean> fetchWorkCategories(HttpServletRequest request) {
		return commonService.fetchWorkCategories();
	}

	@RequestMapping(value = "fetchWorkStatus", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatus(HttpServletRequest request) {
		return commonService.fetchWorkStatus();
	}

}