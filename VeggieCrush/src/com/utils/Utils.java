package com.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.UUID;

public final class Utils {
	
	//public static String salt = "3iLh3nalluX";
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
	private static String salt = applicationProperties.getString("bd.pass.salt");
	
	public static Boolean testStringForJson (String string) {
		return (string!= null && !string.equals(""));
	}
	
	public static Boolean testDateNulleForTimstamp (Timestamp timestamp) {
		Timestamp reference = new Timestamp(0);
		if (reference.equals(timestamp)) {
			return true;
		} else {
			return false;
		}
	}

	public static String usernameExistDansUneAutreAppli(String username, String securePass){
		String nomAppli = null;
		
		return nomAppli;
		
	}

	
	public static UUID generateUuid (){
		 return UUID.randomUUID();
	}

	public static String get_SHA_512_SecurePassword(String passwordToHash){
	String generatedPassword = null;
	    try {
	         MessageDigest md = MessageDigest.getInstance("SHA-512");
	         md.update(salt.getBytes("UTF-8"));
	         byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         generatedPassword = sb.toString();
	        } 
	       catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	       } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return generatedPassword;
	}

}
