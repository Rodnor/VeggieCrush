package com.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Classe permettant de gérer les appels HTTP(S) sur l'API VeggieCrush
 */
public final class HttpClient {

	private static final Logger logger = Logger.getLogger(HttpClient.class.getName());

	/**
	 * Permet de créer une requête HTTP GET
	 * 
	 * @param url
	 * @return JSONObject
	 */
	public JSONObject getHttpRequest(String url) {
		JSONObject jsonRetour = new JSONObject();

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jsonRetour = new JSONObject(response.toString());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonRetour;
	}
	
	

	/**
	 * Permet de créer une requête HTTP POST avec des paramètre JSON dans le
	 * body
	 * 
	 * @param url
	 * @return JSONObject
	 */
	public JSONObject postRequestWithJsonParam(String url, JSONObject jsonEnvoi) {

		JSONObject jsonRetour = new JSONObject();

		try {
			HttpURLConnection con;

			URL url_call = new URL(url);
			con = (HttpURLConnection) url_call.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");

			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonEnvoi.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();

				while ((output = in.readLine()) != null) {
					response.append(output);
				}
				in.close();

				jsonRetour = new JSONObject(response.toString());
				System.out.println(jsonRetour);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRetour;
	}
	
	/**
	 * Permet de créer une requête HTTP GET
	 * 
	 * @param url
	 * @return JSONObject
	 */
	public JSONObject getHttpRequestWithToken(String url, String token) {
		JSONObject jsonRetour = new JSONObject();

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty ("Authorization", token);
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jsonRetour = new JSONObject(response.toString());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonRetour;
	}
	
	

	/**
	 * Permet de créer une requête HTTP POST avec des paramètre JSON dans le
	 * body
	 * 
	 * @param url
	 * @return JSONObject
	 */
	public JSONObject postRequestWithJsonParamAndToken(String url, JSONObject jsonEnvoi, String token) {

		JSONObject jsonRetour = new JSONObject();

		try {
			HttpURLConnection con;

			URL url_call = new URL(url);
			con = (HttpURLConnection) url_call.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty ("Authorization", token);

			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonEnvoi.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();

				while ((output = in.readLine()) != null) {
					response.append(output);
				}
				in.close();

				jsonRetour = new JSONObject(response.toString());
				System.out.println(jsonRetour);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRetour;
	}
}