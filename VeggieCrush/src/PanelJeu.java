import java.awt.Checkbox;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.dao.InventaireDao;
import com.entitie.Inventaire;
import com.utils.Bonus;
import com.utils.GestionBonus;
import com.utils.MusicPlayer;

import net.miginfocom.swing.MigLayout;


/**
 * Classe responsable de la création du panel comportant l'espace de jeu et le HUD.
 * 
 * @author Tristan
 *
 */
public class PanelJeu extends JPanel implements ActionListener {

	// Variables de classe
	private JButton b[][] = new JButton[9][9];
	private JPanel canvas;
	private JLabel nbHerbe1;
	private JLabel nbHerbe2;
	private JLabel nbHerbe3;
	private JLabel nbHerbe4;
	private int numClic = 0;
	private ImageIcon prev;
	private ImageIcon next;
	private JButton prevJButton;
	private BufferedImage iconPlante = null;
	private int nombreCoups = 25;
	private int nombreCoupsBonus = 0;
	private int tempsBonus = 0;
	private int tempsBase = 30;
	private int tempsTotal;
	private int scoreBonus = 0;
	private int herbe1Bonus = 0;
	private int herbe2Bonus = 0;
	private int herbe3Bonus = 0;
	private int herbe4Bonus = 0;
	private JLabel nbCoupsRestants;
	private JLabel lblTimer;
	private boolean gameRunning = false;
	private boolean canSwitch = false;
	private int row;
	private int col;
	private ArrayList<JButton> herbe1 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe2 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe3 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe4 = new ArrayList<JButton>();
	private JLabel lblScore;
	private int scoreTotal;
	private int pointDeBase = 20;
	private JButton btnJouer;
	private int multiplicateurBonus1 = 1;
	private int multiplicateurBonus2 = 1;
	private int multiplicateurBonus3 = 1;
	private int multiplicateurBonus4 = 1;
	private JToggleButton tglbtnMuteSound;
	//private static ResourceBundle applicationProperties = ResourceBundle.getBundle("jeu");
	private BufferedImage imgFond = null;
	private JButton btnRgles;
	private Checkbox boomCraft = new Checkbox("BoomCraft");
	private Checkbox farmVillage = new Checkbox("FarmVillage");
	private Checkbox howob = new Checkbox("HOWOB");
	private ArrayList<Bonus> listeBonus;
	private boolean premierJeu = true;

	/**
	 * Constructeur du panel.
	 */
	public PanelJeu() {

		System.out.println(MainFrame.getUUID());

		// En fonction des bonus reçus en provenance de la fenêtre des bonus, on attribue les bonus au jour pour la partie suivante
		attribuerBonus();

		tempsTotal = tempsBase + tempsBonus;

		// On charge l'image de background de la fenêtre
		try {
			imgFond = ImageIO.read(new File("images/foret.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setLayout(new MigLayout("", "[][][grow][][][]", "[][][grow][grow][grow][grow][grow][]"));

		lblTimer = new JLabel("Temps restant : " + String.valueOf(tempsTotal));
		lblTimer.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblTimer, "cell 0 0");

		lblScore = new JLabel("Score : " + String.valueOf(0 + scoreBonus));
		lblScore.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblScore, "cell 5 0");

		nbCoupsRestants = new JLabel("Nombre de coups restants : " + String.valueOf(nombreCoups + nombreCoupsBonus));
		nbCoupsRestants.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(nbCoupsRestants, "cell 0 1");

		canvas = new JPanel();
		canvas.setOpaque(false);
		add(canvas, "cell 0 2 3 5,alignx center,aligny center");
		canvas.setLayout(new GridLayout(9, 9, -25, -10));

		// Cette méthode permet de remplir l'aire de jeux de 9x9 cases avec l'icône vide pour initialiser le jeu
		fillEmptyCanvas();

		// On instancie les 4 images pour les 4 types d'herbes différentes et on les fait apparaître sur le HUD
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe1.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 3,alignx center,aligny center");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe2.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblX, "cell 4 3");

		nbHerbe1 = new JLabel(String.valueOf(0 + herbe1Bonus));
		nbHerbe1.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(nbHerbe1, "cell 5 3");
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 4,alignx center,aligny center");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe3.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("x");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblNewLabel, "cell 4 4");

		nbHerbe2 = new JLabel(String.valueOf(0 + herbe2Bonus));
		nbHerbe2.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(nbHerbe2, "cell 5 4");
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 5,alignx center,aligny center");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe4.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JLabel lblNewLabel_1 = new JLabel("x");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblNewLabel_1, "cell 4 5");

		nbHerbe3 = new JLabel(String.valueOf(0 + herbe3Bonus));
		nbHerbe3.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(nbHerbe3, "cell 5 5");
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 6,alignx center,aligny center");

		btnJouer = new JButton("Jouer !");
		btnJouer.addActionListener(this);

		JLabel lblNewLabel_2 = new JLabel("x");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(lblNewLabel_2, "cell 4 6");

		nbHerbe4 = new JLabel(String.valueOf(0 + herbe4Bonus));
		nbHerbe4.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		add(nbHerbe4, "cell 5 6");
		btnJouer.setActionCommand("jouer");
		add(btnJouer, "cell 1 7,alignx center,aligny bottom");

		// On gère ici la fonctionnalité de mute pour la musique du panel de jeu
		tglbtnMuteSound = new JToggleButton("Mute Sound");
		// Ajout d'un listener sur le toggle bouton pour savoir s'il est enfoncé ou non
		tglbtnMuteSound.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				// S'il est enfoncé et que le player de la musique du jeu a déjà été instancié, on mute la musique
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (MusicPlayer.loopPanelGame != null) {
						MusicPlayer.gameMusicIsMute = true;
					}
				// S'il est relaché et que le player de la musique du jeu a déjà été instancié, on enlève le mute 
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					if (MusicPlayer.loopPanelGame != null) {
						MusicPlayer.gameMusicIsMute = false;
					}
				}
			}
		});
		add(tglbtnMuteSound, "cell 0 7,alignx left,aligny bottom");

		btnRgles = new JButton("Règles");
		btnRgles.addActionListener(this);
		btnRgles.setActionCommand("regles");
		add(btnRgles, "cell 5 7,alignx right,aligny bottom");
	}

	/**
	 * Méthode lancée lors de l'activation d'un composant muni d'un listener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si le composant est un JButton
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			// Si le bouton est le bouton "règles"
			if (btn.getActionCommand().equals("regles")) {
				// On affiche l'instance de la fenêtre des règles
				ReglesFrame.getInstance();
			// Si le bouton est le bouton "jouer"
			} else if (btn.getActionCommand().equals("jouer")) {
				// Si ce n'est pas la première partie lancée par le joueur après l'ouverture de la fenêtre
				if (!premierJeu) {
					// On affiche la popup pour le choix des bonus
					genererPopupBonus();
				} else {
					premierJeu = false;
				}

				// On lance la méthode qui permet de remettre à zéro les éléments de l'interface (grille de jeu, HUD)
				resetElements();

				// On remplis la grille de jeu aléatoirement avec les 4 images d'herbe
				fillCanvas();

				// On met une herbe de couleur par coin de la grille
				fillCanvasCorners();

				// On démarre les thread qui permettent de monitorer l'état de la partie
				startThreads();

				// MusicPlayer.playMusic("sounds/game.wav");

				// On cache le bouton de jeu pour que l'utilisateur ne puisse pas lancer 2 parties en même temps
				btnJouer.setVisible(false);
			// C'est que l'on a appuyé sur un des 81 boutons de la grille de jeu
			} else {
				// S'il reste des coupts à jouer et que le jeu n'est pas stoppé
				if (nombreCoups > 0 && gameRunning) {
					// Si c'est le premier clic sur un bouton de la grille
					if (numClic == 0) {
						// On parcours la grille des boutons
						for (int row = 0; row < 9; row++) {
							for (int col = 0; col < 9; col++) {
								// On trouve le bouton sur lequel on a cliqué
								if (b[row][col] == e.getSource()) {
									// On stocke l'icône du bouton
									prev = (ImageIcon) b[row][col].getIcon();
									// On stocke la référence du bouton
									prevJButton = b[row][col];
									// On récupère la colonne et la ligne du bouton et on incrémente le numéro de clic
									this.col = col;
									this.row = row;
									numClic++;
								}
							}
						}
					// On clique sur le deuxième bouton de la grille, celui que l'on veut inverser avec le premier
					} else {
						// On parcourt l'ensemble de la grille
						for (int row = 0; row < 9; row++) {
							for (int col = 0; col < 9; col++) {
								//On trouve le bouton sur lequel on a cliqué
								if (b[row][col] == e.getSource()) {
									// Si le bouton est directement adjacent en haut ou bas ou droite ou gauche au bouton cliqué en premier, alors on peut les échanger
									if (row + 1 == this.row && col == this.col || row - 1 == this.row && col == this.col
											|| row == this.row && col + 1 == this.col
											|| row == this.row && col - 1 == this.col) {
										canSwitch = true;
									}

									// Si le bouton cliqué en 2ème est différent de celui cliqué en premier et qu'il est possible de les échanger
									if (b[row][col] != prevJButton && canSwitch) {
										// On lance le son de coupe d'herbe
										MusicPlayer.playSound();
										// On stocke l'icone du 2ème bouton
										next = (ImageIcon) b[row][col].getIcon();
										// On stocke le numéro de plante du 2ème bouton
										String tmp = b[row][col].getName();

										// On met l'icône du premier bouton ainsi que son nom sur le 2ème
										b[row][col].setIcon(prev);
										b[row][col].setName(prevJButton.getName());

										// On met l'icône du deuxième bouton ainsi que son nom sur le 1er
										prevJButton.setIcon(next);
										prevJButton.setName(tmp);

										// On décrémente le nombre de coups et on interdit d'intervertir 2 boutons supplémentaires
										nombreCoups--;
										canSwitch = false;
									}
								}
							}
						}
						// Lorsque les 2 clics ont été effectués, on repart à 0
						numClic = 0;
					}
					// On met à jour le nombre de coups restants
					nbCoupsRestants.setText("Nombre de coups restants : " + String.valueOf(nombreCoups));
				}
			}
		}
	}

	/**
	 * Méthode lançant les threads permettant de monitorer l'état de la partie.
	 */
	public void startThreads() {

		// Lorsque le jeu se lance, on interdit l'utilisation de la fenêtre de craft
		MainFrame.getTabbedPane().setEnabledAt(1, false);

		// On laisse une pause de 2 secondes entre le moment ou le joueur clique sur le bouton jouer et le moment où le jeu se lance pour qu'il puisse se préparer
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Le jeu est en cours
		gameRunning = true;

		// Ce thread anonyme gère le timer
		Thread t = new Thread() {
			public void run() {
				// Tant que le temps restant est supérieur à 0 et que le jeu est en cours
				while (tempsTotal >= 0 && gameRunning) {
					// On met à jour la valeur du timer
					lblTimer.setText("Temps restant : " + String.valueOf(tempsTotal));
					// On fait une pause de 1s et on décrémente le temps restant
					try {
						Thread.sleep(1000);
						tempsTotal--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Lorsqu'il ne reste plus de temps, on met à jour le label du timer, on indique que le jeu n'est plus en cours et on lance la procédure de fin de partie
					if (tempsTotal == 0) {
						lblTimer.setText("Temps restant : 0");
						gameRunning = false;
						finDePartie();
					}
				}
			}
		};
		t.start();

		// Ce thread anonyme gère le nombre de coups
		Thread t2 = new Thread() {
			public void run() {
				// Tant que le jeu est en cours, on regarde le nombre de coups restants
				while (gameRunning) {
					// S'il n'y a plus de coups à jouer, on arrête la partie et on lance la procédure de fin de partie
					if (nombreCoups == 0) {
						gameRunning = false;
						finDePartie();
					}
					// On regarde toutes les secondes
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t2.start();
	}

	/**
	 * Cette méthode remplit la grille de jeu de manière aléatoire.
	 */
	public void fillCanvas() {

		// On supprime les éléments de la grille correspondants à la partie d'avant
		canvas.removeAll();

		// Pour chaque case de la grille
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// On tire un entier aléatoirement entre 1 et 4 inclus
				int rand = (int) ((Math.random() * 4) + 1);
				// On récupère l'icône de plante associée
				try {
					iconPlante = ImageIO.read(new File("images/herbe" + String.valueOf(rand) + ".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				// On ajoute l'icône sur le bouton et on le paramètre pour qu'il soit paré pour le jeu
				b[i][j] = new JButton(new ImageIcon(iconPlante));
				b[i][j].setBorderPainted(false);
				b[i][j].setFocusPainted(false);
				b[i][j].setContentAreaFilled(false);
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand("click");
				b[i][j].setName(String.valueOf(rand));
				// On ajoute le bouton dans la grille
				canvas.add(b[i][j]);
			}
		}

		// On enlève les listeners sur les 4 boutons des 4 coins pour que le joueur ne puisse pas les intervertir
		b[0][0].removeActionListener(this);
		b[0][8].removeActionListener(this);
		b[8][0].removeActionListener(this);
		b[8][8].removeActionListener(this);
	}

	/**
	 * Méthode permettant d'afficher une herbe différente par coin de la grille.
	 */
	public void fillCanvasCorners() {
		// L'herbe 1 sera toujours située dans la 1ère case de la 1ère colonne
		try {
			b[0][0].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe1.jpg"))));
			b[0][0].setName("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// L'herbe 2 sera toujours située dans la 1ère case de la dernière colonne
		try {
			b[0][8].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe2.jpg"))));
			b[0][8].setName("2");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// L'herbe 3 sera toujours située dans la dernière case de la 1ère colonne
		try {
			b[8][0].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe3.jpg"))));
			b[8][0].setName("3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// L'herbe 4 sera toujours située dans la dernière case de la dernière colonne
		try {
			b[8][8].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe4.jpg"))));
			b[8][8].setName("4");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cette méthode sert à remplir la grille de jeu d'icônes vides lorsque le joueur arrive sur le panel de jeu au lancement de la fenêtre.
	 */
	public void fillEmptyCanvas() {
		// Pour chaque case de la grille
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// On récupère l'image de l'icône vide et on l'ajoute au bouton
				try {
					b[i][j] = new JButton(new ImageIcon(ImageIO.read(new File("images/vide.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				// On paramètre le bouton
				b[i][j].setBorderPainted(false);
				b[i][j].setFocusPainted(false);
				b[i][j].setContentAreaFilled(false);
				// On ajoute le bouton à la grille
				canvas.add(b[i][j]);
			}
		}
	}

	/**
	 * Méthode lancée à chaque fin de partie pour récupérer les résultats et remettre à zéro le jeu.
	 */
	public void finDePartie() {
		// On lance les procédures d'exploration de chemin à partir des 4 coins de la grille et l'on stocke dans les 4 listes associées la longueur de ceux-ci
		explorer(b, b[0][0], 0, 0, herbe1);
		explorer(b, b[0][8], 0, 8, herbe2);
		explorer(b, b[8][0], 8, 0, herbe3);
		explorer(b, b[8][8], 8, 8, herbe4);

		// On indique sur le HUD le nombre de plantes de chaque couleur récoltées qui correspond à la longueur des chemins récupérés au dessus
		nbHerbe1.setText(String.valueOf(herbe1.size()));
		nbHerbe2.setText(String.valueOf(herbe2.size()));
		nbHerbe3.setText(String.valueOf(herbe3.size()));
		nbHerbe4.setText(String.valueOf(herbe4.size()));

		// Si la longueur des listes est supérieur ou égale à 10, on multiplie par 2 le nombre de points engrangés pour la couleur de plante associée
		if (herbe1.size() >= 10) {
			multiplicateurBonus1 = 2;
		}

		if (herbe2.size() >= 10) {
			multiplicateurBonus2 = 2;
		}

		if (herbe3.size() >= 10) {
			multiplicateurBonus3 = 2;
		}

		if (herbe4.size() >= 10) {
			multiplicateurBonus4 = 2;
		}

		// On calcule le score total en fonction du score bonus obtenu par un bonus d'un autre jeu, de la taille des listes de plantes, de la valeur d'un point de base et des multiplicateurs associées à chaque couleur
		scoreTotal = scoreBonus + herbe1.size() * pointDeBase * multiplicateurBonus1
				+ herbe2.size() * pointDeBase * multiplicateurBonus2
				+ herbe3.size() * pointDeBase * multiplicateurBonus3
				+ herbe4.size() * pointDeBase * multiplicateurBonus4;

		// On affiche sur le HUD le score final
		lblScore.setText("Score : " + scoreTotal);

		// On envoie le nombre de plantes récoltées dans la base de donnée
		sendDatasToDatabase();

		// On autorise l'utilisateur à se rendre sur le panel de craft
		MainFrame.getTabbedPane().setEnabledAt(1, true);

		// On indique à l'utilisateur que la partie est terminée
		JOptionPane.showMessageDialog(null, "Fin de partie !");

		// On remet visible le bouton de jeu s'il veut relancer une partie
		btnJouer.setVisible(true);
	}

	/**
	 * Méthode récursive permettant de calculer le nombre de cases adjacentes. 
	 * @param graphe Matrice de JButton à analyser.
	 * @param bouton JButton à partir duquel on veut calculer le nombre de cases adjacentes.
	 * @param rowCurrentButton int représentant la ligne du JButton de départ.
	 * @param colCurrentButton int représentant la colonne du JButton de départ.
	 * @param liste ArrayList<JButton> permettant de stocker les cases adjacentes au JButton de départ.
	 */
	public void explorer(JButton graphe[][], JButton bouton, int rowCurrentButton, int colCurrentButton,
			ArrayList<JButton> liste) {
		
		// On ajoute le bouton de départ dans la liste d'adjacence
		liste.add(bouton);

		// On parcourt l'ensemble de la matrice de boutons
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				// On s'assure que le test que l'on va effectuer reste dans les bornes de la matrice
				if (row < 9 && row >= 0 && col >= 0 && col < 9) {
					// Si le bouton de la matrice est directement en haut ou en bas ou à droite ou à gauche du bouton courant
					if (row + 1 == rowCurrentButton && col == colCurrentButton
							|| row - 1 == rowCurrentButton && col == colCurrentButton
							|| row == rowCurrentButton && col + 1 == colCurrentButton
							|| row == rowCurrentButton && col - 1 == colCurrentButton) {
						// Si le bouton de la matrice n'est pas déjà dans la liste d'adjacence et que le type d'herbe est le même que celui du bouton courant
						if (!liste.contains(graphe[row][col]) && graphe[row][col].getName().equals(bouton.getName())) {
							// On lance la procédure d'exploration à partir de ce nouveau bouton
							explorer(graphe, graphe[row][col], row, col, liste);
						}
					}
				}
			}
		}
	}

	/**
	 * Méthode permettant de réinitialiser le HUD et la grille de jeu.
	 */
	public void resetElements() {
		// On remet à leur valeur d'origine le temps et le nombre de coups
		tempsTotal = tempsBase + tempsBonus;
		nombreCoups = 25 + nombreCoupsBonus;

		// On supprime les boutons de la grille
		canvas.removeAll();
		revalidate();
		repaint();

		// On remet les valeurs d'origine pour le HUD
		lblScore.setText("Score : " + (0 + scoreBonus));
		nbCoupsRestants.setText("Nombre de coups restants : " + String.valueOf(25 + nombreCoupsBonus));
		nbHerbe1.setText(String.valueOf(0 + herbe1Bonus));
		nbHerbe2.setText(String.valueOf(0 + herbe2Bonus));
		nbHerbe3.setText(String.valueOf(0 + herbe3Bonus));
		nbHerbe4.setText(String.valueOf(0 + herbe4Bonus));
		lblTimer.setText("Temps restant : " + String.valueOf(tempsTotal));

		// On vide les listes d'adjacence des différents types d'herbe
		herbe1.clear();
		herbe2.clear();
		herbe3.clear();
		herbe4.clear();

		// On remplit la grille de jeu avec les icônes de boutons vides
		fillEmptyCanvas();

		// On refait apparaître le bouton de jeu
		btnJouer.setVisible(true);
	}

	/**
	 * Méthode permettant d'envoyer les résultats de la partie à la base de données.
	 */
	public void sendDatasToDatabase() {
		// Ces 4 nombres représentent le nombre de plantes de chaque type que nous allons envoyer à la base
		int nb1;
		int nb2;
		int nb3;
		int nb4;

		// Si le score de la partie est en dessous de 500, aucune plante n'est envoyée à la base
		if (scoreTotal < 500) {
			nb1 = 0;
			nb2 = 0;
			nb3 = 0;
			nb4 = 0;
		// Si le score de la partie est en dessous de 1000, on envoie la moitié des plantes récoltées lors de celle-ci
		} else if (scoreTotal < 1000) {
			nb1 = herbe1.size() / 2;
			nb2 = herbe2.size() / 2;
			nb3 = herbe3.size() / 2;
			nb4 = herbe4.size() / 2;
		// Si le score est en dessous de 2000, on envoie la totalité des plantes récoltées lors de celle-ci
		} else if (scoreTotal < 2000) {
			nb1 = herbe1.size();
			nb2 = herbe2.size();
			nb3 = herbe3.size();
			nb4 = herbe4.size();
		// Si le score est supérieur ou égal à 2000, on envoie le double des plantes récoltées lors de celle-ci
		} else {
			nb1 = herbe1.size() * 2;
			nb2 = herbe2.size() * 2;
			nb3 = herbe3.size() * 2;
			nb4 = herbe4.size() * 2;
		}

		// On instancie un DAO inventaire qui permet d'interagir avec la table Inventaire de la base de données
		InventaireDao inventaireDao = new InventaireDao();

		// On créé un inventaire à insérer pour le joueur identifié qui contient le nombre de plantes 1 à envoyer, puis on déclenche la requête d'insertion en base
		Inventaire inventaireAInserer = new Inventaire(MainFrame.getUUID(), 1, nb1);
		inventaireDao.insertNewInventaire(inventaireAInserer);

		// On créé un inventaire à insérer pour le joueur identifié qui contient le nombre de plantes 2 à envoyer, puis on déclenche la requête d'insertion en base
		inventaireAInserer = new Inventaire(MainFrame.getUUID(), 2, nb2);
		inventaireDao.insertNewInventaire(inventaireAInserer);

		// On créé un inventaire à insérer pour le joueur identifié qui contient le nombre de plantes 3 à envoyer, puis on déclenche la requête d'insertion en base
		inventaireAInserer = new Inventaire(MainFrame.getUUID(), 3, nb3);
		inventaireDao.insertNewInventaire(inventaireAInserer);

		// On créé un inventaire à insérer pour le joueur identifié qui contient le nombre de plantes 4 à envoyer, puis on déclenche la requête d'insertion en base
		inventaireAInserer = new Inventaire(MainFrame.getUUID(), 4, nb4);
		inventaireDao.insertNewInventaire(inventaireAInserer);

	}

	/**
	 * Méthode permettant d'attribuer les bonus au joueur en fonction des éléments récupérés à la création de la fenêtre de jeu.
	 */
	public void attribuerBonus() {

		// On lance la méthode de notification vers les API des autres groupes pour indiquer qu'un joueur à récupéré tel bonus en fonction des choix de bonus qu'il a fait avant de lancer une partie.
		GestionBonus.notifierRecupererBonus(MainFrame.getUUID(), MainFrame.getBonusHowob(),
				MainFrame.getBonusFarmVillage(), MainFrame.getBonusBoomCraft());

		// On attribue le bonus de temps de +15sec si le joueur a choisi d'utiliser le bonus HOWOB. On remet la possession du bonus à faux.
		if (MainFrame.getBonusHowob()) {
			tempsBonus = 15;
			MainFrame.setBonusHowob(false);
		}

		// On attribue le bonus de score de +500 si le joueur a choisi d'utiliser le bonus FarmVillage. On remet la possession du bonus à faux.
		if (MainFrame.getBonusFarmVillage()) {
			scoreBonus = 500;
			MainFrame.setBonusFarmVillage(false);
		}

		// On attribue le bonus de nombre de coups de +10 si le joueur a choisi d'utiliser le bonus BoomCraft. On remet la possession du bonus à faux.
		if (MainFrame.getBonusBoomCraft()) {
			nombreCoupsBonus = 10;
			MainFrame.setBonusBoomCraft(false);
		}
	}

	/**
	 * Méthode permettant de générer une PopUp permettant au joueur de choisir un bonus au démarrage de la partie lorsqu'il a déjà effectué une partie au préalable.
	 */
	public void genererPopupBonus() {
		// On créé un panel auquel on ajoute 3 checkbox, une pour chaque bonus pouvant être récupéré
		JPanel panel = new JPanel();
		panel.add(boomCraft);
		panel.add(farmVillage);
		panel.add(howob);

		// On récupère la liste des bonus disponibles pour le joueur
		listeBonus = GestionBonus.recupererBonus(MainFrame.getUUID());

		// Pour chaque bonus de la liste
		for (Bonus bonus : listeBonus) {
			System.out.println(bonus.getNomJeu() + " : " + bonus.getPossedeBonus());
			
			// S'il existe un bonus
			if (bonus.getPossedeBonus()) {
				// Suivant le nom du jeu, on active la chackbox correspondante au bonus existant
				switch (bonus.getNomJeu()) {
				case "howob":
					howob.setEnabled(true);
					break;
				case "farmvillage":
					farmVillage.setEnabled(true);
					break;
				case "boomcraft":
					boomCraft.setEnabled(true);
					break;
				}
			// S'il n'existe pas de bonus
			} else {
				// Suivant le nom du jeu, on désactive la checkbox correspondante au bonus non existant
				switch (bonus.getNomJeu()) {
				case "howob":
					howob.setEnabled(false);
					break;
				case "farmvillage":
					farmVillage.setEnabled(false);
					break;
				case "boomcraft":
					boomCraft.setEnabled(false);
					break;
				}
			}
		}

		// On affiche la popup comprennant les informations récupérées auparavant
		int option = JOptionPane.showConfirmDialog(null, panel, "Voulez-vous utiliser un bonus ?",
				JOptionPane.YES_NO_OPTION);

		// Si l'utilisateur souhaite utiliser un bonus, on stocke les bonus choisis dans la fenêtre de jeu et on décoche les checkbox
		if (option == JOptionPane.YES_OPTION) {
			if (howob.getState() && howob.isEnabled()) {
				MainFrame.setBonusHowob(true);
				howob.setState(false);
			}
			if (farmVillage.getState() && farmVillage.isEnabled()) {
				MainFrame.setBonusFarmVillage(true);
				farmVillage.setState(false);
			}
			if (boomCraft.getState() && boomCraft.isEnabled()) {
				MainFrame.setBonusBoomCraft(true);
				boomCraft.setState(false);
			}

			// On vide la liste des bonus et on lance la méthode permettant d'attribuer les bonus au joueur pour la partie suivante
			listeBonus.clear();
			attribuerBonus();
		}
	}

	/**
	 * Méthode permettant d'afficher l'image de fond du panel de jeu.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(imgFond, 0, 0, null);
	}
}