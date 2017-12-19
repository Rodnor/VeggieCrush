import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import com.utils.Bonus;
import com.utils.GestionBonus;

import net.miginfocom.swing.MigLayout;

/**
 * Classe responsable de la création de la fenêtre permettant de choisir ses
 * bonus de jeu.
 * 
 * @author Tristan
 *
 */
public class BonusFrame implements ActionListener {

	// Variables de classe
	private JFrame frame;
	private static String UUID;
	private JPanel contentPane;
	private JLabel lblBonusHowob;
	private JLabel lblBonusBoomCraft;
	private JLabel lblBonusFarmVillage;
	private static boolean bonusHowob = false;
	private static boolean bonusBoomCraft = false;
	private static boolean bonusFarmVillage = false;
	private JButton btnValider;
	private ArrayList<Bonus> listeBonus;
	private boolean run = true;

	/**
	 * Lanceur de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BonusFrame window = new BonusFrame();
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
	 *            String contenant l'identifiant universel de l'utilisateur.
	 */
	public BonusFrame(String UUID) {
		BonusFrame.UUID = UUID;
		initialize();
	}

	/**
	 * Constructeur de la frame.
	 */
	public BonusFrame() {
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 */
	public void initialize() {
		// Paramétrages de la fenêtre
		frame = new JFrame("Bonus disponibles");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow,center][grow]", "[grow][grow][grow][grow][center]"));

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		JLabel lblNewLabel = new JLabel("Choisissez vos bonus :");
		contentPane.add(lblNewLabel, "cell 0 0");

		JToggleButton tglbtnBonusHowob = new JToggleButton("Bonus HOWOB");
		// Ajout d'un ItemListener sur le toggle bouton pour afficher ou cacher
		// le texte explicatif du bonus
		tglbtnBonusHowob.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					lblBonusHowob.setVisible(true);
					bonusHowob = true;
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					lblBonusHowob.setVisible(false);
					bonusHowob = false;
				}
			}
		});
		contentPane.add(tglbtnBonusHowob, "cell 0 1");

		lblBonusHowob = new JLabel("+15 sec au timer");
		lblBonusHowob.setVisible(false);
		contentPane.add(lblBonusHowob, "cell 1 1");

		JToggleButton tglbtnBonusFarmvillage = new JToggleButton("Bonus FarmVillage");
		// Ajout d'un ItemListener sur le toggle bouton pour afficher ou cacher
		// le texte explicatif du bonus
		tglbtnBonusFarmvillage.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					lblBonusFarmVillage.setVisible(true);
					bonusFarmVillage = true;
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					lblBonusFarmVillage.setVisible(false);
					bonusFarmVillage = false;
				}
			}
		});
		contentPane.add(tglbtnBonusFarmvillage, "cell 0 2");

		lblBonusFarmVillage = new JLabel("+500 pts de score");
		lblBonusFarmVillage.setVisible(false);
		contentPane.add(lblBonusFarmVillage, "cell 1 2");

		JToggleButton tglbtnBonusBoomCraft = new JToggleButton("Bonus BoomCraft");
		// Ajout d'un ItemListener sur le toggle bouton pour afficher ou cacher
		// le texte explicatif du bonus
		tglbtnBonusBoomCraft.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					lblBonusBoomCraft.setVisible(true);
					bonusBoomCraft = true;
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					lblBonusBoomCraft.setVisible(false);
					bonusBoomCraft = false;
				}
			}
		});
		contentPane.add(tglbtnBonusBoomCraft, "cell 0 3");

		lblBonusBoomCraft = new JLabel("+10 coups");
		lblBonusBoomCraft.setVisible(false);
		contentPane.add(lblBonusBoomCraft, "cell 1 3");

		btnValider = new JButton("Valider");
		btnValider.addActionListener(this);
		btnValider.setActionCommand("valider");
		contentPane.add(btnValider, "cell 0 4");

		// Thread permettant de regarder si des bonus existent en provenance des
		// 3 autres jeux et de rafraîchir l'affichage en conséquence
		Thread t = new Thread() {
			public void run() {
				while (run) {
					// On récupère la liste des bonus disponibles pour
					// l'utilisateur
					listeBonus = GestionBonus.recupererBonus(getUUID());

					// Pour chaque jeu
					for (Bonus bonus : listeBonus) {
						// S'il y a un bonus pour le jeu
						if (bonus.getPossedeBonus()) {
							// Suivant le nom du jeu, on autorise le clique sur
							// le bouton correspondant
							switch (bonus.getNomJeu()) {
							case "howob":
								tglbtnBonusHowob.setEnabled(true);
								break;
							case "farmvillage":
								tglbtnBonusFarmvillage.setEnabled(true);
								break;
							case "boomcraft":
								tglbtnBonusBoomCraft.setEnabled(true);
								break;
							}
							// S'il n'y a pas de bonus pour le jeu
						} else {
							// Suivant le nom du jeu, on interdit le clic sur le
							// bouton correspondant, on cache l'explication du
							// bonus et on désactive les booléens des bonus
							switch (bonus.getNomJeu()) {
							case "howob":
								bonusHowob = false;
								tglbtnBonusHowob.setEnabled(false);
								lblBonusHowob.setVisible(false);
								break;
							case "farmvillage":
								bonusFarmVillage = false;
								tglbtnBonusFarmvillage.setEnabled(false);
								lblBonusFarmVillage.setVisible(false);
								break;
							case "boomcraft":
								bonusBoomCraft = false;
								tglbtnBonusBoomCraft.setEnabled(false);
								lblBonusBoomCraft.setVisible(false);
								break;
							}
						}
					}

					// On vide la liste des bonus
					listeBonus.clear();
					try {
						// On rafraîchit toutes les 5 secondes
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}

	/**
	 * Méthode lancée lors de l'activation d'un composant muni d'un listener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si le composant est un JButton
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			// Si le bouton est le bouton "valider"
			if (btn.getActionCommand().equals("valider")) {
				// On lance la fenêtre principale avec l'identifiant universel
				// du joueur et les bonus qu'il a choisit d'utiliser, on coupe
				// le Thread et on ferme la fenêtre
				new MainFrame(UUID, bonusBoomCraft, bonusFarmVillage, bonusHowob);
				this.run = false;
				this.frame.dispose();
			}
		}
	}

	/**
	 * Getter permettant de retourner l'identifiant universel du joueur.
	 * 
	 * @return String.
	 */
	public static String getUUID() {
		return BonusFrame.UUID;
	}
}