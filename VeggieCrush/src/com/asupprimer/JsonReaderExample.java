package com.asupprimer;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.utils.HttpClient;


public  class JsonReaderExample {
	private static final String TEST_HOWOB = "http://veggiecrush.masi-henallux.be/rest_server/api/account/existing";

	
	public static void main(String[] args) {
		
		HttpClient httpClient = new HttpClient();
		JSONObject jRetour = new JSONObject();
		System.out.println("###############");
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", "michel");
			jsonObject.put("email", "parism@3il.fr");
			
			jRetour = httpClient.postRequestWithJsonParam(TEST_HOWOB, jsonObject);
			
			System.out.println(jRetour);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

}
