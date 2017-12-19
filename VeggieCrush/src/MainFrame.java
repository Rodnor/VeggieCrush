import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.utils.MusicPlayer;

/**
 * Classe responsable de la création de la fenêtre principale du jeu.
 * 
 * @authors Tristan & Kévin
 *
 */
public class MainFrame {

	private static JFrame frame;
	private static JTabbedPane onglet;
	private final static Logger logger = Logger.getLogger(MainFrame.class.getName());
	private static String UUID;
	private boolean craftMusicIsOn = false;
	private boolean gameMusicIsOn = true;
	private static boolean bonusBoomcraft = false;
	private static boolean bonusFarmVillage = false;
	private static boolean bonusHowob;

	/**
	 * Lanceur de l'application.
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
	 * Constructeur surchargé de la frame.
	 * 
	 * @param UUID
	 *            String contenant l'identifiant universel du joueur.
	 * @param bonusBoomCraft
	 *            Boolean indiquant la présence ou non d'un bonus en provenance
	 *            de BoomCraft.
	 * @param bonusFarmVillage
	 *            Boolean indiquant la présence ou non d'un bonus en provenance
	 *            de FarmVillage.
	 * @param bonusHowob
	 *            Boolean indiquant la présence ou non d'un bonus en provenance
	 *            de HOWOB.
	 */
	public MainFrame(String UUID, boolean bonusBoomCraft, boolean bonusFarmVillage, boolean bonusHowob) {
		this.UUID = UUID;
		this.bonusBoomcraft = bonusBoomCraft;
		this.bonusFarmVillage = bonusFarmVillage;
		this.bonusHowob = bonusHowob;
		initialize();
	}

	/**
	 * Constructeur de la frame.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 */
	private void initialize() {
		// Changement du Look and Feel original pour le Nimbus
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		// Paramétrages de la fenêtre
		frame = new JFrame();
		frame.setTitle("Jeu");

		// Tableau de JPanel pour différencier les onglets
		JPanel[] tPan = { new PanelJeu(), new PanelCraft() };

		// On créé les onglets et on leur assignes les JPanel appropriés
		onglet = new JTabbedPane();
		onglet.add("Jeu", tPan[0]);
		onglet.add("Craft", tPan[1]);

		frame.getContentPane().add(onglet);

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 900, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// Création d'un Thread permettant de détecter dans quel onglet le
		// joueur se situe pour adapter la musique en fonction
		Thread t = new Thread() {
			public void run() {
				while (true) {
					if (MusicPlayer.gameMusicIsMute) {
						MusicPlayer.stopGameMusic();
					}
					if (MusicPlayer.craftMusicIsMute) {
						MusicPlayer.stopCraftMusic();
					}
					if (onglet.getSelectedIndex() == 1) {
						if (gameMusicIsOn) {
							MusicPlayer.stopGameMusic();
							gameMusicIsOn = false;
							craftMusicIsOn = true;
						}

						if (craftMusicIsOn && !MusicPlayer.craftMusicIsMute) {
							MusicPlayer.playCraftMusic();
						}
					}
					if (onglet.getSelectedIndex() == 0) {
						if (craftMusicIsOn) {
							MusicPlayer.stopCraftMusic();
							craftMusicIsOn = false;
							gameMusicIsOn = true;
						}

						if (gameMusicIsOn && !MusicPlayer.gameMusicIsMute) {
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

		// Ajout d'un WindowListener pour fermer le Thread du panel de Craft
		// lorsque la fenêtre principale se ferme
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PanelCraft.setRun(false);
			}
		});
	}

	/**
	 * Getter des onglets de la fenêtre.
	 * 
	 * @return JTabbedPane.
	 */
	public static JTabbedPane getTabbedPane() {
		return onglet;
	}

	/**
	 * Getter de l'identifiant universel du joueur.
	 * 
	 * @return String.
	 */
	public static String getUUID() {
		return UUID;
	}

	/**
	 * Getter permettant de savoir si le joueur possède un bonus en provenance
	 * de BoomCraft.
	 * 
	 * @return Boolean.
	 */
	public static boolean getBonusBoomCraft() {
		return bonusBoomcraft;
	}

	/**
	 * Getter permettant de savoir si le joueur possède un bonus en provenance
	 * de FarmVillage.
	 * 
	 * @return Boolean.
	 */
	public static boolean getBonusFarmVillage() {
		return bonusFarmVillage;
	}

	/**
	 * Getter permettant de savoir si le joueur possède un bonus en provenance
	 * de HOWOB.
	 * 
	 * @return Boolean.
	 */
	public static boolean getBonusHowob() {
		return bonusHowob;
	}

	/**
	 * Setter de bonus pour HOWOB.
	 * 
	 * @param b
	 *            Boolean.
	 */
	public static void setBonusHowob(boolean b) {
		bonusHowob = b;
	}

	/**
	 * Setter de bonus pour FarmVillage.
	 * 
	 * @param b
	 *            Boolean.
	 */
	public static void setBonusFarmVillage(boolean b) {
		bonusFarmVillage = b;
	}

	/**
	 * Setter de bonus pour BoomCraft.
	 * 
	 * @param b
	 *            Boolean.
	 */
	public static void setBonusBoomCraft(boolean b) {
		bonusBoomcraft = b;
	}
}
