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
// MIPA COMMENTAIRE
// MIPA 2
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
	    
	    logger.info("Appel TEST_DEVV getUser");
		InventaireDao inventaireDao = new InventaireDao();
		
		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		logger.debug("MiPa avant appel DAO");
		inventaires = inventaireDao.getAllInventaires();	
		
		for (Inventaire inventaire : inventaires) {
			
			if (inventaire.getId_objet() == 1) {
				logger.debug("C'est une plante !");
			}
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
