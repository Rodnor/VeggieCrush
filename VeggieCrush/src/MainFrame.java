import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

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
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (UnsupportedLookAndFeelException e) {} 
		catch (ClassNotFoundException e) {} 
		catch (InstantiationException e) {} 
		catch (IllegalAccessException e) {}


		frame = new JFrame();
	    frame.setTitle("Jeu");
	    
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
	    frame.setLocationRelativeTo(null);
	    
	    frame.getContentPane().setForeground(Color.RED);
	}
}
