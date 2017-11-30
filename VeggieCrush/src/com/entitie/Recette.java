package com.entitie;

/**
 * Classe permettant de gérer une RECETTE
 */
public class Recette {
	private int idRecette;
	private int idObjet;
	private String nomRecette;
	private String type;
	private String description;
	private int qte1;
	private int qte2;
	private int qte3;
	private int qte4;

	/**
	 * Constructeur par défaut
	 */
	public Recette() {

	}

	/**
	 * Constructeur
	 * 
	 * @param idRecette
	 * @param idObjet
	 * @param nomRecette
	 * @param type
	 * @param description
	 * @param qte1
	 * @param qte2
	 * @param qte3
	 * @param qte4
	 */
	public Recette(int idRecette, int idObjet, String nomRecette, String type, String description, int qte1, int qte2,
			int qte3, int qte4) {
		this.idRecette = idRecette;
		this.idObjet = idObjet;
		this.nomRecette = nomRecette;
		this.type = type;
		this.description = description;
		this.qte1 = qte1;
		this.qte2 = qte2;
		this.qte3 = qte3;
		this.qte4 = qte4;
	}

	/**
	 * @return the idRecette
	 */
	public int getIdRecette() {
		return idRecette;
	}

	/**
	 * @param idRecette
	 *            the idRecette to set
	 */
	public void setIdRecette(int idRecette) {
		this.idRecette = idRecette;
	}

	/**
	 * @return the idObjet
	 */
	public int getIdObjet() {
		return idObjet;
	}

	/**
	 * @param idObjet
	 *            the idObjet to set
	 */
	public void setIdObjet(int idObjet) {
		this.idObjet = idObjet;
	}

	/**
	 * @return the nomRecette
	 */
	public String getNomRecette() {
		return nomRecette;
	}

	/**
	 * @param nomRecette
	 *            the nomRecette to set
	 */
	public void setNomRecette(String nomRecette) {
		this.nomRecette = nomRecette;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the qte1
	 */
	public int getQte1() {
		return qte1;
	}

	/**
	 * @param qte1
	 *            the qte1 to set
	 */
	public void setQte1(int qte1) {
		this.qte1 = qte1;
	}

	/**
	 * @return the qte2
	 */
	public int getQte2() {
		return qte2;
	}

	/**
	 * @param qte2
	 *            the qte2 to set
	 */
	public void setQte2(int qte2) {
		this.qte2 = qte2;
	}

	/**
	 * @return the qte3
	 */
	public int getQte3() {
		return qte3;
	}

	/**
	 * @param qte3
	 *            the qte3 to set
	 */
	public void setQte3(int qte3) {
		this.qte3 = qte3;
	}

	/**
	 * @return the qte4
	 */
	public int getQte4() {
		return qte4;
	}

	/**
	 * @param qte4
	 *            the qte4 to set
	 */
	public void setQte4(int qte4) {
		this.qte4 = qte4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Recette [idRecette=" + idRecette + ", idObjet=" + idObjet + ", nomRecette=" + nomRecette + ", type="
				+ type + ", description=" + description + ", qte1=" + qte1 + ", qte2=" + qte2 + ", qte3=" + qte3
				+ ", qte4=" + qte4 + "]";
	}
}
