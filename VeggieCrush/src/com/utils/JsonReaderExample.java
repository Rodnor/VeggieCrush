package com.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.entitie.Account;

public class JsonReaderExample {

	public static void main(String[] args) {

		Account test1 = new Account(1, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal1", // id_global avec le protocole de BEN
				"user1", //string
				"mail1", // String
				"pass1", // mot de pass TODO voir pour le cryptage
				1,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		
		Account test2 = new Account(2, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal2", // id_global avec le protocole de BEN
				"user2", //string
				"mail2", // String
				"pass2", // mot de pass TODO voir pour le cryptage
				2,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		
		Account test3 = new Account(3, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal3", // id_global avec le protocole de BEN
				"user3", //string
				"mail3", // String
				"pass3", // mot de pass TODO voir pour le cryptage
				2,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		try {
			JSONObject jObject  = new JSONObject();
			jObject.put(String.valueOf(test1.getId()), test1.getJson());
			jObject.put(String.valueOf(test2.getId()), test2.getJson());
			jObject.put(String.valueOf(test3.getId()), test3.getJson());
			
			JSONArray jArray = new JSONArray();
			
			jArray.put(test1.getJson());
			jArray.put(test2.getJson());
			
			System.out.println("YOP"+jArray.toString());
			System.out.println(jObject.length());
			System.out.println(jObject);
			JSONObject jso = new JSONObject();
			for (int i = 1; i <= jObject.length(); i++) {
				jso = (JSONObject) jObject.get(String.valueOf(i));
				System.out.println(jso.get("password"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
