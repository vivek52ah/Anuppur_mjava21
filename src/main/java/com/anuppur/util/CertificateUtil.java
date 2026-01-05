package com.anuppur.util;


import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anuppur.exception.DMSBusinessException;







public class CertificateUtil {
	
public static final Logger logger = LoggerFactory.getLogger(CertificateUtil.class);
	
	public static X509Certificate getX509Certificate(String encodedString) {
	  if (encodedString == null) {
	       return null;
	  }

	  Decoder decoder = Base64.getDecoder();
	  byte[] decodedData = decoder.decode(encodedString);

	  try (InputStream inputStream = new ByteArrayInputStream(decodedData)) {
				   CertificateFactory cf = CertificateFactory.getInstance("X.509");
			
				   java.security.cert.Certificate certificate = cf.generateCertificate(inputStream);

				   if (certificate instanceof X509Certificate) {
				    return (X509Certificate) certificate;
				   }

	  } catch (Exception e) {
		  
		  	logger.error("error Getting Certificate",e);
	  }
	  return null;
	 }
	
	
	public static boolean verifySignature(X509Certificate toVerify, X509Certificate signingCert)  {
		 
		 if (!toVerify.getIssuerX500Principal().equals(signingCert.getSubjectX500Principal())) { return false; }
		 if (!toVerify.equals(signingCert)) { return false; }
		
				 try {
					 toVerify.verify(signingCert.getPublicKey());
				   	 toVerify.checkValidity();
					 return true;
				 } catch (GeneralSecurityException e) {
					 logger.error("error verifing signature",e);
				}catch (Exception e) {
					logger.error("error verifing signature",e);
					
				}
				 return false;		 
		}

	
	public static boolean verifyUserCert(X509Certificate userCert, PublicKey CAPublicKey) throws DMSBusinessException {
	    try {
	        if (userCert == null) {
	            throw new DMSBusinessException("userCert can't be null");
	        }
	        if (CAPublicKey == null) {
	            throw new DMSBusinessException("CAPublicKey can't be null");
	        }
	        userCert.verify(CAPublicKey);
	        return true;
	    } catch (Exception e) {
	        System.err.println(e);
	        logger.error("Can not verify Certificate ",e);
	        return false;
	    }
	}
	
	public static boolean isValidDsc(String userCertificate, String signedBase64) {
		logger.info("Enter inside isValidDsc Method ::::");
		boolean valid = true;
		try {
			if(StringUtils.isNotBlank(userCertificate) && StringUtils.isNotBlank(signedBase64)) {
				// Check Whether User Certificate is same as that of document signed
				byte[] decoder = null;
				decoder = Base64.getDecoder().decode(signedBase64);
				String xmlData = new String(decoder);
				logger.info("Decoded XMl Data ::::"+xmlData);
				
				String xmlCertificate = StringUtils.substringBetween("<"+"X509Certificate"+"X509Certificate","</" + "X509Certificate" + "X509Certificate"); 
				
				logger.info("Signed File XML Certificate Base 64 :::::: " + xmlCertificate);

				if(StringUtils.isNotBlank(xmlCertificate) && userCertificate.equals(xmlCertificate)) {

					//X509Certificate userCert = CertificateUtil.getX509Certificate(userCertificate);
					//X509Certificate xmlCert = CertificateUtil.getX509Certificate(xmlCertificate);
					
	   				  try {
	   					  X509Certificate requestCert = CertificateUtil.getX509Certificate(userCertificate);
	    				  X509Certificate userCert = CertificateUtil.getX509Certificate(xmlCertificate);
	    				  //userCert.
	    				  
	    				  if(CertificateUtil.verifySignature(requestCert, userCert)) {
	      					  return false;
	      					  //response.setErrorMessage("Invalid certificate Observed");
	      				  }
	   				  
	   				  }catch (Exception e) {
	   					  logger.error("CANNOT VALIDATE CERTIFICATE",e);
					
					  }
					//valid = CertificateUtil.verifySignature(userCert, xmlCert);
				}else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("En exception has occured inside isValidDsc Method:::::"+e);
			e.printStackTrace();
			return false;
		}
		logger.info("Exit isValidDsc Method ::::"+valid);
		return valid;
	}

}
