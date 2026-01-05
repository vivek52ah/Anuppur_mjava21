package com.anuppur.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anuppur.bean.SMSBean;
import com.anuppur.controller.SystemAdminController;
import com.anuppur.exception.DMSBusinessException;


//@Service
//public class SMSServiceImpl  {
//    
//	public static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);
//
//	@Value("${sms.userName}")
//	private String userName;
//	
//	@Value("${sms.password}")
//	private String password;
//	
//	@Value("${sms.senderId}")
//	private String senderId;
//	
//	@Value("${sms.url}")
//	private String url;
//
//	
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	
//	public void setSenderId(String senderId) {
//		this.senderId = senderId;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//
//}
