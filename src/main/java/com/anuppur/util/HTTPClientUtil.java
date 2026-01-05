package com.anuppur.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.anuppur.exception.DMSBusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Kumar Gaurav Sharma
 *
 */
@Service
public class HTTPClientUtil {

	/*
	 * @Value("${gst.url}") private String gstUrl;
	 * 
	 * @Value("${gst.email}") private String gstEmail;
	 * 
	 * @Value("${gst.clientid}") private String gstClientId;
	 * 
	 * @Value("${gst.secretkey}") private String gstSecretKey;
	 * 
	 * @Value("${cin.url}") private String cinUrl;
	 * 
	 * @Value("${pan.url}") private String panUrl;
	 */
	
	
	
	

	
	public static final Logger logger = LoggerFactory.getLogger(HTTPClientUtil.class);
	
	public static String sendHTTPUrlConnectionRequest(Map<String, String> requestParams, Map<String, String> headers, String body, String url, String httpMethod) throws DMSBusinessException {

		long starttime = System.currentTimeMillis();
		long endtime = 0;
		StringBuilder qString = new StringBuilder("");
		double timetaken = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String responseString = "";
		try {
			String spec = url;
			
			if(requestParams!=null) {
				int size =requestParams.entrySet().size();
				int i=1;
				for (Entry<String, String> entry : requestParams.entrySet()) {
					
					if(i==size) {
						if(entry.getKey().equalsIgnoreCase("SecreteKey") && url.contains("prd.mp.gov.in")) {
							qString.append(entry.getKey() + "=" + entry.getValue());
						}else {
							qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
						}
							
					
					} else {
						if(entry.getKey().equalsIgnoreCase("SecreteKey") && url.contains("prd.mp.gov.in")) {
							qString.append(entry.getKey() + "=" + entry.getValue() + "&");
						}else {
							qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
						}
						
					}
					i++;
				}
//				if(requestParams.containsKey("gstin")) {
//					qString.append("gstin=" + URLEncoder.encode(requestParams.get("gstin"), "UTF-8") + "&");
//				}
				spec = url + "?" + qString;
			}
			
			
			
			URL requestUrl = new URL(spec);
			//System.out.println("URL: "+requestUrl);
			
			HttpsURLConnection connection = (HttpsURLConnection) requestUrl.openConnection();
			 
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(httpMethod);
			
				
			
			
		
			HttpsURLConnection.setFollowRedirects(true);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			// connection.setRequestProperty("Content-length",String.valueOf(queryLength));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");
			
			
			if(headers!=null) {
				for (Entry<String, String> entry : headers.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			if(body!=null && !StringUtils.isEmpty(body)) {
				OutputStream outStream = connection.getOutputStream();
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
				outStreamWriter.write(body);
				outStreamWriter.flush();
				outStreamWriter.close();
				outStream.close();
			}
			
			
			int responseCode = connection.getResponseCode();
			//System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpsURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				//System.out.println(response.toString());
				responseString=response.toString();
				responseString = responseString!=null?responseString:connection.getResponseMessage();
			}
			
			/*
			
			if(body!=null) {
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
				osw.write(body);
				osw.flush();
				osw.close();
				os.close();
			}

			
			// get ready to read the response from the cgi script
		DataInputStream input = new DataInputStream(connection.getInputStream());
		//String responseTxt = readLine(input);
			// read in each character until end-of-stream is detected
			String responseTxt = "";
			for (int c = input.read(); c != -1; c = input.read()) {
				responseTxt += (char) c;
			}
			
		input.close();
			

//			System.out.println("Resp Code:" + connection.getResponseCode());
//			System.out.println("Resp Message:"+ connection.getResponseMessage());
//			System.out.println("Resp Text:"+ responseTxt);

			if (connection.getResponseCode() != 200) {
				// resendSingleUnicodeSMS(smsBean);
			}
			responseString = responseTxt!=null?responseTxt:connection.getResponseMessage();
			
			*/
			
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			String logText = "HTTPURLConnection logs||" + sdf.format(date) + "||" + timetaken + "||" + connection.getResponseCode() + "||" + url.toString() + "?" + qString + "||" + connection.getResponseMessage() + ":\n" + responseString;
			logger.info(logText);
			
		}  catch (MalformedURLException  e) {
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_MALFORMED_URL_EXCEPTION");
		} catch (UnsupportedEncodingException e) {
		
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_USUPPORTATED_ENCODING_EXCEPTION");
		} catch (SocketTimeoutException  e) {
			
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_SOCKET_TIMEOUT_EXCEPTION");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_CONN_IO_EXCEPTION");
		
		}catch (Exception e) {
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_CONN_ERROR");
		}
		//logger.info(responseString);
		return responseString;
	}
	
	
	public static String sendHTTPUrlConnectionPnRD(Map<String, String> requestParams, Map<String, String> headers, String body, String url, String httpMethod)  throws DMSBusinessException{

		long starttime = System.currentTimeMillis();
		long endtime = 0;
		StringBuilder qString = new StringBuilder("");
		double timetaken = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String responseString = "";
		try {
			String spec = url;
			
			if(requestParams!=null) {
				int size =requestParams.entrySet().size();
				int i=1;
				for (Entry<String, String> entry : requestParams.entrySet()) {
					
					if(i==size) {
						if(entry.getKey().equalsIgnoreCase("SecreteKey") && url.contains("prd.mp.gov.in")) {
							qString.append(entry.getKey() + "=" + entry.getValue());
						}else {
							qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
						}
							
					
					} else {
						if(entry.getKey().equalsIgnoreCase("SecreteKey") && url.contains("prd.mp.gov.in")) {
							qString.append(entry.getKey() + "=" + entry.getValue() + "&");
						}else {
							qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
						}
						
					}
					i++;
				}
				spec = url + "?" + qString;
			}
			
			URL requestUrl = new URL(spec);
			HttpsURLConnection connection = (HttpsURLConnection) requestUrl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(httpMethod);

			HttpsURLConnection.setFollowRedirects(true);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			 //connection.setRequestProperty("Content-length",String.valueOf(queryLength));
			//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");
			
			
			if(headers!=null) {
				for (Entry<String, String> entry : headers.entrySet()) {
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			if(body!=null && !StringUtils.isEmpty(body)) {
				OutputStream outStream = connection.getOutputStream();
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
				outStreamWriter.write(body);
				outStreamWriter.flush();
				outStreamWriter.close();
				outStream.close();
			}
			
			
			int responseCode = connection.getResponseCode();
			//System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpsURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
			//	System.out.println(response.toString());
				responseString=response.toString();
				responseString = responseString!=null?responseString:connection.getResponseMessage();
			}
			
			/*
			
			if(body!=null) {
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
				osw.write(body);
				osw.flush();
				osw.close();
				os.close();
			}

			
			// get ready to read the response from the cgi script
		DataInputStream input = new DataInputStream(connection.getInputStream());
		//String responseTxt = readLine(input);
			// read in each character until end-of-stream is detected
			String responseTxt = "";
			for (int c = input.read(); c != -1; c = input.read()) {
				responseTxt += (char) c;
			}
			
		input.close();
			

//			System.out.println("Resp Code:" + connection.getResponseCode());
//			System.out.println("Resp Message:"+ connection.getResponseMessage());
//			System.out.println("Resp Text:"+ responseTxt);

			if (connection.getResponseCode() != 200) {
				// resendSingleUnicodeSMS(smsBean);
			}
			responseString = responseTxt!=null?responseTxt:connection.getResponseMessage();
			
			*/
			
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			String logText = "HTTPURLConnection logs||" + sdf.format(date) + "||" + timetaken + "||" + connection.getResponseCode() + "||" + url.toString() + "?" + qString + "||" + connection.getResponseMessage() + ":\n" + responseString;
			logger.info(logText);
			
		}  catch (MalformedURLException  e) {
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_MALFORMED_URL_EXCEPTION");
		} catch (UnsupportedEncodingException e) {
		
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_USUPPORTATED_ENCODING_EXCEPTION");
		} catch (SocketTimeoutException  e) {
			
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_SOCKET_TIMEOUT_EXCEPTION");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_CONN_IO_EXCEPTION");
		
		}catch (Exception e) {
			endtime = System.currentTimeMillis();
			timetaken = (double)(endtime - starttime)/1000;	
			logger.error("HTTPURLConnection logs||"+ sdf.format(date) + "||"+ timetaken + "||"+url.toString()+"?"+qString+"||"+e );
			throw new DMSBusinessException("REMOTE_CONN_ERROR");
		}
		//logger.info(responseString);
		return responseString;
	}
	
	
	 public static String readLine(InputStream in) throws IOException {
		    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		    while (true) {
		      int b = in.read();
		      if (b < 0) {
		        throw new IOException("Data truncated");
		      }
		      if (b == 0x0A) {
		        break;
		      }
		      buffer.write(b);
		    }
		    return new String(buffer.toByteArray(), "UTF-8");
		  }

	public static String fetchResponse(Map<String, String> requestParams, Map<String, String> headersMap, Object body, String url, HttpMethod httpMethod) throws Exception{
		long starttime = System.currentTimeMillis();
		long endtime = 0;
		StringBuilder qString = new StringBuilder("");
		double timetaken = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String responseString = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			     
			HttpHeaders headers = new HttpHeaders();
			for (Entry<String, String> entry : headersMap.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
			
			String baseUrl = url;
			if(requestParams!=null) {
				int size =requestParams.entrySet().size();
				int i=1;
				for (Entry<String, String> entry : requestParams.entrySet()) {
					if(i==size)
						qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
					else
						qString.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
					
					i++;
				}
				baseUrl = url + "?" + qString;
			}
			 
			HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
			
			URI uri = new URI(baseUrl);
			ResponseEntity<String> result = restTemplate.exchange(uri, httpMethod, requestEntity, String.class);
			     
			if(result!=null && result.getBody()!=null) {
				responseString = result.getBody();
			} 
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			String logText = "RestTemplateConnection logs||" + sdf.format(date) + "||" + timetaken + "||" + result.getStatusCode() + "||" + url.toString() + "?" + qString + "||" + result.getBody();
			logger.info(logText);
			return responseString;
		} catch(Exception e) {
			endtime = System.currentTimeMillis();
			timetaken = (double) (endtime - starttime) / 1000;
			String logText = "RestTemplateConnection logs||" + sdf.format(date) + "||" + timetaken + "||" + e.getMessage() + "||" + url.toString() + "?" + qString;
			logger.error(logText, e);
			throw new DMSBusinessException("Unable to connect servers");
		}
	}
	
//	public static void main(String[] a) {
//	
//	String cin="L21091KA2019OPC141331";
//	String cinurl="https://apisetu.gov.in/mca/v1/companies/"+cin;
//	
//	Map<String, String> headerMap = new HashMap<String, String>();
//	headerMap.put("X-APISETU-APIKEY", "VpIlkRP17f8n56Ts0PMLceMwFRDXkQW7");
//	headerMap.put("X-APISETU-CLIENTID", "in.gov.mpigr");
//	
//	String response = sendHTTPUrlConnectionRequest(null, headerMap, cinurl);
//	System.out.println("response: "+response);	
		
//		String responseFail = "{\"status_cd\":\"0\",\"status_desc\":\"Public API Search Taxpayer Fail\",\"error\":{\"message\":\"No records found\",\"error_cd\":\"FO8000\"}}";
//		String gstRes = "{\"data\":{\"stjCd\":\"BR013\",\"stj\":\"Gandhi Maidan\",\"dty\":\"Regular\",\"lgnm\":\"UJJIVAN SMALL FINANCE BANK LIMITED\",\"adadr\":[{\"addr\":{\"bnm\":\"3353-PATNA CITY\",\"st\":\"P.S. Khajekala, P.O. Jhauganj,\",\"loc\":\"Patna\",\"bno\":\"Gujari Bazar\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"\",\"lt\":\"\",\"pncd\":\"800001\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3358-HAJIPUR\",\"st\":\"Dighi Kala,Opp- Telephone Exchange,Hajipur\",\"loc\":\"Vaishali\",\"bno\":\"C/O Mr. Rambilash Singh\",\"stcd\":\"Bihar\",\"dst\":\"Vaishali\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"844101\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3373-FATUAH\",\"st\":\"Devi Chak, Fatua,\",\"loc\":\"Patna\",\"bno\":\"Station Road\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"803201\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3375-FULWARI SHARIFF\",\"st\":\"Phulwari Sharif, Anishabad\",\"loc\":\"Patna\",\"bno\":\"Telephone Exchange Building\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"801505\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3388-MDDM COLLEGE\",\"st\":\"Diwan Road\",\"loc\":\"Muzaffarpur\",\"bno\":\"Saroj Complex\",\"stcd\":\"Bihar\",\"dst\":\"Muzaffarpur\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"842001\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3389-ZERO MILE\",\"st\":\"Near Bairiya Bus Stand,\",\"loc\":\"Muzaffarpur\",\"bno\":\"C/O R. K. Singh, Ayachi Gram, Road No.6\",\"stcd\":\"Bihar\",\"dst\":\"Muzaffarpur\",\"city\":\"\",\"flno\":\"Ground\",\"lt\":\"\",\"pncd\":\"842002\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3524-SULTANGANJ\",\"st\":\"Sultanganj\",\"loc\":\"Bhagalpur\",\"bno\":\"Vill and P.O. Thana Road,\",\"stcd\":\"Bihar\",\"dst\":\"Bhagalpur\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"813213\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3525-KAHELGAON\",\"st\":\"Near Bus Stand, Kahelgaon,\",\"loc\":\"Bhagalpur\",\"bno\":\"Choudharytola, C/O Shankar Service Station\",\"stcd\":\"Bihar\",\"dst\":\"Bhagalpur\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"813203\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3529-PURNIA-RNSHAW CHOWK\",\"st\":\"P. S. K. Hat Line Bazar, P.O.Bhattabazar,\",\"loc\":\"Purnia\",\"bno\":\"Suddin Chowk\",\"stcd\":\"Bihar\",\"dst\":\"Purnea\",\"city\":\"\",\"flno\":\"Ground floor\",\"lt\":\"\",\"pncd\":\"854301\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3535-GAYA\",\"st\":\"New Area, Nutan Nagar,\",\"loc\":\"Gaya\",\"bno\":\"Menka Bhawan\",\"stcd\":\"Bihar\",\"dst\":\"Gaya\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"823001\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3537-JEHANABAD\",\"st\":\"Near Sbi Bank,\",\"loc\":\"Jehanabad\",\"bno\":\"Horil Gang\",\"stcd\":\"Bihar\",\"dst\":\"Jehanabad\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"804408\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3544-DARBHANGA\",\"st\":\"Lalbag, Mrm Road\",\"loc\":\"Darbhanga\",\"bno\":\"Opp Dr. S. K. Das Clinic\",\"stcd\":\"Bihar\",\"dst\":\"Darbhanga\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"846004\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3545-SAHARSA\",\"st\":\"Ashoke Cinema Road Gandhi Path\",\"loc\":\"Saharsa\",\"bno\":\"Meera Bazaar\",\"stcd\":\"Bihar\",\"dst\":\"Saharsa\",\"city\":\"\",\"flno\":\"3rd floor\",\"lt\":\"\",\"pncd\":\"852201\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3547-BARAUNI\",\"st\":\"P.O. Barauni, P.S. Phuwaria\",\"loc\":\"Begusarai\",\"bno\":\"Near Aloochhati Road\",\"stcd\":\"Bihar\",\"dst\":\"Begusarai\",\"city\":\"\",\"flno\":\"1st floor\",\"lt\":\"\",\"pncd\":\"851112\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3548-BEGUSARAI\",\"st\":\"Near Amardeep Cinema Hall, NH 31\",\"loc\":\"Begusarai\",\"bno\":\"Baba Market\",\"stcd\":\"Bihar\",\"dst\":\"Begusarai\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"851101\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3557 Samastipur\",\"st\":\"Mohanpur Road,\",\"loc\":\"Samastipur\",\"bno\":\"Ruby Mention,\",\"stcd\":\"Bihar\",\"dst\":\"Samastipur\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"848101\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"3558 Dalsingsarai\",\"st\":\"Yaswant Nagar,Nh-28,Hatt Nawada,Dalsingh Sarai\",\"loc\":\"P.S.- Dalsingh Sarai, Dist- Samastipur\",\"bno\":\"Veena Complex,\",\"stcd\":\"Bihar\",\"dst\":\"Samastipur\",\"city\":\"\",\"flno\":\"2nd floor\",\"lt\":\"\",\"pncd\":\"848114\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"Beside Raymonds Show Room\",\"st\":\"Beside Raymonds Show Room\",\"loc\":\"Danapur, Patna\",\"bno\":\"Saguna More, Mainpura Main Road\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"Saguna More, Mainpura Main Road\",\"lt\":\"\",\"pncd\":\"801503\",\"lg\":\"\"},\"ntr\":\"Office / Sale Office, Supplier of Services, Recipient of Goods or Services, Retail Business, Others\"},{\"addr\":{\"bnm\":\"\",\"st\":\"Central Jail Road, Bihar, Dist. - Bhagalpur\",\"loc\":\"Central Jail Road, Bihar, Dist. - Bhagalpur\",\"bno\":\"Ganji Mill Campus, Tilka Majhi\",\"stcd\":\"Bihar\",\"dst\":\"Bhagalpur\",\"city\":\"\",\"flno\":\"Central Jail Road, Bihar, Dist. - Bhagalpur\",\"lt\":\"\",\"pncd\":\"812001\",\"lg\":\"\"},\"ntr\":\"Retail Business, Office / Sale Office, Supplier of Services, Recipient of Goods or Services, Others\"},{\"addr\":{\"bnm\":\"\",\"st\":\"Ajay Market, Jaiprakash Nagar\",\"loc\":\"Opp. BSNL Tower, Maner\",\"bno\":\"Ajay Market, Jaiprakash Nagar\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"Ajay Market, Jaiprakash Nagar\",\"lt\":\"\",\"pncd\":\"801108\",\"lg\":\"\"},\"ntr\":\"Retail Business, Office / Sale Office, Recipient of Goods or Services, Supplier of Services, Others\"},{\"addr\":{\"bnm\":\"\",\"st\":\"Near Mahavir Petrol Pump,Chattauni Chowk\",\"loc\":\"Motihari, East Champaran\",\"bno\":\"Bhawanipur Zirat,  N.H. Bye pass\",\"stcd\":\"Bihar\",\"dst\":\"East Champaran\",\"city\":\"\",\"flno\":\"Near Mahavir Petrol Pump,Chattauni Chowk\",\"lt\":\"\",\"pncd\":\"845401\",\"lg\":\"\"},\"ntr\":\"Retail Business, Office / Sale Office, Supplier of Services, Recipient of Goods or Services, Others\"}],\"cxdt\":\"\",\"gstin\":\"10AABCU9603R1Z2\",\"nba\":[\"Supplier of Services\",\"Office / Sale Office\",\"Recipient of Goods or Services\",\"Retail Business\",\"Others\"],\"lstupdt\":\"13/10/2021\",\"rgdt\":\"25/07/2017\",\"ctb\":\"Public Limited Company\",\"pradr\":{\"addr\":{\"bnm\":\"3374\",\"st\":\"sheet no 31 Thana Kotwali New Dak Bunglaw road\",\"loc\":\"Patna\",\"bno\":\"Ward 2 Circle No 6\",\"stcd\":\"Bihar\",\"dst\":\"Patna\",\"city\":\"\",\"flno\":\"\",\"lt\":\"\",\"pncd\":\"800001\",\"lg\":\"\"},\"ntr\":\"Supplier of Services, Office / Sale Office, Recipient of Goods or Services, Retail Business, Others\"},\"sts\":\"Active\",\"ctjCd\":\"XU0201\",\"tradeNam\":\"UJJIVAN SMALL FINANCE BANK LIMITED\",\"ctj\":\"GANDHI MAIDAN RANGE\"},\"status_cd\":\"1\",\"status_desc\":\"Public API Search Taxpayer Success\"}";
//		GstResponse gstResponse = new Gson().fromJson(gstRes!=null?gstRes:responseFail, GstResponse.class);
//		System.out.println(gstResponse.getStatus_cd());
//	}
	
	/*public GstResponse getGSTValidation(String gstNo) throws DMSBusinessException {
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("email", gstEmail);
		reqMap.put("gstin", gstNo);
		
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("client_id", gstClientId);
		headerMap.put("client_secret", gstSecretKey);
		
		try {
			String response = sendHTTPUrlConnectionRequest(reqMap, headerMap, null, gstUrl, "GET");
			//<response>  <message>Invalid UNQ Id</message></response>
			
//			String response = "{\"data\":{\"stjCd\":\"TG149\",\"dty\":\"Regular\",\"stj\":\"NACHARAM-II\",\"lgnm\":\"S M ENTERPRISES\",\"adadr\":[],\"cxdt\":\"\",\"gstin\":\"36ABSFS7913D1ZT\",\"nba\":[\"Factory / Manufacturing\"],\"lstupdt\":\"16/09/2019\",\"ctb\":\"Partnership\",\"rgdt\":\"01/07/2017\",\"pradr\":{\"addr\":{\"bnm\":\"SURVEY NO.257\",\"st\":\"PHASE-II\",\"loc\":\"CHERLAPALLY,HYDERABAD\",\"bno\":\"PLOT NO.200/1/B\",\"stcd\":\"Telangana\",\"dst\":\"Ranga Reddy\",\"city\":\"\",\"flno\":\"\",\"lt\":\"\",\"pncd\":\"500051\",\"lg\":\"\"},\"ntr\":\"Factory / Manufacturing\"},\"ctjCd\":\"YO0601\",\"tradeNam\":\"S M ENTERPRISES\",\"sts\":\"Active\",\"ctj\":\"UPPAL\"},\"status_cd\":\"1\",\"status_desc\":\"Public API Search Taxpayer Success\"}";
			String responseFail = "{\"status_cd\":\"0\",\"status_desc\":\"Public API Search Taxpayer Fail\",\"error\":{\"message\":\"No records found\",\"error_cd\":\"FO8000\"}}";
//			String responseBad = "{\"status_cd\":\"0\",\"error\":{\"error_cd\":\"0\"}}";
			return new Gson().fromJson(response!=null?response:responseFail, GstResponse.class);

			}catch (DMSBusinessException e) {
				throw new DMSBusinessException("HTTP_REMOTE_EXCEPTION");   
			}catch(Exception e) {
			
			}
		return null;
	}*/
	
	/*public CinResponse getCINValidation(String cin) throws DMSBusinessException {
		
		Map<String, String> headerMap = new HashMap<String, String>();
	    headerMap.put("X-APISETU-APIKEY", "VpIlkRP17f8n56Ts0PMLceMwFRDXkQW7");
		headerMap.put("X-APISETU-CLIENTID", "in.gov.mpigr");
		//String response = sendHTTPUrlConnectionRequest(null, headerMap, null, cinUrl+cin, "GET");
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("cin", cin);
		String response = sendXmlString(tokenMap, headerMap, null, cinUrl, "POST");
		 CinResponse returnResponse= new CinResponse();
		 Document doc = ConversionUtil.convertStringToXMLDocument(response);
		if (doc.hasChildNodes()) {
			// ConversionUtil.printNodeList(doc.getChildNodes());
		}

		Element documentElement = doc.getDocumentElement();
		NodeList nList = doc.getElementsByTagName("return");
		Node elemNode = nList.item(0);
		
		if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
			if (elemNode.hasChildNodes()) {
				NodeList childNodeleve1 = elemNode.getChildNodes();
				//returnResponse.setError(null);
				//returnResponse.setErrorDescription(resp.getErrorDescription());
				for (int count = 0; count < childNodeleve1.getLength(); count++) {
					Node elemChildNodeLevel1 = childNodeleve1.item(count);
					if (elemChildNodeLevel1.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println("\nNode Name =" + elemChildNodeLevel1.getNodeName() + "Node Content ="+ elemChildNodeLevel1.getTextContent());
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("cin"))
						{	
							System.out.println("cin"+elemChildNodeLevel1.getTextContent());
						   returnResponse.setCin(elemChildNodeLevel1.getTextContent());
						}
							//xmlResponseObject.setSuccess(elemChildNodeLevel1.getTextContent());
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("companyName"))
						{
							System.out.println("companyName"+elemChildNodeLevel1.getTextContent());
							returnResponse.setCompanyName(elemChildNodeLevel1.getTextContent());	
						}	
							//xmlResponseObject.setMessage(elemChildNodeLevel1.getTextContent());
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("companyStatus")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setCompanyStatus(returnResponse.getCompanyStatus());
						}

						
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("companyStatus")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setEmail(returnResponse.getCompanyStatus());
						}
						
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("financialAuditStatus")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setFinancialAuditStatus(returnResponse.getCompanyStatus());
						}
						
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("registeredAddress")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setRegisteredAddress(returnResponse.getCompanyStatus());
						}
						
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("registeredContactNo")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setRegisteredContactNo(returnResponse.getCompanyStatus());
						}
						
						if (elemChildNodeLevel1.getNodeName() != null && elemChildNodeLevel1.getNodeName().equalsIgnoreCase("rocCode")) {
							System.out.println("email"+elemChildNodeLevel1.getTextContent());
							returnResponse.setRocCode(returnResponse.getCompanyStatus());
						}
					}

				}
			}
		}
		
		return returnResponse;
	}*/
	
	
	/*public CinResponse getCINValidationXMLResponse(String cin) throws DMSBusinessException {
		Map<String, String> headerMap = new HashMap<String, String>();

		headerMap.put("X-APISETU-APIKEY", "VpIlkRP17f8n56Ts0PMLceMwFRDXkQW7");
		headerMap.put("X-APISETU-CLIENTID", "in.gov.mpigr");
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("cin", cin);
		
		String response = sendXmlString(tokenMap, headerMap, null, cinUrl, "POST");

		return new Gson().fromJson(response, CinResponse.class);
	}
	*/
	/*public PanResponse getPANValidation(String pan) {
//		Map<String, String> headerMap = new HashMap<String, String>();
//		headerMap.put("X-APISETU-APIKEY", "VpIlkRP17f8n56Ts0PMLceMwFRDXkQW7");
//		headerMap.put("X-APISETU-CLIENTID", "in.gov.mpigr");
//		String body = "{"txnId":"f7f1469c-29b0-4325-9dfc-c567200a70f7","format":"xml","certificateParameters":{"panno":"ABCD123EF","PANFullName":"RAMESHWAR KUMAR SINGH","FullName":"Sunil Kumar","DOB":"31-12-1980","GENDER":"M"},"consentArtifact":{"consent":{"consentId":"ea9c43aa-7f5a-4bf3-a0be-e1caa24737ba","timestamp":"2022-01-31T05:58:05.133Z","dataConsumer":{"id":"string"},"dataProvider":{"id":"string"},"purpose":{"description":"string"},"user":{"idType":"string","idNumber":"string","mobile":"string","email":"string"},"data":{"id":"string"},"permission":{"access":"string","dateRange":{"from":"2022-01-31T05:58:05.133Z","to":"2022-01-31T05:58:05.133Z"},"frequency":{"unit":"string","value":0,"repeats":0}}},"signature":{"signature":"string"}}}";
//		String response = sendHTTPUrlConnectionRequest(null, headerMap, body, panUrl, "POST");
		String response = "{\"Certificate\":{\"IssuedBy\":{\"Organization\":{\"Address\":{\"type\":\"string\",\"line1\":\"string\",\"line2\":\"string\",\"house\":\"string\",\"landmark\":\"string\",\"locality\":\"string\",\"vtc\":\"string\",\"district\":\"string\",\"pin\":\"string\",\"state\":\"string\",\"country\":\"IN\"},\"name\":\"Income Tax Department\",\"code\":\"string\",\"tin\":\"string\",\"uid\":\"string\",\"type\":\"CG\"}},\"IssuedTo\":{\"Person\":{\"Address\":{\"type\":\"permanent\",\"line1\":\"string\",\"line2\":\"string\",\"house\":\"string\",\"landmark\":\"string\",\"locality\":\"string\",\"vtc\":\"string\",\"district\":\"string\",\"pin\":\"string\",\"state\":\"string\",\"country\":\"IN\"},\"Photo\":{\"format\":\"string\"},\"uid\":\"215051602786\",\"title\":\"string\",\"name\":\"ANAND KUMAR\",\"dob\":\"01-10-1988\",\"swd\":\"PRAMOD KUMAR\",\"swdIndicator\":\"S\",\"gender\":\"string\",\"maritalStatus\":\"string\",\"religion\":\"string\",\"phone\":\"string\",\"email\":\"string\"}},\"CertificateData\":{\"PANVerificationRecord\":{\"num\":\"BBXPK2797M\",\"verifiedOn\":\"12-12-2018 12:53:57\"}},\"language\":\"99\",\"name\":\"PAN Card\",\"type\":\"PANCR\",\"number\":\"BBXPK2797M\",\"issuedAt\":\"Digilocker\",\"issueDate\":\"string\",\"validFromDate\":\" \",\"status\":\"A\"}}";
//		String responseFail = "{\"error\":\"server_error\",\"errorDescription\":\"Service is unavailable\"}";
//		return new Gson().fromJson(response!=null?response:responseFail, PanResponse.class);
		return new Gson().fromJson(response, PanResponse.class);
	}
	*/
	/*
	 * public String sendXmlString(Map<String, String> requestParams, Map<String,
	 * String> headers, String body, String cinUrl, String httpMethod){ String
	 * xmlResponceString = ""; String xmlStringTemp =
	 * "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:mca=\"http://www.mca.gov.in/\">    <soap:Header/>    <soap:Body>       <mca:getCINInfo>     <arg0>[cin]</arg0>      </mca:getCINInfo>    </soap:Body>  </soap:Envelope>"
	 * ;
	 * 
	 * 
	 * String xmlString= AccountUtil.replaceTokens(xmlStringTemp, requestParams);
	 * try { // Replace here with your target URL
	 * 
	 * URL url = new URL(cinUrl); HttpURLConnection connection = (HttpURLConnection)
	 * url.openConnection();
	 * 
	 * // Set timeout as per needs connection.setConnectTimeout(20000);
	 * connection.setReadTimeout(20000);
	 * 
	 * // Set DoOutput to true if you want to use URLConnection for output. //
	 * Default is false connection.setDoOutput(true);
	 * 
	 * connection.setUseCaches(true); connection.setRequestMethod("POST");
	 * 
	 * // Set Headers
	 * 
	 * connection.setRequestProperty("Content-Type", "application/soap+xml");
	 * connection.setRequestProperty("User-Agent", "Mozilla"); //
	 * connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5"); // Write
	 * XML OutputStream outputStream = connection.getOutputStream(); byte[] b =
	 * xmlString.getBytes("UTF-8"); outputStream.write(b); outputStream.flush();
	 * outputStream.close();
	 * 
	 * // Read XML InputStream inputStream = connection.getInputStream(); byte[] res
	 * = new byte[2048]; int i = 0; StringBuilder response = new StringBuilder();
	 * while ((i = inputStream.read(res)) != -1) { response.append(new String(res,
	 * 0, i)); } inputStream.close(); xmlResponceString = new
	 * String(response.toString()); } catch (IOException e) { e.printStackTrace(); }
	 * return xmlResponceString; }
	 */
	


 

}
