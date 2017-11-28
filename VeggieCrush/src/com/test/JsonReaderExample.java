package com.test;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.utils.HttpClient;


public  class JsonReaderExample {
	private static final String TEST_HTTP = "http://artshared.fr/andev1/distribue/android/get_game.php?uid=UNIQUEID1";
	private static final String TEST_HOWOB = "http://howob.masi-henallux.be/api/auth/signin/";

	
	public static void main(String[] args) {
		
		HttpClient httpClient = new HttpClient();
		JSONObject jRetour = httpClient.getHttpRequest(TEST_HTTP);
		
		
		System.out.println(jRetour);
		jRetour = null;
		
		System.out.println("###############");
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", "test");
			jsonObject.put("password", "test");
			
			jRetour = httpClient.postRequestWithJsonParam(TEST_HOWOB, jsonObject);
			
			System.out.println(jRetour);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

}
