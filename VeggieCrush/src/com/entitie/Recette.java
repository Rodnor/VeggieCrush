package com.entitie;

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
	
	
	public String getNomRecette() {
		return nomRecette;
	}
	public void setNomRecette(String nomRecette) {
		this.nomRecette = nomRecette;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Recette(int idRecette, int idObjet, int idFaction, String nomRecette, String type, String description,
			int qte1, int qte2, int qte3, int qte4) {
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
	@Override
	public String toString() {
		return "Recette [idRecette=" + idRecette + ", idObjet=" + idObjet + ", nomRecette="
				+ nomRecette + ", type=" + type + ", description=" + description + ", qte1=" + qte1 + ", qte2=" + qte2
				+ ", qte3=" + qte3 + ", qte4=" + qte4 + "]";
	}
	public Recette (){
		
	}
}
