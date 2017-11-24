package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.utils.HttpClient;

public final class HttpClient {

	private static final Logger logger = Logger.getLogger(HttpClient.class.getName());
	
	public JSONObject getJsonByHttp (String url) throws IOException {
		URL obj = new URL(url);
		JSONObject jObject = new JSONObject();
		
		try {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			logger.info("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jObject = new JSONObject(response.toString());
				logger.debug(" trouvé : " + jObject.toString());

			} else {
				logger.error("GET request not worked");
			}
		} catch (ConnectException e) {
			// e.printStackTrace();
			logger.error("Erreur de connexion");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jObject;
	}
}