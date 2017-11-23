package com.entitie;

public class Recette {
	private int idRecette;
	private int idObjet;
	private int idFaction;
	private Objet compo1;
	private Objet compo2;
	private Objet compo3;
	private Objet compo4;
	private int qte1;
	private int qte2;
	private int qte3;
	private int qte4;
	
	public int getIdRecette() {
		return idRecette;
	}
	public void setIdRecette(int idRecette) {
		this.idRecette = idRecette;
	}
	public int getIdObjet() {
		return idObjet;
	}
	public void setIdObjet(int idObjet) {
		this.idObjet = idObjet;
	}
	public int getIdFaction() {
		return idFaction;
	}
	public void setIdFaction(int idFaction) {
		this.idFaction = idFaction;
	}
	public Objet getCompo1() {
		return compo1;
	}
	public void setCompo1(Objet compo1) {
		this.compo1 = compo1;
	}
	public Objet getCompo2() {
		return compo2;
	}
	public void setCompo2(Objet compo2) {
		this.compo2 = compo2;
	}
	public Objet getCompo3() {
		return compo3;
	}
	public void setCompo3(Objet compo3) {
		this.compo3 = compo3;
	}
	public Objet getCompo4() {
		return compo4;
	}
	public void setCompo4(Objet compo4) {
		this.compo4 = compo4;
	}
	public int getQte1() {
		return qte1;
	}
	public void setQte1(int qte1) {
		this.qte1 = qte1;
	}
	public int getQte2() {
		return qte2;
	}
	public void setQte2(int qte2) {
		this.qte2 = qte2;
	}
	public int getQte3() {
		return qte3;
	}
	public void setQte3(int qte3) {
		this.qte3 = qte3;
	}
	public int getQte4() {
		return qte4;
	}
	public void setQte4(int qte4) {
		this.qte4 = qte4;
	}
	public Recette(int idRecette, int idObjet, int idFaction, Objet compo1, Objet compo2, Objet compo3, Objet compo4,
			int qte1, int qte2, int qte3, int qte4) {
		this.idRecette = idRecette;
		this.idObjet = idObjet;
		this.idFaction = idFaction;
		this.compo1 = compo1;
		this.compo2 = compo2;
		this.compo3 = compo3;
		this.compo4 = compo4;
		this.qte1 = qte1;
		this.qte2 = qte2;
		this.qte3 = qte3;
		this.qte4 = qte4;
	}
	
	public Recette (){
		
	}
	@Override
	public String toString() {
		return "Recette [idRecette=" + idRecette + ", idObjet=" + idObjet + ", idFaction=" + idFaction + ", compo1="
				+ compo1 + ", compo2=" + compo2 + ", compo3=" + compo3 + ", compo4=" + compo4 + ", qte1=" + qte1
				+ ", qte2=" + qte2 + ", qte3=" + qte3 + ", qte4=" + qte4 + "]";
	}
}
