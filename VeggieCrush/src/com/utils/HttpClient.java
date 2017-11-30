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
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRetour;
	}

	/**
	 * Permet de créer une requête HTTPS GET
	 * 
	 * @param url
	 * @return JSONObject
	 */
	public JSONObject getHttpsRequest(String urlHttps) {

		JSONObject jObject = new JSONObject();

		try {
			URL url = new URL(urlHttps);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jObject = new JSONObject(response.toString());
				System.out.println(" trouvé : " + jObject.toString());

			} else {
				System.out.println("GET request not worked");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jObject;

	}

	/**
	 * Permet de connaitre et verifier le certificat SSL
	 * 
	 * @param con
	 */
	private void printCert(HttpsURLConnection con) {
		if (con != null) {
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}