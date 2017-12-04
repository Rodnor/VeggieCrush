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
import com.utils.MusicPlayer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class MainFrame {

	private JFrame frame;
	private static JTabbedPane onglet;
	private final static Logger logger = Logger.getLogger(MainFrame.class.getName());
	private static String UUID;
	private boolean craftMusicIsOn=false;
	private boolean gameMusicIsOn=true;
	private static boolean bonusBoomcraft=false;
	private static boolean bonusFarmVillage=false;
	private static boolean bonusHowob;

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
	public MainFrame(String UUID, boolean bonusBoomCraft, boolean bonusFarmVillage, boolean bonusHowob) {
		this.UUID = UUID;
		this.bonusBoomcraft = bonusBoomCraft;
		this.bonusFarmVillage = bonusFarmVillage;
		this.bonusHowob = bonusHowob;
		initialize();
	}
	
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
	    	    
	    Thread t = new Thread() {
			public void run() {
				while(true) {
					if(MusicPlayer.gameMusicIsMute) {
						MusicPlayer.stopGameMusic();
					}
					if(MusicPlayer.craftMusicIsMute) {
						MusicPlayer.stopCraftMusic();
					}
					if(onglet.getSelectedIndex() == 1) {
						if(gameMusicIsOn) {
							MusicPlayer.stopGameMusic();
							gameMusicIsOn = false;
							craftMusicIsOn=true;
						}

						if(craftMusicIsOn && !MusicPlayer.craftMusicIsMute) {
							MusicPlayer.playCraftMusic();
						}
					}
					if(onglet.getSelectedIndex() == 0) {
						if(craftMusicIsOn) {
							MusicPlayer.stopCraftMusic();
							craftMusicIsOn = false;
							gameMusicIsOn = true;
						}
						
						if(gameMusicIsOn && !MusicPlayer.gameMusicIsMute) {
							MusicPlayer.playGameMusic();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
	
	public static JTabbedPane getTabbedPane() {
		return onglet;
	}
	
	public static String getUUID() {
		return UUID;
	}
	
	public static boolean getBonusBoomCraft() {
		return bonusBoomcraft;
	}
	
	public static boolean getBonusFarmVillage() {
		return bonusFarmVillage;
	}
	
	public static boolean getBonusHowob() {
		return bonusHowob;
	}
}
