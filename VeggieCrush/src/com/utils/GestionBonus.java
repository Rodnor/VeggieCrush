package com.utils;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Classe permettant la gestion des bonus externes à l'application
 */
public class GestionBonus {

	/**
	 * Constructeur
	 */
	public GestionBonus() {

	}

	// pour gérer les ressources
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	// Nom des 3 autres jeux
	public static String HOWOB = "howob";
	public static String FARMVILLAGE = "farmvillage";
	public static String BOOMCRAFT = "boomcraft";

	/**
	 * Permet de récupérer les bonus des autres jeux
	 * 
	 * @param uuid
	 * @return ArrayList<Bonus>
	 */
	public static ArrayList<Bonus> recupererBonus(String uuid) {
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		HttpClient httpClient = new HttpClient();
		JSONObject jsonObject = new JSONObject();
		Bonus bonus = new Bonus();

		// on récupére les bonus via notre API pour FARMVILLAGE
		jsonObject = httpClient.getHttpRequestWithToken(
				applicationProperties.getString("api.url.bonus") + FARMVILLAGE + "/" + uuid,
				applicationProperties.getString("api.token.secure"));

		if (!jsonObject.isNull(FARMVILLAGE)) {
			try {
				bonus = new Bonus(FARMVILLAGE, jsonObject.getBoolean(FARMVILLAGE));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// on récupére les bonus via notre API pour HOWOB

		jsonObject = httpClient.getHttpRequestWithToken(
				applicationProperties.getString("api.url.bonus") + HOWOB + "/" + uuid,
				applicationProperties.getString("api.token.secure"));

		if (!jsonObject.isNull(HOWOB)) {
			try {
				bonus = new Bonus(HOWOB, jsonObject.getBoolean(HOWOB));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// on récupére les bonus via notre API pour BOOMCRAFT

		jsonObject = httpClient.getHttpRequestWithToken(
				applicationProperties.getString("api.url.bonus") + BOOMCRAFT + "/" + uuid,
				applicationProperties.getString("api.token.secure"));

		if (!jsonObject.isNull(BOOMCRAFT)) {
			try {
				bonus = new Bonus(BOOMCRAFT, jsonObject.getBoolean(BOOMCRAFT));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return listeBonus;
	}

	/**
	 * Permet d'indiquer les bonus consommés chez les autres via notre API
	 * 
	 * @param uuid
	 * @param bonusHowob
	 * @param bonusFarmvillage
	 * @param bonusBoomcraft
	 */
	public static void notifierRecupererBonus(String uuid, Boolean bonusHowob, Boolean bonusFarmvillage,
			Boolean bonusBoomcraft) {
		JSONObject jsonObject = new JSONObject();
		HttpClient httpClient = new HttpClient();
		try {
			// pour chaque jeu
			jsonObject.put("uuid", uuid);
			jsonObject.put("howob", bonusHowob);
			jsonObject.put("farmvillage", bonusFarmvillage);
			jsonObject.put("boomcraft", bonusBoomcraft);
			httpClient.postRequestWithJsonParamAndToken(applicationProperties.getString("api.url.bonus.notifier"),
					jsonObject, applicationProperties.getString("api.token.secure"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
