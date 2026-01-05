package com.anuppur.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.anuppur.constants.DMSConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Component
public class AccountUtil {
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	@Value("${default_values}")
	private static String defaultValues;
	
	public static final Logger logger = LoggerFactory.getLogger(AccountUtil.class);

	public static String currentIndenterInCart;
	public static String[] acceptedContentTypes = { "application/pdf","image/jpg", "image/jpeg", "image/png", "image/gif" };

	public static String[] pdfOnly = { "application/pdf" };
	
	public static String EXPMSG = "";
	
	public static String getImageString(byte[] profileImage) {
		return "data:image/jpg;base64," + Base64.encodeBase64String(profileImage);
	}
	
	public static String getPDFString(byte[] profileImage) {
		return Base64.encodeBase64String(profileImage);
	}

	public static String getFiscalYear() {
		Calendar calendarDate= Calendar.getInstance();
	    int    FIRST_FISCAL_MONTH  = Calendar.MARCH;
	    
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        return (month >= FIRST_FISCAL_MONTH) ? (year + "-" + (year+1)) : (year - 1) +"-"+year;
    }

	public static String generateCaptchaText(int captchaLength) {

		String saltChars = "0123456789";
	    StringBuilder captchaStrBuffer = new StringBuilder();

	    // Build a random captchaLength chars salt
	    while (captchaStrBuffer.length() < captchaLength) {
	        int index = SECURE_RANDOM.nextInt(saltChars.length());
	        captchaStrBuffer.append(saltChars.charAt(index));
	    }

	    return captchaStrBuffer.toString();
		//return "1";
	}
	
	public static String getCaptchaBase64(String captchaStr) throws IOException {
		BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		// Set back ground of the generated image to white
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 100, 35);

		// set gradient font of text to be converted to image
		GradientPaint gradientPaint = new GradientPaint(0, 0, Color.DARK_GRAY, 20, 10, Color.LIGHT_GRAY, true);
		graphics.setPaint(gradientPaint);
		Font font = new Font("Comic Sans MS", Font.BOLD, 18);
		graphics.setFont(font);

		graphics.drawString(captchaStr, 10, 20);

		// release resources used by graphics context
		graphics.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = baos.toByteArray();
		return Base64.encodeBase64String(bytes);
	}

	public static String getMessage(String propertyFile, String key, Object[] params) {
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(propertyFile, locale);
		return MessageFormat.format(bundle.getString(key), params);
	}

	public static String generatePassword() {
		return generatePassword(6);
	}
	
	public static String generatePassword(int length) {
		String alphaNumStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuilder builder = new StringBuilder();
	    SecureRandom random = new SecureRandom();  // SecureRandom for better security
	    
	    while (length-- != 0) {
	        int character = random.nextInt(alphaNumStr.length()); // Using SecureRandom to generate a random index
	        builder.append(alphaNumStr.charAt(character));
	    }
	    
	    return builder.toString();
	}
	
	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.format(new Date());
		} catch (Exception e) {
			logger.error("failed to convert date to String", e);
			return "";
		}	
	}
	
	public static String ConvertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(date!=null)
				return dateFormat.format(date);
		} catch (Exception e) {
			logger.error("failed to convert date to String", e);
		}	
		return "";
	}
	
	public static String convertDateToStringWithFormat(Date inputDate, String format) {
		if(format==null || "".equalsIgnoreCase(format)) {
			format = DMSConstants.DTF_DDMMYYYY_S;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			if(inputDate!=null)
				return dateFormat.format(inputDate);
		} catch (Exception e) {
			logger.info("failed to convert date to String, Input Date - {}, format - {}", inputDate, format);
			logger.error("An exception occurred.", e);
		}	
		return null;
	}


	public static LocalDate convertStringToDateWithFormatDDMMYYYY(String inputDate){
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		    try {
		        if (inputDate != null && !inputDate.isEmpty() && !"00.00.0000".equals(inputDate)) {
		            return LocalDate.parse(inputDate.trim(), formatter);
		        }
		    } catch (DateTimeParseException e) {
		        logger.info("Failed to convert String to date, Input Date - {}", inputDate);
		        logger.error("An exception occurred.", e);
		    }
		    return null;
	}

	public static String getFinancialYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (month < 4)
			return (year - 1)%100 + "-" + year%100;
		else
			return year%100 + "-" + (year + 1)%100;
		
	}
	
	public static String getFinancialYearFull() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (month < 4)
			return (year - 1) + "-" + year%100;
		else
			return year + "-" + (year + 1)%100;
		
	}

	/*
	 * public static String decryptParam(String param) throws
	 * GeneralSecurityException { String decryptedString = new
	 * String(java.util.Base64.getDecoder().decode(param)); AesUtil aesUtil = new
	 * AesUtil(); if (decryptedString != null && decryptedString.split("::").length
	 * == 3) { return aesUtil.decrypt(decryptedString.split("::")[1],
	 * decryptedString.split("::")[0], "1234567891234567",
	 * decryptedString.split("::")[2]); } else{ return "0"; } }
	 * 
	 * public static String encryptLcncParam(String param) throws
	 * GeneralSecurityException, UnsupportedEncodingException { AesUtil aesUtil =
	 * new AesUtil(); return aesUtil.encrypt(AesUtil.random(128/8),
	 * AesUtil.random(128/8), "sampada@lcnc", param); }
	 * 
	 * 
	 * 
	 * public static String decryptLcncParam(String param) throws
	 * GeneralSecurityException { String decryptedString = new
	 * String(java.util.Base64.getDecoder().decode(param)); AesUtil aesUtil = new
	 * AesUtil(); if (decryptedString != null && decryptedString.split("::").length
	 * == 3) { return aesUtil.decrypt(decryptedString.split("::")[1],
	 * decryptedString.split("::")[0], "sampada@lcnc",
	 * decryptedString.split("::")[2]); } else{ return "0"; } }
	 * 
	 * public static String encryptParam(String param) throws
	 * GeneralSecurityException, UnsupportedEncodingException { AesUtil aesUtil =
	 * new AesUtil(); return aesUtil.encrypt(AesUtil.random(128/8),
	 * AesUtil.random(128/8), "1234567891234567", param); }
	 * 
	 * 
	 * 
	 * public static String decryptAngularCryptoAES(String cipherText) { try {
	 * String secret = "123456"; byte[] cipherData =
	 * java.util.Base64.getDecoder().decode(cipherText); byte[] saltData =
	 * Arrays.copyOfRange(cipherData, 8, 16);
	 * 
	 * MessageDigest md5 = MessageDigest.getInstance("MD5"); final byte[][] keyAndIV
	 * = AesUtil.GenerateKeyAndIV(32, 16, 1, saltData,
	 * secret.getBytes(StandardCharsets.UTF_8), md5); SecretKeySpec key = new
	 * SecretKeySpec(keyAndIV[0], "AES"); IvParameterSpec iv = new
	 * IvParameterSpec(keyAndIV[1]);
	 * 
	 * byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
	 * Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
	 * aesCBC.init(Cipher.DECRYPT_MODE, key, iv); byte[] decryptedData =
	 * aesCBC.doFinal(encrypted); return new String(decryptedData,
	 * StandardCharsets.UTF_8); } catch (Exception e) { e.printStackTrace(); return
	 * null; } }
	 */
	
	public static <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
            n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }
	
	public static String getBetweenStrings(String text, String textFrom,String textTo) {
	    String result = "";
	    result = text.substring(text.indexOf(textFrom) + textFrom.length(),text.length());
	    result =   result.substring(0,result.indexOf(textTo));
	    return result;
	  }

	
	public static String retuenDoubleWithTwoDigit(Double d) {
//		 DecimalFormat df = new DecimalFormat("####0.00");
		  return String.format("%.2f", d)+"";
	}
	
	
	public static final String[] units = {
		    "", " ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN",
		    "EIGHT", "NINE", "TEN", "ELEVEN", "TWELE", "THIRTEENE", "FOURTEEN",
		    "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"
		};

	public static final String[] tens = {
	    "", // 0
	    "", // 1
	    "TWENTY", // 2
	    "THIRTY", // 3
	    "FORTY", // 4
	    "FIFTY", // 5
	    "SIXTY", // 6
	    "SEVENTY", // 7
	    "EIGHTY", // 8
	    "NINETY" // 9
	};

	public static String doubleConvert(final double n) {
	    String pass = n + "";
	    StringTokenizer token = new StringTokenizer(pass, ".");
	    String first = token.nextToken();
	    String last = token.nextToken();
	    try {
	        pass = convert(Integer.parseInt(first))+" ";
	        pass=pass+"POINT";
	        for (int i = 0; i < last.length(); i++) {
	            String get=convert(Integer.parseInt(last.charAt(i)+""));
	            if(get.isEmpty()){
	            pass=pass+" "+"ZERO";
	            }else{
	            pass=pass+" "+get;    
	            }
	        }

	    } catch (NumberFormatException nf) {
	    }
	    return pass;
	}
	public static String convert(final int n) {
	    if (n < 0) {
	        return "MINUS " + convert(-n);
	    }

	    if (n < 20) {
	        return units[n];
	    }

	    if (n < 100) {
	        return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
	    }

	    if (n < 1000) {
	        return units[n / 100] + " HUNDRED " + ((n % 100 != 0) ? " " : "") + convert(n % 100);
	    }

	    if (n < 1000000) {
	        return convert(n / 1000) + " THOUSANDS " + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
	    }

	    if (n < 1000000000) {
	        return convert(n / 1000000) + " MILLION " + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
	    }

	    return convert(n / 1000000000) + " BILLION " + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
	 }

	
	
	public static Double truncateDecimal(Double x,int numberofDecimals) {
		if (x == null) {
	        return 0.00D; // Handle null input
	    }
	    
	    // Use String constructor to avoid floating-point imprecision
	    BigDecimal truncatedValue = new BigDecimal(String.valueOf(x))
	                                    .setScale(numberofDecimals, RoundingMode.FLOOR);

	    // Optional: Round to 2 decimal places (if required)
	    BigDecimal finalValue = truncatedValue.setScale(2, RoundingMode.CEILING);
	    
	    return finalValue.doubleValue();
	}
	
	public static double truncateDecimalNew(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public static boolean checkPassword(String password) {
		String temp=""+LocalDate.now();
		temp=temp.replaceAll("-", "");
		if(password.equals(temp))
			return true;
		else
			return false;
	}
	
	public static String getJsonString(Object obj) {
		if(defaultValues!=null && defaultValues.equals("1")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(obj);	
		} else {
			return "";
		}
	}
	
	public static String replaceTokens(String text, Map<String, String> replacements) {
		Pattern pattern = Pattern.compile("\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			String replacement = replacements.get(matcher.group(1));
			if (replacement != null) {
				matcher.appendReplacement(buffer, "");
				buffer.append(replacement);
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
	
    public static boolean isValidGSTNo(String str) {
        String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
 
        Pattern p = Pattern.compile(regex);
 
        if (str == null) {
            return false;
        }
        
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static boolean islgdcode(String str) {
        //String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
        String regex = "\\d+";
 
        Pattern p = Pattern.compile(regex);
 
        if (str == null) {
            return false;
        }
        
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static boolean isValidCINNo(String str) {
        String regex = "^([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static boolean isValidPANNo(String str) {
    	
    	// [A-Z]{5} - match five literals which can be A to Z
    	// [0-9]{4} - followed by 4 numbers 0 to 9
    	// [A-Z]{1} - followed by one literal which can A to Z
        String regex = "^([A-Z]{5}[0-9]{4}[A-Z]{1})$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static boolean isValidPassword(String str) {
//      String regex = "^(?=(.*[a-zA-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$&])).{6,14}$";
      Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,14}$");
      if (str == null) {
          return false;
      }
      Matcher m = p.matcher(str);
      return m.matches();
  }
    
    /**
	 * 
	 * @param a : from date
	 * @param b : to date
	 * @return
	 */
    
    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }
    
	public static Map<String, Long> getDateDifference(Date dt2, Date dt1) {
		Map<String, Long> map = new HashMap<String, Long>();
		Long diff = dt2.getTime() - dt1.getTime();
        map.put("milliseconds", diff);
        map.put("seconds", diff / 1000 % 60);
        map.put("minutes", diff / (60 * 1000) % 60);
        map.put("hours", diff / (60 * 60 * 1000));
        map.put("days", ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24)));
        
        map.put("Weeks", ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24 * 7)));
        map.put("Months", (long)((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24 * 30.41666666)));
        map.put("Years", ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24 * 365)));
        return map;
	}
	
	
	/*
	 * public static boolean validateFeatureAccess(String featureServiceUrl,
	 * List<MstFeatureBean> featureList) { Boolean validateFeatureServiceUrl =
	 * Boolean.FALSE; if(featureList!=null && featureList.size()>0) {
	 * 
	 * //featureList.stream().forEach(s->System.out.println("Feature Code"+s.
	 * getFeatureCode()+ "Feature Service - " + s.getFeatureServiceUrl() +" --- "
	 * +featureServiceUrl.trim()));
	 * 
	 * if(featureList.stream().anyMatch(feature -> feature.getStatus()==1 && ( (
	 * feature.getFeatureCode()!=null &&
	 * feature.getFeatureCode().trim().equalsIgnoreCase(featureServiceUrl.trim()))
	 * || ( feature.getFeatureServiceUrl()!=null &&
	 * feature.getFeatureServiceUrl().trim().equalsIgnoreCase(featureServiceUrl.trim
	 * ())) ) )) { validateFeatureServiceUrl = Boolean.TRUE; } } return
	 * validateFeatureServiceUrl; }
	 */
	private static final SecureRandom rnd = new SecureRandom();
	public static String getFourRandomNumberString() {
	    // It will generate 4 digit random Number.
	    // from 0 to 9999
		 int number = rnd.nextInt(10000); // nextInt(10000) generates numbers between 0 and 9999
	        // This will convert the number into a 4-character string, padding with leading zeros if necessary
	        return String.format("%04d", number);
	}

	public static boolean isValidKhasaraId(String str) {
	       String regex = "\\d+";
	        Pattern p = Pattern.compile(regex);
	        if (str == null) {
	            return false;
	        }
	        Matcher m = p.matcher(str);
	        return m.matches();
	 	}

	public static boolean isValidKhasaraTehId(String str) {
	       String regex = "\\d+";
	        Pattern p = Pattern.compile(regex);
	        if (str == null) {
	            return false;
	        }
	        Matcher m = p.matcher(str);
	        return m.matches();
	}

	public static boolean isValidKhasaraDistId(String str) {
	       String regex = "\\d+";
	       
	        Pattern p = Pattern.compile(regex);
	        if (str == null) {
	            return false;
	        }
	        Matcher m = p.matcher(str);
	        return m.matches();
	}

	public static boolean isValidAlphaNumbericStr(String str) {
	       
	       
	       String regex="^[ A-Za-z0-9_@./#&+-]*$" ;
	        Pattern p = Pattern.compile(regex);
	        if (str == null) {
	            return false;
	        }
	        Matcher m = p.matcher(str);
	        return m.matches();
	}
	
	public static boolean isValidPropertyId(String str) {
	       
	       
	       String regex="[a-zA-Z0-9//s]" ;
	        Pattern p = Pattern.compile(regex);
	        if (str == null) {
	            return false;
	        }
	        Matcher m = p.matcher(str);
	        return m.matches();
	}

	private static char[] password = "EF737CC29DAE7C80644A5B01544CBA61".toCharArray();
	private static String salt = "0123456789";
    private static byte iv[];
    static {
        try {             
            iv = getBytes("79994A6EF73DA76C");
        } catch (Exception e) {
           logger.error("error",e);
            
        }
    }
    
    public static String encryptAndEncode(String raw)
    {
        try
        {
            Cipher c = getCipher(1,salt);
            byte[] encryptedVal = c.doFinal(getBytes(raw));
            return new String(java.util.Base64.getEncoder().encodeToString(encryptedVal));
           // return new String(encryptedVal);
        }
        catch (Throwable t)
        {
            throw new RuntimeException(t);
        }
    }
  
    public static String decodeAndDecrypt(String encrypted) throws Exception
    {
        byte[] decodedValue = java.util.Base64.getDecoder().decode(encrypted);
        Cipher c = getCipher(2,salt);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }
  
    private static byte[] getBytes(String str) throws UnsupportedEncodingException
    {
        return str.getBytes("UTF-8");
    }
    
    private static Cipher getCipher(int mode,String salt)throws Exception
    {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(mode, generateKey(salt), new IvParameterSpec(iv));
        return c;
    }
  
  
    private static Key generateKey(String salt) throws Exception
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] saltb = getBytes(salt);
    
        KeySpec spec = new PBEKeySpec(password, saltb, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    public static String encodeBase64(String salt) 
    {
    	String BasicBase64format= java.util.Base64.getEncoder().encodeToString(salt.getBytes());
    	return BasicBase64format;
    }
	
}
