package com.anuppur.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.anuppur.exception.DMSBusinessException;

public class SHAHashingUtil {

	public static StringBuffer encryptPassword(String password) throws DMSBusinessException {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString;
		} catch (NoSuchAlgorithmException e) {
			throw new DMSBusinessException("Exception Occured.", e);
		}
	}

	public static String generatePassword() {
		return generateSessionKey(8);
	}
	 private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static String generateSessionKey(int length) {
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int n = alphabet.length();

        // Use a StringBuilder for better performance
        StringBuilder result = new StringBuilder(length);

        // Reuse the single SecureRandom instance
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(SECURE_RANDOM.nextInt(n)));
        }

        return result.toString();
	}

}
