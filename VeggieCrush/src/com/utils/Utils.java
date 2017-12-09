package com.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.swing.JEditorPane;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dao.AccountDao;
import com.entitie.Account;

/**
 * Classe permettant de regrouper les méthodes Utils
 */
public final class Utils {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/**
	 * Permet de tester la nullité d'une chaine avant de l'inserer dans un JSON
	 * 
	 * @param string
	 * @return <code>false</code> si la chaine est vide, <code>true</code> sinon
	 */
	public static Boolean testStringForJson(String string) {
		return (string != null && !string.equals(""));
	}

	/**
	 * Permet de verifier si le compte existe dans un autre jeu. Retourne
	 * <code>null</code> si le compte n'existe pas
	 * 
	 * @param username
	 * @param securePass
	 * @return uuid
	 */
	public static String signinDansUneAutreAppli(String username, String password) {
		String uuidTrouve = null;

		HttpClient httpClient = new HttpClient();
		JSONObject jsonEnvoi = new JSONObject();
		try {
			jsonEnvoi.put("username", username);
			jsonEnvoi.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject jsonRetour = httpClient.postRequestWithJsonParam(
				"https://veggiecrush.masi-henallux.be/rest_server/api/account/signinAutreJeu", jsonEnvoi);

		try {
			if (!jsonRetour.isNull("signin") && jsonRetour.get("signin").equals(true)
					&& !jsonRetour.isNull("uuid_ailleurs")) {
				uuidTrouve = jsonRetour.getString("uuid_ailleurs");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return uuidTrouve;
	}

	public static String creditsExistDansUneAutreAppli(String username, String mail) {
		String nomAppli = null;

		HttpClient httpClient = new HttpClient();
		JSONObject jsonEnvoi = new JSONObject();
		try {
			jsonEnvoi.put("username", username);
			jsonEnvoi.put("email", mail);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject jsonRetour = httpClient.postRequestWithJsonParam(
				"https://veggiecrush.masi-henallux.be/rest_server/api/account/existingAutreJeu", jsonEnvoi);

		try {
			if (!jsonRetour.get("existing").equals("null")) {
				nomAppli = jsonRetour.getString("existing");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return nomAppli;
	}

	/**
	 * Permet de générer l'uuid unique
	 * 
	 * @return uuid
	 */
	public static UUID generateUuid() {
		return UUID.randomUUID();
	}

	/**
	 * Permet de valider le format d'une adresse mail
	 * 
	 * @param emailStr
	 * @return
	 */
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	/**
	 * Permet de génerer un nouveau mot de passe alétoire
	 * 
	 * @param taille
	 * @return mot de passe
	 */
	public static String generateNewPassword(int taille) {

		String chars = "abcdefghijklmnopqrstuvwxyz!():-_ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String pass = "";

		for (int x = 0; x < taille; x++) {
			int i = (int) Math.floor(Math.random() * 68);
			pass += chars.charAt(i);
		}
		return pass;
	}

	/**
	 * Permet de modifier le mot de passe en base et de l'envoyer par mail à
	 * l'utilisateur
	 * 
	 * @param adresseMail
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public static Boolean modfierMotDePasse(String adresseMail) {

		String pass = Utils.generateNewPassword(13);

		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountByMail(adresseMail);

		if (account == null) {
			return false;
		}

		HttpClient httpClient = new HttpClient();
		JSONObject jsonEnvoi = new JSONObject();
		try {
			jsonEnvoi.put("id", String.valueOf(account.getId()));
			jsonEnvoi.put("password", pass);
		} catch (JSONException error) {
			error.printStackTrace();
		}

		httpClient.postRequestWithJsonParam(
				"https://veggiecrush.masi-henallux.be/rest_server/api/account/updatePassword", jsonEnvoi);
		accountDao.updateFlag(account.getId(), "O");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Bonjour ");
		stringBuilder.append(account.getUsername());
		stringBuilder.append(", </br></br>");
		stringBuilder.append(
				"Une demande de génération de nouveau mot de passe vient d'être faite sur le jeu VeggieCrush. Vous le trouverez ci après : </br> </br>");
		stringBuilder.append(pass);
		stringBuilder.append("</br></br>Copiez-coller ce nouveau mot de passe lors de votre prochaine connexion.");
		stringBuilder.append(
				"</br></br>Ceci est mail généré automatiquement par l'application VeggieCrush. Merci de ne pas y répondre !");

		System.out.println(stringBuilder.toString());

		try {
			SendMail.sendEmailSSL(adresseMail, "Nouveau mot de passe sur VeggieCrush", stringBuilder.toString());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public static String signinVeggie(String username, String pass) {
		JSONObject jsonRetour = new JSONObject();
		JSONObject jsonEnvoi = new JSONObject();

		try {
			jsonEnvoi.put("password", pass);
			jsonEnvoi.put("username", username);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpClient httpClient = new HttpClient();

		jsonRetour = httpClient.postRequestWithJsonParam(
				"https://veggiecrush.masi-henallux.be/rest_server/api/account/signin", jsonEnvoi);

		try {
			if (jsonRetour.isNull("error") && !jsonRetour.isNull("user")
					&& !jsonRetour.getJSONObject("user").isNull("globalId")) {

				return (String) jsonRetour.getJSONObject("user").get("globalId");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Boolean canConnectApi() {
		String addr = "veggiecrush.masi-henallux.be";
		int openPort = 443;
		int timeOutMillis = 1000;

		try {
			try (Socket soc = new Socket()) {
				soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
			}
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}