package com.asupprimer;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.utils.HttpClient;
import com.utils.Utils;

public class TestS {

	public static void main(String[] args) {
	/*	HttpClient httpClient = new HttpClient();		
		System.out.println("On appelle");
		JSONObject json = new JSONObject();
		try {
			json = httpClient.getHttpRequest("https://veggiecrush.masi-henallux.be:8443/rest_server/api/test/testHTTP");

			System.out.println(json.toString());
			if (!json.isNull("ok") && json.get("ok").equals(true)){
				System.out.println("On a trouvé true");
				
			} else {
				System.out.println("On a PAS trouvé true");

			}
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		Utils.signinDansUneAutreAppli("test","test");
	}

}
