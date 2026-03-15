package com.anuppur.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anuppur.bean.EmailBean;
import com.anuppur.bean.SMSBean;
import com.anuppur.entity.Notification;
import com.anuppur.exception.DMSBusinessException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
		StringBuilder queryString = new StringBuilder("");
		long starttime = System.currentTimeMillis();
		long endtime = 0;
		double timetaken = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String responseMessage = "";
		String responseTxt = "";
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
			queryString.append("username=" + URLEncoder.encode(userName, "UTF-8") + "&");
			queryString.append("password=" + URLEncoder.encode(password, "UTF-8") + "&");
			queryString.append("smsservicetype=" + URLEncoder.encode(smsServiceType, "UTF-8") + "&");
			queryString.append("content=" + URLEncoder.encode(smsBean.getSmsText(), "UTF-8") + "&");
			queryString.append("mobileno=" + URLEncoder.encode(smsBean.getMobileNumber(), "UTF-8") + "&");
			queryString.append("senderid=" + URLEncoder.encode(senderId, "UTF-8") + "&");
			queryString.append("templateid=" + URLEncoder.encode(templateId, "UTF-8"));

			logger.info("queryString====");
			URL smsUrl = new URL(url + "?" + queryString);
			logger.info("SMS URL====");
			HttpsURLConnection connection = (HttpsURLConnection) smsUrl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			HttpsURLConnection.setFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

			// get ready to read the response from the cgi script
			DataInputStream input = new DataInputStream(connection.getInputStream());
			// read in each character until end-of-stream is detected
			for (int c = input.read(); c != -1; c = input.read()) {
				responseTxt += (char) c;
			}
			logger.info("responseTxt====" + responseTxt);
			input.close();
			logger.info(smsBean.getSmsText());
			if (connection.getResponseCode() == 200) {
				logger.info("SMS send successfully.....");
			} else {
				logger.info("SMS not send successfully.....");
			}
			responseMessage = connection.getResponseMessage();
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			String logText = "SMS Delivery:transaction logs||" + smsBean.getMobileNumber() + "||" + sdf.format(date)
					+ "||" + timetaken + "||" + connection.getResponseCode() + "||" + url.toString() + "?" + queryString
					+ "||" + connection.getResponseMessage() + ":" + responseTxt;
			logger.info("logText");
		} catch (Exception ex) {
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			
			logger.error("error sending sms",ex);
			// logger.error("SMS Delivery:transaction
			// logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken +
			// "||"+url.toString()+"?"+queryString+"||"+ex );
		}
		return responseMessage;
	}
	
	public void fetchSamagraData() throws
	  DMSBusinessException {
		
			/*
			 * String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
			 * 
			 * String key = "26092022";
			 * 
			 * String result;
			 * 
			 * result = CalculateMD5Hash(timeStamp+key,timeStamp);
			 * System.out.println("HashValue....."+result+"timesStamp...."+timeStamp);
			 */
			  
		String value= getDecryptData("yWpiR7fmO8F4sIugJMOoWmbQ57zY3HJ8XlA+h/J/bcCWErYUKnkd9CUvZdmbQVKUIhbXtFRfHuHpnuxkDs0qMWQc9ZbGCHYJzwTJinTmyIzDc6IIXd13YjZ/erx9AjxTkAe0DbqpWBBQyt3Itg/BJalDi1J0biyavT5jKOWq8VArXEBgWY7Eq2ToYLccCZQIEabe1ew/pFOMvlPoqoFXh4wK9lzQNEgwKquSB5gGRH7Gh1D85sVdg536aTYaZlz+YSXwJiz141y20YymhfrJbulspP5R186oK2+b17QTxLbQ0MdHZUPFyPm94OhPI0vMQTqqTBb2R32w8Dcda5F6OqOSXIDgudzo8C4EtBwnqFUmsfQqR81r3ftujvWd3RgxPkSUgPFw+mFsfKGLfs+Z3TPyDAaBTBcxHwrYqhjVihf/NB5N2ADU5IDyo4vsu/fa4uEX0yURwb4qLC6jH80ur2s5kHpb0ineb2s/+3yvM34b32hpXZHluqnt01SnCbtzZnFIo5VJA4lKQzhMzDjNE42Cqz4/lojmshkb0U2P83cduwwP6qSVv1a3504rNlY1jz7173rDcdNMpGKEJKZXPQeTG7lABK8f8PUu4F9eCyTziM8HMumiN/oknuOJ+eX61GG0njtQrPjVUw0WGDoECVLAKqRjCRftY/TSjvd5lX+xfrw3pG9b5PkG8tIcS4kD1/q2u609BVb9wuTweMFMG9SNcZGYxQODte56n2rpEG14jfiBwod/y7oamDeeBiIvmsPmtNVscnB25OPA7oA3pGGGQ79/TMSRnAgtH4sMtmGVVbUjBAV9R+ik5Jb1OydBKVWjMS0rVm8ZdUNXCUM4vFxYafUqBi4NPeWJedjlCFH9UcsXpI/nmP87l8kyA6J/ctRvZ59sRw6m9pEPEnFv8aNuh07Zwp1oSmMIOmIU3SHxtt7ahQ+B/tUFuSDbrSEtwsheymIYdmMz1woHit73v14Epk0sJga3L1+0aXvuc+d6ZNdI1Cpb55sV9wmpm/HjQSZOVpF44f0KIbnmFcscrsZst4zY14iD5t4vOapVw3M6/rgI0CVTAYdcHGeFcBJ7YznuMFfnmrU8CdK7yLhySHPzsfTw1kcdAjAdBVmPYE+YEXppyslOjfuZSPYIyQ2chV8EZ/JXN9MWUz8k4jrnLZPwZgQvRiG4vkNTZ3HfIgRiBRG1PUPDUMCoRaSgItTgf6csCq3UAtiE6+FRoKAeKm217e9QIq4NUqXOjlKnvyLU+SqBlPnzeCyDEMq9Q9IFvW2RnJXM8eF5y2ZzTZNS+Oww8JlFrgxe+6ik7HUUqQvf2bz3aVtJ0A5gvVDeOBqKGFeKNOz0g5/0rbC36g/1giz9T+H207x3cOwzXJJ50Ltd3qKgAln+F7QfXNYG/jRPYN71sIrKWDPj2/SbLymicwnYCmvWN6yjjLhQtC0QlZrvmkJUDyWzJnso3CUdQlZA3ysXl/v9ViA05WD/otGbSLmvGzIQNYznjuk5GFUODZTVsHCJLIVAfgaXAzJ9d/aQddsOCpQQaWB1LGulo6LENkPCW4y8AEsdXSGHDopPLJ1aM0OBNFcoFrAGTvlh5kM1YUNMf0UaqSxaLUEV8THOAA==" ); logger.info("Data..."+value);
				 
	 
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
			HostnameVerifier hostnameVerifier = (host, sslSession) -> true;
			TrustManager[] trustManagers = new TrustManager[]{UnsafeX509ExtendedTrustManager.getInstance()};

			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(null, trustManagers, null);

			LayeredConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			CloseableHttpClient httpClient = HttpClients.custom()
			        .setSSLSocketFactory(socketFactory)
			        .build();
			
			             HttpPost post=new HttpPost(urlCdac);
			             encryptedPassword  = MD5(passwordForSMS);
			             String genratedhashKey = hashGenerator(userNameCdac, senderIdCdac, finalmessage, secureKeyCdac);
			             
			             List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(1);
			             
			             @SuppressWarnings("java:S2068")
			             String query = MessageFormat.format(
			            		    "username={0}&pwd={1}&smsservicetype={2}&content={3}&mobileno={4}&senderid={5}&key={6}&templateid={7}",
			            		    URLEncoder.encode(userNameCdac, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(passwordForSMS, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(serviceType, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(finalmessage, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(smsBean.getMobileNumber(), StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(senderIdCdac, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(genratedhashKey, StandardCharsets.UTF_8.toString()),
			            		    URLEncoder.encode(templateid, StandardCharsets.UTF_8.toString())
			            		);

						 int queryLength = query.length();
			             nameValuePairs.add(new BasicNameValuePair("mobileno", smsBean.getMobileNumber()));
			             nameValuePairs.add(new BasicNameValuePair("senderid", senderIdCdac));
			             nameValuePairs.add(new BasicNameValuePair("content", finalmessage));
			             nameValuePairs.add(new BasicNameValuePair("smsservicetype", serviceType));
			             nameValuePairs.add(new BasicNameValuePair("username", userNameCdac));
			             nameValuePairs.add(new BasicNameValuePair("password", encryptedPassword));
			             nameValuePairs.add(new BasicNameValuePair("key", genratedhashKey));
                         nameValuePairs.add(new BasicNameValuePair("templateid", templateid));
			             post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			             
			             HttpResponse response=httpClient.execute(post);
			             BufferedReader bf=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			             String line="";
			             while((line=bf.readLine())!=null){
			                 responseString = responseString+line;
			             }
			             //System.out.println(responseString);
			                  endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;
						    String logText = "SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+responseString+"||"+smsBean.getSmsText()+"||"+urlCdac.toString()+"?"+query+"||"+responseString ;	
						 //   System.out.println(logText);
						    if(!responseString.contains("402,MsgID")) {
						    	throw new DMSBusinessException("SMS_DELIVERY_FAILURE_IOException");
						    }
			         } catch (NoSuchAlgorithmException e) {
							endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_NoSuchAlgorithmException");
			         } catch (KeyManagementException e) {
			                endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_KeyManagementException");
			         } catch (UnsupportedEncodingException e) {
			                endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_UnsupportedEncodingException");
			         } catch (ClientProtocolException e) {
			                endtime = System.currentTimeMillis();
							timetaken = (double)(endtime - starttime)/1000;	
							logger.error("SMS Delivery:transaction logs||"+smsBean.getMobileNumber()+"||"+ sdf.format(date) + "||"+ timetaken + "||"+urlCdac.toString()+"?||"+e );
							throw new DMSBusinessException("SMS_DELIVERY_FAILURE_ClientProtocolException");
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


	public String CalculateMD5Hash(String input,String time) { 
		
		
		try {
			 
            // Static getInstance method is called with hashing MD5
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
	
	
	public String getDecryptData(String stringToDecrypt) {
		String _key="20220926";
		stringToDecrypt = stringToDecrypt.replace(" ", "+");
        byte[] rgbIV = new byte[]{
                (byte) 10,
                (byte) 20,
                (byte) 30,
                (byte) 40,
                (byte) 50,
                (byte) 60,
                (byte) 70,
                (byte) 80
        };

        try {
            byte[] bytes = Arrays.copyOf(_key.getBytes(StandardCharsets.UTF_8), 8);
            DESKeySpec desKeySpec = new DESKeySpec(bytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(rgbIV));
            byte[] buffer = Base64.getDecoder().decode(stringToDecrypt);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            cipherOutputStream.write(buffer);
            cipherOutputStream.close();
            String decryptedString = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
            logger.info("Descypted...."+decryptedString);
            return decryptedString.toString();
        } catch (Exception ex) {
        	logger.info("Error: " + ex.getMessage());
        }
        
        return null;
    }
	

	
	
	
	private static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		//md = MessageDigest.getInstance("SHA-1");
		md = MessageDigest.getInstance("HmacSHA256");
		byte[] md5 = new byte[64];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5 = md.digest();
		return convertedToHex(md5);
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
