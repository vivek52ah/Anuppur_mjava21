package com.anuppur.util;


import java.io.*;

import java.net.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class APIBased {
	public static final Logger logger1 = LoggerFactory.getLogger(APIBased.class);
	public static class DummyTrustManager implements X509TrustManager {

		public static final Logger logger = LoggerFactory.getLogger(DummyTrustManager.class);
		
	       public DummyTrustManager() {
	       }

	       public boolean isClientTrusted(X509Certificate cert[]) {
	               return true;
	       }

	       public boolean isServerTrusted(X509Certificate cert[]) {
	               return true;
	       }

	       public X509Certificate[] getAcceptedIssuers() {
	               return new X509Certificate[0];
	       }

	       public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	       }

	       public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

	       }
	   }
	public static class DummyHostnameVerifier implements HostnameVerifier {

	    public boolean verify( String urlHostname, String certHostname ) {
	            return true;
	    }

	    public boolean verify(String arg0, SSLSession arg1) {
	            return true;
	    }
	}
	
	public StringBuilder base64_to_binary(String base64_str) {
		// String base64_str = "MDQw";
	        byte[] decode = DatatypeConverter.parseBase64Binary(base64_str);

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < decode.length; i++){
	            String temp = Integer.toBinaryString(decode[i]);
	            sb.append(String.format("%8s", temp).replace(" ", "0"));
	        }

	   //     System.out.println(sb.toString());
	        
	        return sb;
	}
	

	
	
 public static void main(String[] args) {
 
 	
 	
 	 SSLContext sslcontext = null;
      try {
              sslcontext = SSLContext.getInstance("SSL");
              
              sslcontext.init(new KeyManager[0],
                              new TrustManager[] { new DummyTrustManager() },
                              new SecureRandom());
      } catch (NoSuchAlgorithmException e) {
              e.printStackTrace(System.err);
             
      } catch (KeyManagementException e) {
              e.printStackTrace(System.err);
      }
				
      SSLSocketFactory factory = sslcontext.getSocketFactory();
      
      //String data="V0000501^AOVPP2323N";
      String data="V0265801^AOVPP2323N";
      data="V0136601^AAIPM3854E";
      
      //String data="V0000501^AASPA5467J^AAAPM3212X";
		 //Contains details of User-id and PANs
//      String signature="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";

		 //Contains details of User-id and PANs
      //String signature="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";
      //Digital signature in PKCS7 format with base 64 encoding
      String	signature = "MIIFczCCBFugAwIBAgIIJcjMbgECtJkwDQYJKoZIhvcNAQELBQAwczELMAkGA1UEBhMCSU4xIjAgBgNVBAoTGVNpZnkgVGVjaG5vbG9naWVzIExpbWl0ZWQxDzANBgNVBAsTBlN1Yi1DQTEvMC0GA1UEAxMmU2FmZVNjcnlwdCBzdWItQ0EgZm9yIFJDQUkgQ2xhc3MzIDIwMTQwHhcNMjAwNzAyMDcwNzM0WhcNMjIwNzAyMDcwNzM0WjCCATYxCzAJBgNVBAYTAklOMScwJQYDVQQKEx5NQURIWUEgUFJBREVTSCBQUk9NT1RJT04gT0YgSVQxJzAlBgNVBAsTHk1BREhZQSBQUkFERVNIIFBST01PVElPTiBPRiBJVDEPMA0GA1UEERMGNDYyMDExMRcwFQYDVQQIEw5NYWRoeWEgUHJhZGVzaDFJMEcGA1UEFBNAMTFlYWY5MWNhNDEzM2I5MjY5MjgzYTUzNjFlMjllODlkMDVkYWIwNzY4MTM1MTM1NzVmNGU3OTlhMWFkOTc0ZDFJMEcGA1UEBRNAM2ExYzI5MzM1NWY0ZWEyNjA2MDc2YTY1ZmRkMTcyZjgxZDUwMmE4MDJmZDA0ZDYwMDQyNmUxZTI4MjJlOWI5YzEVMBMGA1UEAxMMVklOQVkgUEFOREVZMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9bfpWri3oza6LTZtutDA3r52w0msl7MYOgRBEz5M+aLm4UDq4BZZH11HODvsPaTamDze3bFbRWNvBBKPv/7ujfdUo4TtcqWN/ryUkQNXDxDmve7yi7a2fs5EDvP3wMOwOKhJbpVg7vi+KrIghuqrKesL3e0VAhfU8IIfdATW2eJMyqnHdsCFeOrGIH8jyvtIevZIqKdZGukTzHa7AN3yaFIa676Td0nBKeqeikCYfI9ySbYHcHpvpC7aeLX5J12DWnK7Foltr+4mb5TKGKce1rgyKhk0UoQ1RScpqaw2RTRns6OQMxceT17YfjwqU27hBcNq4jdj0DzjmsLk/lmAIwIDAQABo4IBRDCCAUAwDgYDVR0PAQH/BAQDAgbAMBMGA1UdIwQMMAqACETAUaMKIW3iMBEGA1UdDgQKBAhH1+ZVHDW7iTAkBgNVHREEHTAbgRlWaW5heS5wYW5kZXlAbWFwaXQuZ292LmluMEcGA1UdHwRAMD4wPKA6oDiGNmh0dHA6Ly9jcmwuc2FmZXNjcnlwdC5jb20vU2FmZVNjcnlwdFJDQUlDbGFzczMyMDE0LmNybDCBgQYIKwYBBQUHAQEEdTBzMEkGCCsGAQUFBzAChj1odHRwczovL3d3dy5zYWZlc2NyeXB0LmNvbS9kcnVwYWwvZG93bmxvYWQvUkNBSUNsYXNzMzIwMTQuY2VyMCYGCCsGAQUFBzABhhpodHRwOi8vb2NzcC5zYWZlc2NyeXB0LmNvbTATBgNVHSAEDDAKMAgGBmCCZGQCAzANBgkqhkiG9w0BAQsFAAOCAQEAx+VXgUelM4iQmcvMgiq3hCLR+1yJICftS8V0ZsHbXrSpT8j1dZlwl0d3tcQfCZaJ9Yta2tCMZW4w7VoMfwVvmuaPRB8CDdbUWpPKA04eSPn3++ETvDBRrKZvIUCTIhVpasq4JR3EWfIhiJ05T3iEhYBoJulcTmTSLTGj+1EShmU6+JJo24HiV7crcKX1cfcKwl3PFmFX87l0iFniU2seBXy2r2P3KlUzIBrIddMHgftMfUFzzstcbQVtBnKSVqnxNnhKrEW4Ege6zcYefZ59wBpPm/kv5iUhcP0wt+RiBC5O6xKIcwgau0dSHXEzhsHXykp392iSqbnTlp1BIBSQ5g==";
		 
      signature="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";
      
      String userCertificate = signature; 
      
     // X509Certificate userCert = CertificateUtil.getX509Certificate(userCertificate);
      
      
      //System.out.println("Issuer Name"+StringUtils.substringBefore(userCert.getIssuerX500Principal().getName(), ","));
      //System.out.println("Certificate Subject"+ StringUtils.substringBefore(userCert.getSubjectDN().getName(), ","));
      //System.out.println("Serial Number"+ userCert.getSerialNumber().toString());
      //System.out.println("Certificate Issuer"+ userCert.getIssuerDN().getName());
      
      
	 /* Map<String,String> certificateMap = new LinkedHashMap<String, String>();
	  certificateMap.put("Issuer Name", StringUtils.substringBefore(userCert.getIssuerX500Principal().getName(), ","));
	  certificateMap.put("Certificate Subject", StringUtils.substringBefore(userCert.getSubjectDN().getName(), ","));
	  certificateMap.put("Serial Number", userCert.getSerialNumber().toString());
	  certificateMap.put("Certificate Issuer", userCert.getIssuerDN().getName());
      */
      
	 final String version="2";
		// Contains Input version as 2 or blank. If user is sending additional version(2) parameter in the API request then new response will be received otherwise old response.
	 String urlParameters="data=";
      try{
     	 
      urlParameters =urlParameters + URLEncoder.encode(data, "UTF-8") +"&signature=" + URLEncoder.encode(signature, "UTF-8")+"&version=" + URLEncoder.encode(version, "UTF-8");
      }catch(Exception e){
     	 e.printStackTrace();
      }

		try{
		URL url;
		HttpsURLConnection connection;
		InputStream is = null;
		
		
      //------------------------
		
		
		 String ip="172.19.75.186";
		 ip="59.163.46.2";
		 ip="14.140.81.154";
		 //https://14.140.81.154/TIN/PanInquiryBackEnd
      url = new URL("https://" + ip + "/TIN/PanInquiryBackEnd");
   //url = new URL("https://59.163.46.2/TIN/PanInquiryBackEnd");
    
      //System.out.println("URL "+ip);
      
      connection = (HttpsURLConnection) url.openConnection();
      
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      
      
      connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");  
			
      connection.setUseCaches (false);
      connection.setDoInput(true);
      connection.setDoOutput(true);
     
		 connection.setSSLSocketFactory(factory);
      connection.setHostnameVerifier(new DummyHostnameVerifier());
      

     
     
      OutputStream os = connection.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      osw.write(urlParameters);
      osw.flush();
      osw.close();
     

      
     
      is =connection.getInputStream();
     
      BufferedReader in = new BufferedReader(new InputStreamReader(is));
              
      String line = in.readLine();
		
		//System.out.println("Output "+line);
		is.close();
		in.close();
				
		} catch(Exception e){
			logger1.error("Error",e);
		}
 } 
}
