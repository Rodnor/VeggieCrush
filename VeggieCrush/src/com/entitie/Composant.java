package com.entitie;

public enum Composant { // TODO
	
	PLANTE_1("PLANTE_1"), PLANTE_2("PLANTE_2"), PLANTE_3("PLANTE_3"), PLANTE_4("PLANTE_4");
    
    private String nom ;  
     
    private Composant(String nom) {  
        this.nom = nom ;  
   }  
     
    public String getNom() {  
        return  this.nom ;  
   }  

}
