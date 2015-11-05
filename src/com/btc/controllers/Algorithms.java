package com.btc.controllers;

import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.soap.Text;

public class Algorithms {
	
	public static String key = "1234567812345678";
	static String passwordCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890,.;[]!@#$%^&*()_+-=<>?";
	public static String encryptedString(String str) {
		try {
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
	        byte[] encryptedBytes = cipher.doFinal(str.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedBytes);			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public static String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	public static String decryptString(String str) {
		try {
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
	        byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(str));

	        return new String(plainBytes);
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
    }
	
	public static String generateRandomString(int length) {
		StringBuilder builder = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(passwordCharacters.length());
			builder.append(passwordCharacters.charAt(idx));
		}
		return builder.toString();
	}
	
	public static String generateRandomString() {
		return generateRandomString(30);
	}
	private static Cipher getCipher(int cipherMode) throws Exception {
		String encryptionAlgorithm = "AES";
        SecretKeySpec keySpecification = new SecretKeySpec(
        		key.getBytes("UTF-8"), encryptionAlgorithm);
       
        
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);
     
        return cipher;
	}
	
	
}
