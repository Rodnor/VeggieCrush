package com.utils;

public class Bonus {
	
	private String nomJeu ;
	private Boolean possedeBonus;
	
	public String getNomJeu() {
		return nomJeu;
	}
	public void setNomJeu(String nomJeu) {
		this.nomJeu = nomJeu;
	}
	public Boolean getPossedeBonus() {
		return possedeBonus;
	}
	public void setPossedeBonus(Boolean possedeBonus) {
		this.possedeBonus = possedeBonus;
	}
	
	public Bonus() {
	}
	public Bonus(String nomJeu, Boolean possedeBonus) {
		super();
		this.nomJeu = nomJeu;
		this.possedeBonus = possedeBonus;
	}
	
	@Override
	public String toString() {
		return "Bonus [nomJeu=" + nomJeu + ", possedeBonus=" + possedeBonus + "]";
	}
	
	
	
}
