package com.utils;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class GestionBonus {
	
	public GestionBonus(){
		
	}
	
	public static String HOWOB = "howob";
	public static String FARMVILLAGE = "farmvillage";
	public static String BOOMCRAFT = "boomcraft";

	
	public ArrayList<Bonus> recupererBonus(){
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		HttpClient httpClient = new HttpClient();
		JSONObject jsonObject = new JSONObject();
		Bonus bonus = new Bonus();
		
		jsonObject = httpClient.getHttpRequest("http://veggiecrush.masi-henallux.be/rest_server/api/bonus/"+FARMVILLAGE+"/numero");
		
		if(!jsonObject.isNull(FARMVILLAGE)) {
			try {
				bonus = new Bonus(FARMVILLAGE, jsonObject.getBoolean(FARMVILLAGE));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		
		jsonObject = httpClient.getHttpRequest("http://veggiecrush.masi-henallux.be/rest_server/api/bonus/"+HOWOB+"/numero");
		
		if(!jsonObject.isNull(HOWOB)) {
			try {
				bonus = new Bonus(HOWOB, jsonObject.getBoolean(HOWOB));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		
		jsonObject = httpClient.getHttpRequest("http://veggiecrush.masi-henallux.be/rest_server/api/bonus/"+BOOMCRAFT+"/numero");
		
		if(!jsonObject.isNull(BOOMCRAFT)) {
			try {
				bonus = new Bonus(BOOMCRAFT, jsonObject.getBoolean(BOOMCRAFT));
				listeBonus.add(bonus);
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		
		return listeBonus;
	}

}
