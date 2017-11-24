package com.utils;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public  class JsonReaderExample {
	private static final String TEST_HTTP = "http://artshared.fr/andev1/distribue/android/get_game.php?uid=UNIQUEID1";
	private static final String TEST_HTTPS  = "https://jsonplaceholder.typicode.com/posts/1";

	public static void main(String[] args) {
		try {
			HttpClient httpClient = new HttpClient();
			JSONObject jObject = httpClient.getJsonByHttp(TEST_HTTP);
			
			System.out.println(jObject);

			//System.out.println(jObject.get("iRock"));
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("###################### HTTPS ##########################");
		
		/* HttpsClient httpClient = new HttpsClient();
		JSONObject jObject = httpClient.getJsonByHttps(TEST_HTTPS);
		
		System.out.println(jObject);
		//System.out.println(jObject.get("iRock")); */
		
		try {
			HttpClient httpClient = new HttpClient();
			JSONObject jObject = httpClient.getJsonByHttp(TEST_HTTPS);
			
			System.out.println(jObject);

			//System.out.println(jObject.get("iRock"));
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
