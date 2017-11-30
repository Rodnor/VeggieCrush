package com.entitie;

/**
 * Enumération permettant de gérer les trois types de omposant
 */
public enum TypeObjet {

	amelioration("amelioration"), potion("potion"), composant("composant");

	private String nom;

	/**
	 * Constructeur
	 * 
	 * @param nom
	 */
	private TypeObjet(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return this.nom;
	}

}
