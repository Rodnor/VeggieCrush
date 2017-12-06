package com.utils;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Classe permettant la gestion des bonus externes
 */
public class GestionBonus {

	/**
	 * Constructeur
	 */
	public GestionBonus() {

	}

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

		jsonObject = httpClient.getHttpRequest(
				"https://veggiecrush.masi-henallux.be/rest_server/api/bonus/" + FARMVILLAGE + "/" + uuid);

		if (!jsonObject.isNull(FARMVILLAGE)) {
			try {
				bonus = new Bonus(FARMVILLAGE, jsonObject.getBoolean(FARMVILLAGE));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		jsonObject = httpClient
				.getHttpRequest("https://veggiecrush.masi-henallux.be/rest_server/api/bonus/" + HOWOB + "/" + uuid);

		if (!jsonObject.isNull(HOWOB)) {
			try {
				bonus = new Bonus(HOWOB, jsonObject.getBoolean(HOWOB));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		jsonObject = httpClient
				.getHttpRequest("https://veggiecrush.masi-henallux.be/rest_server/api/bonus/" + BOOMCRAFT + "/" + uuid);

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
	
	public static void notifierRecupererBonus(String uuid, Boolean bonusHowob, Boolean bonusFarmvillage, Boolean bonusBoomcraft) {
		JSONObject jsonObject = new JSONObject();
		HttpClient httpClient = new HttpClient();
		try {
			jsonObject.put("uuid", uuid);
			jsonObject.put("howob", bonusHowob);
			jsonObject.put("farmvillage", bonusFarmvillage);
			jsonObject.put("boomcraft", bonusBoomcraft);
			httpClient.postRequestWithJsonParam("https://veggiecrush.masi-henallux.be/rest_server/api/bonus/notifier", jsonObject);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
		
}

