package com.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import com.dao.AccountDao;
import com.entitie.Account;

/**
 * Classe permettant de regrouper les méthodes Utils
 */
/**
 * @author michelparis
 *
 */
/**
 * @author michelparis
 *
 */
/**
 * @author michelparis
 *
 */
public final class Utils {

	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
	private static String salt = applicationProperties.getString("bd.pass.salt");	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Permet de tester la nullité d'une chaine avant de l'inserer dans un JSON
	 * @param string
	 * @return <code>false</code> si la chaine est vide, <code>true</code> sinon
	 */
	public static Boolean testStringForJson (String string) {
		return (string!= null && !string.equals(""));
	}

	/**
	 * Permet de verifier si le compte existe dans un autre jeu. Retourne <code>null</code> si le compte n'existe pas
	 * @param username
	 * @param securePass
	 * @return nom de l'appli
	 */
	public static String usernameExistDansUneAutreAppli(String username, String securePass) {
		String nomAppli = null;
		return nomAppli;

	}
	
	/**
	 * Permet de verifier si le mail existe dans un autre jeu. Retourne <code>null</code> si le compte n'existe pas
	 * @param username
	 * @param securePass
	 * @return nom de l'appli
	 */
	public static String mailExistDansUneAutreAppli(String mail, String securePass) {
		String nomAppli = null;

		return nomAppli;

	}

	
	/**
	 * Permet de générer l'uuid unique
	 * @return uuid
	 */
	public static UUID generateUuid() {
		return UUID.randomUUID();
	}

	/**
	 * Permet de crypter le mot de passe avec SHA-512 et la clef commune
	 * @param passwordToHash
	 * @return mot de passe
	 */
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
	
	/**
	 * Permet de valider le format d'une adresse mail 
	 * @param emailStr
	 * @return
	 */
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}

	/**
	 * Permet de génerer un nouveau mot de passe alétoire
	 * @param taille
	 * @return mot de passe
	 */
	public static String generateNewPassword(int taille) {
		
	    String chars = "abcdefghijklmnopqrstuvwxyz!():-_ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    String pass = "";

	    for(int x=0;x<taille;x++) {
	       int i = (int)Math.floor(Math.random() * 68);
	       pass += chars.charAt(i);
	    }
	    return pass;
	}
	
	/**
	 * Permet de modifier le mot de passe en base et de l'envoyer par mail à l'utilisateur
	 * @param adresseMail
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public static Boolean modfierMotDePasse(String adresseMail){
		
		String pass = Utils.generateNewPassword(13);
		String securePass = get_SHA_512_SecurePassword(pass);
		
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountByMail(adresseMail);
		
		if (account == null){
			return false;
		}
		
		accountDao.updatePasswordById(account.getId(), securePass);
		accountDao.updateFlag(account.getId(), "O");
		
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