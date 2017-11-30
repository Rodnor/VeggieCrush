package com.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Classe permettant de gérer un OBJET
 */
public class Objet {

	private int id_objet;
	private String nom_objet;
	private TypeObjet type_objet;
	private int puissance_objet;

	/**
	 * Constructeur par défaut
	 */
	public Objet() {

	}

	/**
	 * Constructeur
	 * 
	 * @param id_objet
	 * @param nom_objet
	 * @param type_objet
	 */
	public Objet(int id_objet, String nom_objet, TypeObjet type_objet, int puissance_objet) {
		this.id_objet = id_objet;
		this.nom_objet = nom_objet;
		this.type_objet = type_objet;
		this.puissance_objet = puissance_objet;
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
	 * @return the nom_objet
	 */
	public String getNom_objet() {
		return nom_objet;
	}

	/**
	 * @param nom_objet
	 *            the nom_objet to set
	 */
	public void setNom_objet(String nom_objet) {
		this.nom_objet = nom_objet;
	}

	/**
	 * @return the type_objet
	 */
	public TypeObjet getType_objet() {
		return type_objet;
	}

	/**
	 * @param type_objet
	 *            the type_objet to set
	 */
	public void setType_objet(TypeObjet type_objet) {
		this.type_objet = type_objet;
	}

	/**
	 * @return the puissance_objet
	 */
	public int getPuissance_objet() {
		return puissance_objet;
	}

	/**
	 * @param puissance_objet
	 *            the puissance_objet to set
	 */
	public void setPuissance_objet(int puissance_objet) {
		this.puissance_objet = puissance_objet;
	}

	/**
	 * Permet de construire un objet JSON avec tous les champs
	 * 
	 * @return JSONObject
	 */
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_objet", this.id_objet);
			json.put("nom_objet", this.nom_objet);
			json.put("type_objet", this.type_objet.getNom());
			json.put("puissance", this.puissance_objet);
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
		return "Objet [id_objet=" + id_objet + ", nom_objet=" + nom_objet + ", type_objet=" + type_objet
				+ ", puissance_objet=" + puissance_objet + "]";
	}

}
