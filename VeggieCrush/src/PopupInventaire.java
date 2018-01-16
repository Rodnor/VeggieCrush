import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.entitie.Inventaire;
import com.entitie.Objet;

public class PopupInventaire extends JFrame implements ActionListener {

	private static JPanel contentPane;
	private static JFrame frame = null;
	private static Thread t;
	private static boolean run = true;
	private static ArrayList<Inventaire> inv;
	private static ArrayList<String> listeobjetsinv;
	private static JLabel[] matrice_jlabel_objets;
	private static String[] string_listeobjets;
	private static BufferedImage iconPlante = null;
	private static BufferedImage iconVide = null;
	private static JScrollPane jscroll;
	private static JPanel btnPnl1;
	private static boolean affichagefenetre = true;
	private static ObjetDao objetdao = new ObjetDao();
	private static Objet objet;

	/**
	 * Create the frame.
	 */
	public static void initialize() {
		// on d�finie la taille de la fenetre...etc
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 500, 500);
		frame.setTitle("Inventaire");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				run = false;
			}
		});

		frame.setLayout(new BorderLayout());

		frame.setSize(600, 630);
		frame.setResizable(false);

		//initialisation de l'image vide
		try {
			iconVide = ImageIO.read(new File("images/vide.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		InventaireDao invdao = new InventaireDao();

		//cr�ation d'un thread pour pouvoir refresh l'inventaire toutes les  500ms
		t = new Thread() {
			public void run() {
				while (run) {
					//on reset tout
					objet = null;
					btnPnl1 = new JPanel();
					frame.getContentPane().removeAll();

					inv = new ArrayList<Inventaire>();
					//on r�cup�re tous les objets obtenus par le joueur
					inv = invdao.getInventaireByUuid(MainFrame.getUUID());
					int cpt = 0;
					//on calcule le nombre de ligne de 6 elements qui seont n�cessaire pour l'affichage total de tous les objets
					for (int i = 0; i < inv.size(); i++) {
						if (i % 6 == 0) {
							cpt++;
						}
					}

					btnPnl1.setLayout(new GridLayout(cpt, 10, 25, 25));
					btnPnl1.setSize(new Dimension(500, 500));

					listeobjetsinv = new ArrayList<String>();

					// on initialise le texte de toutes les cases a vide
					for (int index = 0; index < 6 * cpt; index++) {
						listeobjetsinv.add("");
					}

					// pour le nombre d'objet dispo dans l'inventaire on remplace le texte vide par la quantit� r�elle de l'objet contenu sur la case
					for (int index = 0; index < inv.size(); index++) {
						listeobjetsinv.set(index, "Qte : " + inv.get(index).getQte());
					}

					// on cr�� un tableau de string avec les diff�rentes quantit�s
					string_listeobjets = listeobjetsinv.toArray(new String[0]);

					//on cr�� une matrice de jlabel
					matrice_jlabel_objets = new JLabel[listeobjetsinv.size()];

					try {
						
						for (int i = 0; i < string_listeobjets.length; i++) {
							matrice_jlabel_objets[i] = new JLabel(string_listeobjets[i]);
							if (i < inv.size()) {
								//on parcours la liste seulement pour le nombre d'�elemnts dans l'inventaire (les autres elements garderont l'image d'emplacement vide)
								if (i < 4) {
									// on affiche en premier les 4 images pour les 4 plantes
									iconPlante = ImageIO
											.read(new File("images/herbe" + String.valueOf(i + 1) + ".jpg"));
									matrice_jlabel_objets[i].setIcon(new ImageIcon(iconPlante));
								} else {
									//ensuite on va r�cuperer les images pour les autres objets
									iconPlante = ImageIO.read(new File(
											"images/recette" + String.valueOf(inv.get(i).getId_objet() - 4) + ".png"));
									matrice_jlabel_objets[i].setIcon(new ImageIcon(iconPlante));
								}

							} else {
								//image vide par d�faut pour le surplus de cases
								matrice_jlabel_objets[i].setIcon(new ImageIcon(iconVide));
							}
							matrice_jlabel_objets[i].setVerticalTextPosition(JLabel.BOTTOM);
							matrice_jlabel_objets[i].setHorizontalTextPosition(JLabel.CENTER);
							btnPnl1.add(matrice_jlabel_objets[i]);

						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}

					// ajout du tout dans un jscrollpane pour permettre le scroll vers le bas quand trop d'�lements a afficher
					jscroll = new JScrollPane(btnPnl1);

					frame.getContentPane().add(jscroll, BorderLayout.CENTER);

					if (affichagefenetre) {
						frame.setVisible(true);
						affichagefenetre = false;
					}

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*if (!run) {

					t.interrupt();
				}*/
			}
		};
		t.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Méthode permettant de récupérer une unique instance de la frame.
	 * 
	 * @return JFrame contenant l'instance de la frame.
	 */
	public static JFrame getInstance() {
		if (frame == null) {
			initialize();
		}
		frame.setVisible(true);
		return frame;
	}
	
	/**
	 * Lanceur de l'application.
	 */ /*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupInventaire window = new PopupInventaire();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} */
	
	/**
	 * Constructeur de la frame.
	 */
	public PopupInventaire() {
		initialize();
	}

}
