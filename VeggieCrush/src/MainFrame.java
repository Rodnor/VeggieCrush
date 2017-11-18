import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.dao.AccountDao;
import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.entitie.Account;
import com.entitie.Inventaire;
import com.entitie.Objet;
import com.test.Test;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class MainFrame {

	private JFrame frame;
	private JTabbedPane onglet;
	final static Logger logger = Logger.getLogger(MainFrame.class.getName());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
	    frame.setTitle("Jeu de merde");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(900, 750);

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
		}**/
	    
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
		System.out.println(accountDao.insertNewAccount(accountInsert)); // insertion retourne TRUE si OK ou false si KO 
		
		// meme principe que plus haut, je liste tous les account de la table pour verifier mon insetion. 
		// Je peux aussi verfier avec l'API (http://veggiecrush.masi-henallux.be:8080/rest_server/api/account/getAllAccounts)
		// liens utiles :
		/*
		 * 	http://veggiecrush.masi-henallux.be:8080/rest_server/api/inventaire/getAllInventaires
			http://veggiecrush.masi-henallux.be:8080/rest_server/api/objet/getAllObjets
			http://veggiecrush.masi-henallux.be:8080/rest_server/api/account/getAllAccounts
		 */
		ArrayList<Account> accounts = new ArrayList<Account>();
		logger.debug("MiPa avant appel DAO");
		accounts = accountDao.getAllAccounts();	
		
		for (Account account : accounts) {
			logger.debug(account.toString());
		}
		
		
	   JPanel[] tPan = {   new PanelJeu(), new PanelCraft(), new PanelDemandes(Color.BLUE)};

	    onglet = new JTabbedPane();
	    onglet.add("Jeu", tPan[0]);
	    onglet.add("Craft", tPan[1]);
	    onglet.add("Demandes", tPan[2]);


	    frame.getContentPane().add(onglet);

	    frame.setVisible(true);
	    frame.setResizable(false);
		frame.setBounds(100, 100, 900, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
