package com.asupprimer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONObject;
/**
 * @author Arpit Mandliya
 */
public class HttpURLConnectionExample {

	//private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		HttpURLConnectionExample http = new HttpURLConnectionExample();

		// Sending post request
		http.sendingPostRequest();

	}

	// HTTP Post request
	private void sendingPostRequest() throws Exception {

		String url = "http://howob.masi-henallux.be/api/auth/signin/";
		URL obj = new URL(url);
		System.out.println("MiPaAaa");

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type","application/json");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "test");
		jsonObject.put("password", "test");

		
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObject.toString());
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
			
		if (responseCode == HttpURLConnection.HTTP_OK) {
			System.out.println("nSending 'POST' request to URL : " + url);
			System.out.println("Post Data : " + jsonObject.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer response = new StringBuffer();

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();

			//printing result from response
			System.out.println(response.toString());
		} else {

		}
	}
}