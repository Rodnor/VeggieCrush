package com.utils;

/**
 * Classe des bonus externes à l'application
 */
public class Bonus {

	private String nomJeu;
	private Boolean possedeBonus;

	/**
	 * Constructeur
	 */
	public Bonus() {

	}

	/**
	 * Constructeur
	 * 
	 * @param nomJeu
	 * @param possedeBonus
	 */
	public Bonus(String nomJeu, Boolean possedeBonus) {
		super();
		this.nomJeu = nomJeu;
		this.possedeBonus = possedeBonus;
	}

	/**
	 * @return the nomJeu
	 */
	public String getNomJeu() {
		return nomJeu;
	}

	/**
	 * @param nomJeu
	 *            the nomJeu to set
	 */
	public void setNomJeu(String nomJeu) {
		this.nomJeu = nomJeu;
	}

	/**
	 * @return the possedeBonus
	 */
	public Boolean getPossedeBonus() {
		return possedeBonus;
	}

	/**
	 * @param possedeBonus
	 *            the possedeBonus to set
	 */
	public void setPossedeBonus(Boolean possedeBonus) {
		this.possedeBonus = possedeBonus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bonus [nomJeu=" + nomJeu + ", possedeBonus=" + possedeBonus + "]";
	}

}
