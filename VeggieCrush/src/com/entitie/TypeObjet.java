package com.entitie;

public enum TypeObjet {
	
	AMELIORATION("AMELIORATION"), POTION("POTION"), COMPOSANT("COMPOSANT") ;  
    
    private String nom ;  
     
    private TypeObjet(String nom) {  
        this.nom = nom ;  
   }  
     
    public String getNom() {  
        return  this.nom ;  
   }  

}
