package com.test;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dao.AccountDao;
import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.dao.RecetteDao;
import com.entitie.Account;
import com.entitie.Inventaire;
import com.entitie.Objet;
import com.entitie.Recette;
import com.entitie.TypeObjet;
import com.utils.SendMail;
import com.utils.Utils;

public class Test {
	final static Logger logger = Logger.getLogger(Test.class.getName());


	public static void main(String[] args) {
	/*	
		Account test1 = new Account(1, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal1", // id_global avec le protocole de BEN
				"user1", //string
				"mail1", // String
				"pass1", // mot de pass TODO voir pour le cryptage
				1,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		
		Account test2 = new Account(2, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal2", // id_global avec le protocole de BEN
				"user2", //string
				"mail2", // String
				"pass2", // mot de pass TODO voir pour le cryptage
				2,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		
		Account test3 = new Account(3, // je mets un id = 0 car il est auto-incrémenté
				"id_globaaal3", // id_global avec le protocole de BEN
				"user3", //string
				"mail3", // String
				"pass3", // mot de pass TODO voir pour le cryptage
				2,  // id_faction 1 ou 2
				null, // la date de création est gérée par le DAO (date du jour)
				null,
				null);
		try {
			JSONObject jObject  = new JSONObject();
			jObject.put(String.valueOf(test1.getId()), test1.getJson());
			jObject.put(String.valueOf(test2.getId()), test2.getJson());
			jObject.put(String.valueOf(test3.getId()), test3.getJson());	
			
			System.out.println("YOP");
			System.out.println(jObject.length());
			System.out.println(jObject);
			JSONObject jso = new JSONObject();
			for (int i = 1; i <= jObject.length(); i++) {
				jso = (JSONObject) jObject.get(String.valueOf(i));
				System.out.println(jso.get("password"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}	*/
	 /*
		RecetteDao recetteDao = new RecetteDao();

		ArrayList<Recette> recettes = recetteDao.getRecettes();
		
		for (Recette rec : recettes) {
			System.out.println(rec.toString());
		}
		
		Recette recette = new Recette(1,1,1,"Nom de la recette JAVA du soir","W","Une recette pour la fête (JAVA)",1,0,2,0);
		
		
		System.out.println("APRES INSERTION");
		//recetteDao.insertNewrecette(recette);

		
		recettes = recetteDao.getRecettes();

		for (Recette rec : recettes) {
			System.out.println(rec.toString());
		}
		*/
		
		
		
		
		//System.out.println(Utils.generateNewPassword(13));

	}

}


/**
 * 
 * 		/*ObjetDao objetDao = new ObjetDao();
		ArrayList<Objet> objets = new ArrayList<Objet>();
		logger.debug("MiPa avant appel DAO");
		objets = objetDao.getAllObjets();	

		for (Objet objet : objets) {
			logger.debug(objet.toString());
		}
		
		//Objet objetAinserer = new Objet(0, "Nouvel Objet de Java", TypeObjet.amelioration);
		//objetDao.insertNewObjet(objetAinserer);
		
		//System.out.println("plante1 : "+objetDao.getNbObjetByIdAccountAndByIdObjet(1, 1));
		//System.out.println("plante2 : "+objetDao.getNbObjetByIdAccountAndByIdObjet(1, 2));
		//System.out.println("plante3 : "+objetDao.getNbObjetByIdAccountAndByIdObjet(1, 3));
		System.out.println("plante4 : "+objetDao.getNbObjetByIdAccountAndByIdObjet(1, 1));
		
		/**
		InventaireDao inventaireDao = new InventaireDao(); // je crée un inventaireDao pour acceder à la table 

		Inventaire inventaireAInserer = new Inventaire(1, 1, 4);
		//inventaireDao.insertNewInventaire(inventaireAInserer);
		
		

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>(); // je prépare ma liste de retour
		inventaires = inventaireDao.getInventaireByIdAccount(1); // je recupere tous les objets de la table inventaire avec un id_accoun =1

		for (Inventaire inventaire : inventaires) { // J'affiche les objets trouvés
				logger.debug("inv : "+inventaire.toString());
		}

		
		int nb = objetDao.getNbObjetByIdAccountAndByIdObjet(1, 1); //idAccount, idObjet
		String nom = objetDao.getObjetById(1).getNom_objet();
		logger.debug("MiPa:  il y a "+nb+" fois l'objet "+nom); 
		
		logger.debug("MiPa avant appel DAO");
		objets = objetDao.getAllObjets();	

		for (Objet objet : objets) {
			logger.debug(objet.toString());
		}
		
		logger.debug(objetDao.deleteObjetByIdObjet(10));
		
		logger.debug("MiPa avant appel DAO");
		objets = objetDao.getAllObjets();	

		for (Objet objet : objets) {
			logger.debug(objet.toString());
		} **/
		/*
 */

/**
logger.info("Appel TEST_DEVV getUser");
AccountDao accountDao = new AccountDao();

ArrayList<Account> accounts = new ArrayList<Account>();
logger.debug("MiPa avant appel DAO");
accounts = accountDao.getAllAccounts();	

for (Account account : accounts) {
	logger.debug(account.toString());
} 

logger.info("Appel TEST_DEVV getUser");
ObjetDao objetDao = new ObjetDao();

ArrayList<Objet> objets = new ArrayList<Objet>();
logger.debug("MiPa avant appel DAO");
objets = objetDao.getAllObjets();	

for (Objet objet : objets) {
	logger.debug(objet.toString());
}

InventaireDao inventaireDao = new InventaireDao(); // je crée un inventaireDao pour acceder à la table 

ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>(); // je prépare ma liste de retour
inventaires = inventaireDao.getInventaireByIdAccount(1); // je recupere tous les objets de la table inventaire avec un id_accoun =1

for (Inventaire inventaire : inventaires) { // J'affiche les objets trouvés
	if (inventaire.getId_objet() == 1) { // je teste si c'est un objet avec l'id 1
		logger.debug("C'est une plante avec l'id numéro "+inventaire.getId_objet());
	} else {
		logger.debug("Ce n'est pas une plante avec l'id numéro "+inventaire.getId_objet());

	}
}
		
inventaires = inventaireDao.getInventaireByIdAccountAndByIdObjet(1,1); // Cette fois, je récupére seulement les objets de la table inventaire qui appartienneent à l'utilisateur numéro 1 dont l'id de l'objet est 1
ObjetDao objetDao = new ObjetDao(); // je crée un objetDao pour récupérer les infos sur cette objet. On peut aussi faire une nouvelle requete avec un jointure 

Objet objet = new Objet(); // je crée un objet temporaire
for (Inventaire inventaire : inventaires) { // je parcours les objets retourné. Normalement il ne peut y en avoir qu'un seul mais je laisse une liste au cas ou
	objet = objetDao.getObjetById(1); // Je vais recuperer les infos dans la table objet pour l'objet numéro 1
	logger.debug("il y a "+inventaire.getQuantite()+objet.getNom_objet()+" pour l'utilisateur "+ inventaire.getId_user()); // Je peux utiliser les données des deux tables grâce aux deux objets
}

// Maintenant, place à l'insertion
// je prepare le account à inserer
Account accountInsert = new Account(0, // je mets un id = 0 car il est auto-incrémenté
		"id_globaaal", // id_global avec le protocole de BEN
		"user", //string
		"mail", // String
		"pass", // mot de pass TODO voir pour le cryptage
		1,  // id_faction 1 ou 2
		null, // la date de création est gérée par le DAO (date du jour)
		null,
		null);

AccountDao accountDao = new AccountDao();
//System.out.println(accountDao.insertNewAccount(accountInsert)); // insertion retourne TRUE si OK ou false si KO 

// meme principe que plus haut, je liste tous les account de la table pour verifier mon insetion. 
// Je peux aussi verfier avec l'API (http://veggiecrush.masi-henallux.be:8080/rest_server/api/account/getAllAccounts)
// liens utiles :
//
//	http://veggiecrush.masi-henallux.be:8080/rest_server/api/inventaire/getAllInventaires
//	http://veggiecrush.masi-henallux.be:8080/rest_server/api/objet/getAllObjets
//	http://veggiecrush.masi-henallux.be:8080/rest_server/api/account/getAllAccounts
 
ArrayList<Account> accounts = new ArrayList<Account>();
logger.debug("MiPa avant appel DAO");
accounts = accountDao.getAllAccounts();	

for (Account account : accounts) {
	logger.debug(account.toString());
}**/