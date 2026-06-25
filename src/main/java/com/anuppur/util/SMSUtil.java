package com.anuppur.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anuppur.bean.SMSBean;
import com.anuppur.exception.DMSBusinessException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
@SuppressWarnings("all")
public class SMSUtil {

	public static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

//	@Value("${sms.userName}")
	private String userName;

//	@Value("${sms.password}")
	private String password;

//	@Value("${sms.senderId}")
	private String senderId;

//	@Value("${sms.url}")
	private String url;

//	@Value("${cdac.sms.userName}")
	private String userNameCdac;

//	@Value("${cdac.sms.password}")
	private String passwordCdac;

//	@Value("${cdac.sms.senderId}")
	private String senderIdCdac;

//	@Value("${cdac.sms.url}")
	private String urlCdac;

//	@Value("${cdac.sms.secureKey}")
	private String secureKeyCdac;
	
//	@Value("${sms.api.password}")
//	private String smsApiPassword;


	// Method to send SMS
	public String sendSMS(SMSBean smsBean) {
		long starttime = System.currentTimeMillis();
		long endtime = 0;
		double timetaken = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String responseMessage = "";
		String smsServiceType = "unicodemsg";
		String templateId = (smsBean.getTemplateId() != null && smsBean.getTemplateId().equals("")) ? smsBean.getTemplateId()
				: "1004187509346541932";
		
		// Check if SMS configuration is available
		if (userName == null || password == null || senderId == null || url == null) {
			logger.error("SMS configuration is not properly configured. Please configure sms.userName, sms.password, sms.senderId, and sms.url in application properties.");
			return "SMS configuration not available";
		}
		
		try {
			logger.info("Sending SMS....");
			HttpPost post = new HttpPost(url);
			post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

			List<NameValuePair> formParams = new ArrayList<>();
			formParams.add(new BasicNameValuePair("username", userName));
			formParams.add(new BasicNameValuePair("password", password));
			formParams.add(new BasicNameValuePair("smsservicetype", smsServiceType));
			formParams.add(new BasicNameValuePair("content", smsBean.getSmsText()));
			formParams.add(new BasicNameValuePair("mobileno", smsBean.getMobileNumber()));
			formParams.add(new BasicNameValuePair("senderid", senderId));
			formParams.add(new BasicNameValuePair("templateid", templateId));
			post.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));

			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				ClassicHttpResponse response = httpClient.execute(post);
				responseMessage = response.getReasonPhrase();
				if (response.getEntity() != null) {
					try (BufferedReader input = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
						while (input.readLine() != null) {
							// Drain response so the connection can be released without logging provider data.
						}
					}
				}

			logger.info("SMS provider response received");
			if (response.getCode() == 200) {
				logger.info("SMS send successfully.....");
			} else {
				logger.info("SMS not send successfully.....");
			}
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			logger.info("SMS Delivery:transaction logs||{}||{}||{}||{}||{}||{}",
					smsBean.getMobileNumber(), sdf.format(date), timetaken, response.getCode(),
					url, responseMessage);
			}
		} catch (Exception ex) {
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			
			logger.error("error sending sms",ex);
		}
		return responseMessage;
	}
	
	public String sendSingleUnicodeSMSCDAC(SMSBean smsBean) throws
	  DMSBusinessException {

		long starttime = System.currentTimeMillis();
		long endtime = 0;
		double timetaken =0.0;
		
		// Check if SMS configuration is available
		if (userNameCdac == null || passwordCdac == null || senderIdCdac == null || urlCdac == null || secureKeyCdac == null) {
			logger.error("SMS configuration is not properly configured. Please configure cdac.sms.userName, cdac.sms.password, cdac.sms.senderId, cdac.sms.url, and cdac.sms.secureKey in application properties.");
			return "SMS configuration not available";
		}
		
		String message=  smsBean.getSmsText();
	       String finalmessage = "";
	         for(int i = 0 ; i< message.length();i++){
	             char ch = message.charAt(i);
	             int j = (int) ch;
	             String sss = "&#"+j+";";
	             finalmessage = finalmessage+sss;
	         }
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		String serviceType = "unicodemsg"; // For single message
		String responseString = "";
		String passwordForSMS = passwordCdac;
		String encryptedPassword;
		
		//String templateid=(smsBean.getTemplateId()!=null && smsBean.getTemplateId()!="")?smsBean.getTemplateId():"100710152797109492";
		String templateid=smsBean.getTemplateId();
		if("0000000000".equalsIgnoreCase(smsBean.getMobileNumber())) {
			responseString ="406 :Invalid mobile no";
		}else {
		
		try {
			
	CloseableHttpClient httpClient = HttpClients.createDefault();

			             HttpPost post=new HttpPost(urlCdac);
			             encryptedPassword  = sha256Hex(passwordForSMS);
			             String genratedhashKey = hashGenerator(userNameCdac, senderIdCdac, finalmessage, secureKeyCdac);
			             
			             List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(1);
			             
			             nameValuePairs.add(new BasicNameValuePair("mobileno", smsBean.getMobileNumber()));
			             nameValuePairs.add(new BasicNameValuePair("senderid", senderIdCdac));
			             nameValuePairs.add(new BasicNameValuePair("content", finalmessage));
			             nameValuePairs.add(new BasicNameValuePair("smsservicetype", serviceType));
			             nameValuePairs.add(new BasicNameValuePair("username", userNameCdac));
			             nameValuePairs.add(new BasicNameValuePair("password", encryptedPassword));
			             nameValuePairs.add(new BasicNameValuePair("key", genratedhashKey));
                         nameValuePairs.add(new BasicNameValuePair("templateid", templateid));
			             post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			             
			             ClassicHttpResponse response = httpClient.execute(post);
			             BufferedReader bf=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			             String line="";
			             while((line=bf.readLine())!=null){
			                 responseString = responseString+line;
			             }
			             //System.out.println(responseString);
			                  endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;
						    logger.info("SMS Delivery:transaction logs||{}||{}||{}||{}||{}",
						            smsBean.getMobileNumber(), sdf.format(date), timetaken, responseString, urlCdac);
						    if(!responseString.contains("402,MsgID")) {
						    	throw new DMSBusinessException("SMS_DELIVERY_FAILURE_IOException");
						    }
			         } catch (NoSuchAlgorithmException e) {
							endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_NoSuchAlgorithmException");

			         } catch (UnsupportedEncodingException e) {
			                endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_UnsupportedEncodingException");
			         } catch (IOException e) {
			                endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_IOException");
			         }
		finally {
			
		 }
	}
		return responseString;
	}
    
	
	public String createSHAHash(String input ) 
	          throws NoSuchAlgorithmException {

	      String hashtext = null;
	      MessageDigest md = MessageDigest.getInstance("SHA-256");
	      byte[] messageDigest =
	              md.digest(input.getBytes(StandardCharsets.UTF_8));

	      hashtext = convertToHex(messageDigest);
	      return hashtext;
	   }

	   private String convertToHex(final byte[] messageDigest) {
	      BigInteger bigint = new BigInteger(1, messageDigest);
	      String hexText = bigint.toString(16);
	      while (hexText.length() < 32) {
	         hexText = "0".concat(hexText);
	      }
	      return hexText;
	   }


	public String calculateSha256Hash(String input,String time) {
		
		
		try {
			 
            MessageDigest md = MessageDigest.getInstance("SHA-256");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
            	hashtext = "0".concat(hashtext);
            }
            return hashtext.toUpperCase();
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
	
	private static String sha256Hex(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-256");
		byte[] digest = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		digest = md.digest();
		return convertedToHex(digest);
	}

	private static String convertedToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			int halfOfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfBytes = 0;

			do {
				if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
					buf.append((char) ('0' + halfOfByte));
				}

				else {
					buf.append((char) ('a' + (halfOfByte - 10)));
				}

				halfOfByte = data[i] & 0x0F;

			} while (twoHalfBytes++ < 1);
		}
		return buf.toString();
	}



	protected String hashGenerator(String userName, String senderId, String content, String secureKey) {
		// TODO Auto-generated method stub
		StringBuffer finalString = new StringBuffer();
		finalString.append(userName.trim()).append(senderId.trim()).append(content.trim()).append(secureKey.trim());
		// logger.info("Parameters for SHA-512 : "+finalString);
		String hashGen = finalString.toString();
		StringBuffer sb = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(hashGen.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
			logger.error("NO ALGORITHM FOUND",e);
		}
		if (sb != null) {
			return sb.toString();
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	

	
	

}
