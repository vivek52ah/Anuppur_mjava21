package com.anuppur.service.impl;


import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anuppur.bean.TemplateBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.TemplateEntity;
import com.anuppur.service.ConverterService;





@Service
public  class ConverterServiceImpl implements ConverterService {
	public static final Logger logger = LoggerFactory.getLogger(ConverterServiceImpl.class);
	@Value("${applicationDeploymentServerName}")
	private String basePath;


	@Override
	public TemplateBean convertTemplateEToB(TemplateEntity entity, Locale locale) {
		try {
			TemplateBean bean = new TemplateBean();
			bean.setId(entity.getId());
			bean.setKey(entity.getKey());
			bean.setStatus(entity.getStatus());
			bean.setTemplateIdEn(entity.getTemplateIdEn());
			bean.setTemplateIdHi(entity.getTemplateIdHi());
			bean.setTemplateId(locale.getLanguage().equals(Locale.of(DMSConstants.LOCALE_HI).getLanguage())
					? entity.getTemplateIdHi()
					: entity.getTemplateIdEn());

			bean.setMessageTextEn(entity.getMessageTextEn());
			bean.setMessageTextHi(entity.getMessageTextHi());
			bean.setMessageText(locale.getLanguage().equals(Locale.of(DMSConstants.LOCALE_HI).getLanguage())
					? entity.getMessageTextHi()
					: entity.getMessageTextEn());

			bean.setSubjectEn(entity.getSubjectEn());
			bean.setSubjectHi(entity.getSubjectHi());
			bean.setSubject(locale.getLanguage().equals(Locale.of(DMSConstants.LOCALE_HI).getLanguage())
					? entity.getSubjectHi()
					: entity.getSubjectEn());

			return bean;
		} catch (Exception e) {
			logger.error("Error: ConverterServiceImpl.convertTemplateEToB(): ", e);
			throw e;
		}
	}


	
	

	
}
