package com.utils;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import com.dao.AccountDao;
import com.entitie.Account;

public final class Utils {

	// public static String salt = "3iLh3nalluX";
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
	private static String salt = applicationProperties.getString("bd.pass.salt");

		
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static Boolean testStringForJson (String string) {
		return (string!= null && !string.equals(""));
	}

	public static Boolean testDateNulleForTimstamp(Timestamp timestamp) {
		Timestamp reference = new Timestamp(0);
		if (reference.equals(timestamp)) {
			return true;
		} else {
			return false;
		}
	}

	public static String usernameExistDansUneAutreAppli(String username, String securePass) {
		String nomAppli = null;

		return nomAppli;

	}

	public static UUID generateUuid() {
		return UUID.randomUUID();
	}

	public static String get_SHA_512_SecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}

	public static String generateNewPassword(int size) {
		
	    String chars = "abcdefghijklmnopqrstuvwxyz!():-_ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
	    String pass = "";

	    for(int x=0;x<size;x++) {
	       int i = (int)Math.floor(Math.random() * 68); // Si tu supprimes des lettres tu diminues ce nb
	       pass += chars.charAt(i);
	    }
	    return pass;
	}
	
	public static Boolean modfierMotDePasse(String adresseMail){
		
		String pass = Utils.generateNewPassword(13);
		String securePass = get_SHA_512_SecurePassword(pass);
		
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountByMail(adresseMail);
		
		if (account == null){
			return false;
		}
		
		accountDao.updatePasswordById(account.getId(), securePass);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Bonjour ");
		stringBuilder.append(account.getUsername());
		stringBuilder.append(", </br></br>");
		stringBuilder.append("Une demande de génération de nouveau mot de passe vient d'être faite sur le jeu VeggieCrush. Vous le trouverez ci après : </br> </br>");
		stringBuilder.append(pass);
		stringBuilder.append("</br></br>Copiez-coller ce nouveau mot de passe lors de votre prochaine connexion.");
		stringBuilder.append("</br></br>Ceci est mail généré automatiquement par l'application VeggieCrush. Merci de ne pas y répondre !");
		
		System.out.println(stringBuilder.toString());
		
		try {
			SendMail.sendEmailSSL(adresseMail, "Nouveau mot de passe sur VeggieCrush",stringBuilder.toString());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}

// CREATE TABLE NOUVEAU_MDP ( ID INT  NOT NULL, FLAG VARCHAR(1) NOT NULL, CONSTRAINT PK_FLAG PRIMARY KEY (ID, FLAG));   
