/**
 * 
 */
package com.anuppur.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anuppur.bean.TemplateBean;
import com.anuppur.entity.TemplateEntity;


@Service
public interface ConverterService {


	TemplateBean convertTemplateEToB(TemplateEntity entity, Locale locale);
	

}
