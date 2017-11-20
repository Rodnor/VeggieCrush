package com.entitie;

public enum TypeObjet {
	
	amelioration("AMELIORATION"), potion("POTION"), composant("COMPOSANT") ;  
    
    private String nom ;  
     
    private TypeObjet(String nom) {  
        this.nom = nom ;  
   }  
     
    public String getNom() {  
        return  this.nom ;  
   }  

}
