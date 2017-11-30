package com.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Classe permettant de gérer un INVENTAIRE
 */

public class Inventaire {

	private String id_user;
	private int id_objet;
	private int qte;

	/**
	 * Constructeur
	 * 
	 * @param id_user
	 * @param id_objet
	 * @param quantite
	 */
	public Inventaire(String id_user, int id_objet, int quantite) {
		this.id_user = id_user;
		this.id_objet = id_objet;
		this.qte = quantite;
	}

	/**
	 * Constructeur par défaut
	 */
	public Inventaire() {

	}

	/**
	 * @return the id_user
	 */
	public String getId_user() {
		return id_user;
	}

	/**
	 * @param id_user
	 *            the id_user to set
	 */
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	/**
	 * @return the id_objet
	 */
	public int getId_objet() {
		return id_objet;
	}

	/**
	 * @param id_objet
	 *            the id_objet to set
	 */
	public void setId_objet(int id_objet) {
		this.id_objet = id_objet;
	}

	/**
	 * @return the qte
	 */
	public int getQte() {
		return qte;
	}

	/**
	 * @param qte
	 *            the qte to set
	 */
	public void setQte(int qte) {
		this.qte = qte;
	}

	/**
	 * Permet de construire un objet JSON avec tous les champs
	 * 
	 * @return JSONObject
	 */
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_user", this.id_user);
			json.put("id_objet", this.id_objet);
			json.put("qte", this.qte);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Inventaire [id_user=" + id_user + ", id_objet=" + id_objet + ", qte=" + qte + "]";
	}
}