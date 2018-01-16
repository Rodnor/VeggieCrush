import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.text.ParseException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.dao.RecetteDao;
import com.entitie.Inventaire;
import com.entitie.Recette;
import com.utils.MusicPlayer;

import net.miginfocom.swing.MigLayout;

public class PanelCraft extends JPanel implements ActionListener {
	private Color color = Color.white;
	private String message = "";
	private InventaireDao inventairedao = new InventaireDao();
	private ObjetDao objetDao = new ObjetDao();
	private RecetteDao recettedao = new RecetteDao();
	private ArrayList<Recette> listeAllRecette = new ArrayList<Recette>();
	private ArrayList<Recette> listeRecetteHOWOB = new ArrayList<Recette>();
	private ArrayList<Recette> listeRecetteFARMVILLAGE = new ArrayList<Recette>();
	private ArrayList<Recette> listeRecetteBOOMCRAFT = new ArrayList<Recette>();
	private BufferedImage iconPlante1 = null;
	private BufferedImage iconPlante2 = null;
	private BufferedImage iconPlante3 = null;
	private BufferedImage iconPlante4 = null;
	private BufferedImage imgFond = null;
	private BufferedImage iconInventaire = null;
	private BufferedImage iconCraft = null;
	private JLabel lblPlante1Recette;
	private JLabel lblPlante2Recette;
	private JLabel lblPlante3Recette;
	private JLabel lblPlante4Recette;
	private JLabel titre_recette;
	private JLabel description_recette;
	private JSpinner spinner, spinner_1, spinner_2, spinner_3;
	private Thread t;
	static private boolean run = true;
	private JLabel inv_plante1, inv_plante2, inv_plante3, inv_plante4;
	private int qteplante1 = 0;
	private int qteplante2 = 0;
	private int qteplante3 = 0;
	private int qteplante4 = 0;

	

	protected JComponent makeTextPanel(int size) {
		JPanel panel = new JPanel(false);
		panel.setLayout(new GridLayout(1, size));
		return panel;
	}

	public static void setRun(boolean run) {
		PanelCraft.run = run;
	}

	public PanelCraft() {
		setLayout(new MigLayout("", "[][grow][]", "[150px:150px:150px][grow][80px:80px:80px,grow][]"));

		// chargement des images
		try {
			imgFond = ImageIO.read(new File("images/bois_fond.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconPlante1 = ImageIO.read(new File("images/herbe1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconPlante2 = ImageIO.read(new File("images/herbe2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconPlante3 = ImageIO.read(new File("images/herbe3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconPlante4 = ImageIO.read(new File("images/herbe4.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconInventaire = ImageIO.read(new File("images/crate_inventaire1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			iconCraft = ImageIO.read(new File("images/craft.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// r�cup�ration de toutes les recettes dans la base
		listeAllRecette = recettedao.getRecettes();

		//tri des recettes pour les placer dans la bonne liste
		for (Recette recette : listeAllRecette) {
			if (recette.getType().equals("H")) {
				listeRecetteHOWOB.add(recette);

			} else {
				if (recette.getType().equals("F")) {
					listeRecetteFARMVILLAGE.add(recette);
				} else {
					listeRecetteBOOMCRAFT.add(recette);
				}
			}
		}

		JPanel panel_listeRecette = new JPanel();
		panel_listeRecette.setOpaque(false);
		add(panel_listeRecette, "cell 1 0,grow");

		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panel1 = makeTextPanel(10);

		JPanel btnPnl1 = new JPanel();

		// On ajoute les recettes de boomcraft dans l'onglet correspondant a boomcraft (1 recette = 1 bouton)
		JButton[] buttons1 = new JButton[listeRecetteBOOMCRAFT.size()];
		try {
			for (int i = 0; i < listeRecetteBOOMCRAFT.size(); i++) {
				buttons1[i] = new JButton();
				buttons1[i].setIcon(new ImageIcon(ImageIO
						.read(new File("images/recette" + listeRecetteBOOMCRAFT.get(i).getIdRecette() + ".png"))));
				buttons1[i].setName("BOOMCRAFT-" + i);
				buttons1[i].setBorderPainted(false);
				buttons1[i].setFocusPainted(false);
				buttons1[i].setContentAreaFilled(false);
				buttons1[i].addActionListener(this);
				btnPnl1.add(buttons1[i]);

			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}

		panel1.add(new JScrollPane(btnPnl1), BorderLayout.CENTER);

		tabbedPane.addTab("Recettes BoomCraft", panel1);

		JComponent panel2 = makeTextPanel(3);

		JPanel btnPnl2 = new JPanel();

		// On ajoute les recettes de farmvillage dans l'onglet correspondant a farmvillage (1 recette = 1 bouton)
		JButton[] buttons2 = new JButton[listeRecetteFARMVILLAGE.size()];
		try {
			for (int i = 0; i < listeRecetteFARMVILLAGE.size(); i++) {
				buttons2[i] = new JButton();
				buttons2[i].setIcon(new ImageIcon(ImageIO
						.read(new File("images/recette" + listeRecetteFARMVILLAGE.get(i).getIdRecette() + ".png"))));
				buttons2[i].setName("FARMVILLAGE-" + i);
				buttons2[i].setBorderPainted(false);
				buttons2[i].setFocusPainted(false);
				buttons2[i].setContentAreaFilled(false);
				buttons2[i].addActionListener(this);
				btnPnl2.add(buttons2[i]);

			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}

		panel2.add(new JScrollPane(btnPnl2), BorderLayout.CENTER);
		tabbedPane.addTab("Recettes FarmVillage", panel2);

		JComponent panel3 = makeTextPanel(5);

		JPanel btnPnl3 = new JPanel();

		// On ajoute les recettes de howob dans l'onglet correspondant a howob (1 recette = 1 bouton)
		JButton[] buttons3 = new JButton[listeRecetteHOWOB.size()];
		try {
			for (int i = 0; i < listeRecetteHOWOB.size(); i++) {
				buttons3[i] = new JButton();
				buttons3[i].setIcon(new ImageIcon(
						ImageIO.read(new File("images/recette" + listeRecetteHOWOB.get(i).getIdRecette() + ".png"))));
				buttons3[i].setName("HOWOB-" + i);
				buttons3[i].setBorderPainted(false);
				buttons3[i].setFocusPainted(false);
				buttons3[i].setContentAreaFilled(false);
				buttons3[i].addActionListener(this);

				btnPnl3.add(buttons3[i]);

			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}

		panel3.add(new JScrollPane(btnPnl3), BorderLayout.CENTER);
		tabbedPane.addTab("Recettes HoWoB", panel3);

		panel1.setPreferredSize(new Dimension(500, 100));
		panel2.setPreferredSize(new Dimension(500, 90));
		panel3.setPreferredSize(new Dimension(500, 90));
		
		// on rajoute le JTabbedPane dans un panel pour affichage correct
		panel_listeRecette.add(tabbedPane);

		
		JPanel panel_infoRecette = new JPanel();
		panel_infoRecette.setOpaque(false);
		add(panel_infoRecette, "cell 1 1,grow");
		panel_infoRecette.setLayout(new BorderLayout(0, 0));

		JPanel panelComposantRecette = new JPanel();
		panelComposantRecette.setOpaque(false);
		panel_infoRecette.add(panelComposantRecette, BorderLayout.WEST);
		panelComposantRecette.setLayout(new GridLayout(2, 1, 0, -200));
		panelComposantRecette.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panelComposantRecette.add(panel_2);

		
		JLabel listecomporecette = new JLabel("Composants nécessaires");
		listecomporecette.setVerticalAlignment(SwingConstants.TOP);
		listecomporecette.setHorizontalTextPosition(SwingConstants.CENTER);
		listecomporecette.setHorizontalAlignment(SwingConstants.CENTER);
		listecomporecette.setAlignmentX(Component.CENTER_ALIGNMENT);
		listecomporecette.setSize(new Dimension(200, 25));
		listecomporecette.setPreferredSize(new Dimension(200, 25));
		listecomporecette.setMinimumSize(new Dimension(250, 25));
		listecomporecette.setMaximumSize(new Dimension(250, 25));
		listecomporecette.setFont(new Font("Tahoma", Font.BOLD, 15));

		panel_2.add(listecomporecette);

		JPanel panelListeCompo = new JPanel();
		panelListeCompo.setOpaque(false);
		panelListeCompo.setLayout(new GridLayout(2, 2, 25, 25));
		
		
		//initialisation des composants n�cessaire pour les recettes
		lblPlante1Recette = new JLabel("");
		lblPlante1Recette.setVerticalTextPosition(JLabel.BOTTOM);
		lblPlante1Recette.setHorizontalTextPosition(JLabel.CENTER);
		lblPlante1Recette.setIcon(new ImageIcon(iconPlante1));
		panelListeCompo.add(lblPlante1Recette);

		lblPlante2Recette = new JLabel("");
		lblPlante2Recette.setVerticalTextPosition(JLabel.BOTTOM);
		lblPlante2Recette.setHorizontalTextPosition(JLabel.CENTER);
		lblPlante2Recette.setIcon(new ImageIcon(iconPlante2));
		panelListeCompo.add(lblPlante2Recette);

		lblPlante3Recette = new JLabel("");
		lblPlante3Recette.setVerticalTextPosition(JLabel.BOTTOM);
		lblPlante3Recette.setHorizontalTextPosition(JLabel.CENTER);
		lblPlante3Recette.setIcon(new ImageIcon(iconPlante3));
		panelListeCompo.add(lblPlante3Recette);

		lblPlante4Recette = new JLabel("");
		lblPlante4Recette.setVerticalTextPosition(JLabel.BOTTOM);
		lblPlante4Recette.setHorizontalTextPosition(JLabel.CENTER);
		lblPlante4Recette.setIcon(new ImageIcon(iconPlante4));
		panelListeCompo.add(lblPlante4Recette);

		panelComposantRecette.add(panelListeCompo);

		
		JPanel panel_details_infoRecette = new JPanel();
		panel_details_infoRecette.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_details_infoRecette.setOpaque(false);
		panel_details_infoRecette.setSize(new Dimension(600, 250));
		panel_details_infoRecette.setPreferredSize(new Dimension(600, 250));
		panel_details_infoRecette.setMinimumSize(new Dimension(600, 250));
		panel_details_infoRecette.setMaximumSize(new Dimension(600, 250));
		panel_infoRecette.add(panel_details_infoRecette, BorderLayout.EAST);
		panel_details_infoRecette.setLayout(new MigLayout("", "[600px,grow]", "[40px,grow][125px,grow,top]"));

		//initilisation du titre et de la description d'une recette
		titre_recette = new JLabel("");
		titre_recette.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_details_infoRecette.add(titre_recette, "cell 0 0,alignx left,aligny top");

		description_recette = new JLabel("");
		panel_details_infoRecette.add(description_recette, "cell 0 1");

		JPanel panel_title_infoRecette = new JPanel();
		panel_title_infoRecette.setOpaque(false);
		panel_infoRecette.add(panel_title_infoRecette, BorderLayout.NORTH);

		JLabel label = new JLabel("Information sur la recette      ");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_title_infoRecette.add(label);

		JPanel panel_listeRessources = new JPanel();
		panel_listeRecette.setOpaque(false);
		add(panel_listeRessources, "cell 1 2,grow");
		panel_listeRessources.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		inv_plante1 = new JLabel();
		inv_plante2 = new JLabel();
		inv_plante3 = new JLabel();
		inv_plante4 = new JLabel();
		inv_plante1.setName("1");
		inv_plante2.setName("2");
		inv_plante3.setName("3");
		inv_plante4.setName("4");

		// r�cup�ration de la quantit� des composants disponibles pour le joueur pour chaque plante
		qteplante1 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 1);
		qteplante2 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 2);
		qteplante3 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 3);
		qteplante4 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 4);

		// ajout des quantit�s dans les labels
		inv_plante1.setText(qteplante1 + " Dispo");
		inv_plante1.setVerticalTextPosition(JLabel.BOTTOM);
		inv_plante1.setHorizontalTextPosition(JLabel.CENTER);
		inv_plante2.setText(qteplante2 + " Dispo");
		inv_plante2.setVerticalTextPosition(JLabel.BOTTOM);
		inv_plante2.setHorizontalTextPosition(JLabel.CENTER);
		inv_plante3.setText(qteplante3 + " Dispo");
		inv_plante3.setVerticalTextPosition(JLabel.BOTTOM);
		inv_plante3.setHorizontalTextPosition(JLabel.CENTER);
		inv_plante4.setText(qteplante4 + " Dispo");
		inv_plante4.setVerticalTextPosition(JLabel.BOTTOM);
		inv_plante4.setHorizontalTextPosition(JLabel.CENTER);
		inv_plante1.setIcon(new ImageIcon(iconPlante1));
		inv_plante2.setIcon(new ImageIcon(iconPlante2));
		inv_plante3.setIcon(new ImageIcon(iconPlante3));
		inv_plante4.setIcon(new ImageIcon(iconPlante4));

		// cr�ation des spinner avec NBMAX = quantit� de plante du joueur (r�cup�r� ci au-dessus)
		spinner = new JSpinner(new SpinnerNumberModel(0, 0, qteplante1, 1));
		spinner_1 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante2, 1));
		spinner_2 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante3, 1));
		spinner_3 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante4, 1));

		panel_listeRessources.add(inv_plante1);
		panel_listeRessources.setOpaque(false);

		panel_listeRessources.add(spinner);
		panel_listeRessources.add(inv_plante2);

		panel_listeRessources.add(spinner_1);
		panel_listeRessources.add(inv_plante3);

		panel_listeRessources.add(spinner_2);
		panel_listeRessources.add(inv_plante4);

		panel_listeRessources.add(spinner_3);

		//thread pour refresh la quantit� des plantes disponibles
		t = new Thread() {
			public void run() {
				while (run) {
					
					qteplante1 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 1);
					qteplante2 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 2);
					qteplante3 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 3);
					qteplante4 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 4);
					

					//on refresh les valeurs sur tous les spinners
					spinner = new JSpinner(new SpinnerNumberModel(0, 0, qteplante1, 1));
					spinner_1 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante2, 1));
					spinner_2 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante3, 1));
					spinner_3 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante4, 1));
					
					inv_plante1.setText(qteplante1 + " Dispo");
					inv_plante1.setVerticalTextPosition(JLabel.BOTTOM);
					inv_plante1.setHorizontalTextPosition(JLabel.CENTER);
					inv_plante2.setText(qteplante2 + " Dispo");
					inv_plante2.setVerticalTextPosition(JLabel.BOTTOM);
					inv_plante2.setHorizontalTextPosition(JLabel.CENTER);
					inv_plante3.setText(qteplante3 + " Dispo");
					inv_plante3.setVerticalTextPosition(JLabel.BOTTOM);
					inv_plante3.setHorizontalTextPosition(JLabel.CENTER);
					inv_plante4.setText(qteplante4 + " Dispo");
					inv_plante4.setVerticalTextPosition(JLabel.BOTTOM);
					inv_plante4.setHorizontalTextPosition(JLabel.CENTER);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!run) {

					t.interrupt();
				}
			}
		};
		t.start();
		
		
		JPanel panel_btCraft = new JPanel();
		add(panel_btCraft, "cell 1 3,grow");
		panel_btCraft.setLayout(new MigLayout("", "[grow][grow,right][grow,left][grow]", "[29px]"));

		// bouton pour mute la musique
		JToggleButton tglbtnMute = new JToggleButton("Mute Sound");
		tglbtnMute.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					if (MusicPlayer.loopPanelCraft != null) {
						MusicPlayer.craftMusicIsMute = true;
					}
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					if (MusicPlayer.loopPanelCraft != null) {
						MusicPlayer.craftMusicIsMute = false;
					}
				}
			}
		});
		panel_btCraft.add(tglbtnMute, "cell 0 0,alignx left,aligny bottom");

		//bouton pour afficher l'inventaire
		JButton btnInventaire = new JButton(new ImageIcon(iconInventaire));
		panel_btCraft.add(btnInventaire, "cell 1 0,alignx right,aligny top");
		btnInventaire.setBorderPainted(false);
		btnInventaire.setFocusPainted(false);
		btnInventaire.setContentAreaFilled(false);

		btnInventaire.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PopupInventaire.getInstance();
			}
		});

		// bouton pour lancer le craft d�sir�
		JButton btnCraft = new JButton(new ImageIcon(iconCraft));
		btnCraft.setBorderPainted(false);
		btnCraft.setFocusPainted(false);
		btnCraft.setContentAreaFilled(false);

		
		btnCraft.addActionListener(new ActionListener() {
			//lorsqu'on clique sur le boutton de craft, on "commit" les valeurs des diff�rents spinners pour les "figer" et pouvoir les r�cuperer
			@Override
			public void actionPerformed(ActionEvent e) {
				int idobj = 0;
				System.out.println("DEBUT RECHERCHE");
				try {
					spinner.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					spinner_1.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					spinner_2.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					spinner_3.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				// on recherche si une recette existe avec les quantit�s selectionn�es
				idobj = recettedao.getIdRecetteByComposants((Integer) spinner.getValue(),
						(Integer) spinner_1.getValue(), (Integer) spinner_2.getValue(), (Integer) spinner_3.getValue());
				
				if (idobj != 0) {
					//si la recette existe, on craft l'item en question
					inventairedao.craftNewItem(MainFrame.getUUID(), idobj, (Integer) spinner.getValue(),
							(Integer) spinner_1.getValue(), (Integer) spinner_2.getValue(),
							(Integer) spinner_3.getValue());

					// on refresh la quantit� de compo disponible pour le joueur
					qteplante1 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 1);
					qteplante2 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 2);
					qteplante3 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 3);
					qteplante4 = objetDao.getNbObjetByUuidAndByIdObjet(MainFrame.getUUID(), 4);

					panel_listeRessources.removeAll();
					repaint();
					//on refresh les valeurs sur tous les spinners
					spinner = new JSpinner(new SpinnerNumberModel(0, 0, qteplante1, 1));
					spinner.setValue(0);

					spinner_1 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante2, 1));
					spinner_1.setValue(0);
					spinner_2 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante3, 1));
					spinner_2.setValue(0);
					spinner_3 = new JSpinner(new SpinnerNumberModel(0, 0, qteplante4, 1));
					spinner_3.setValue(0);
					panel_listeRessources.add(inv_plante1);
					panel_listeRessources.setOpaque(false);

					panel_listeRessources.add(spinner);
					panel_listeRessources.add(inv_plante2);

					panel_listeRessources.add(spinner_1);
					panel_listeRessources.add(inv_plante3);

					panel_listeRessources.add(spinner_2);
					panel_listeRessources.add(inv_plante4);

					panel_listeRessources.add(spinner_3);
					
					System.out.println("FIN RECHERCHE");


				}
			}
		});

		panel_btCraft.add(btnCraft, "cell 2 0,alignx left,aligny top");
		panel_btCraft.setOpaque(false);

		JButton btnRgles = new JButton("Règles");
		btnRgles.addActionListener(this);
		btnRgles.setActionCommand("regles");
		panel_btCraft.add(btnRgles, "cell 3 0,alignx right,aligny bottom");

	}

	public void paintComponent(Graphics g) {
		g.setColor(this.color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(this.message, 10, 20);
		g.drawImage(imgFond, 0, 0, null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if (btn.getActionCommand().equals("regles")) {
				ReglesFrame.getInstance();
			} else {
				String nombtn = btn.getName();
				String[] str = nombtn.split("-");
				if (str[0].equals("HOWOB")) {
					titre_recette
							.setText(String.valueOf(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getNomRecette()));
					description_recette
							.setText(String.valueOf(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getDescription()));
					lblPlante1Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte1() + " requis");
					lblPlante2Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte2() + " requis");
					lblPlante3Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte3() + " requis");
					lblPlante4Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte4() + " requis");
				} else {
					if (str[0].equals("FARMVILLAGE")) {
						titre_recette.setText(
								String.valueOf(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getNomRecette()));
						description_recette.setText(
								String.valueOf(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getDescription()));
						lblPlante1Recette
								.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte1() + " requis");

						lblPlante2Recette
								.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte2() + " requis");
						lblPlante3Recette
								.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte3() + " requis");
						lblPlante4Recette
								.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte4() + " requis");
					} else {
						// str[0]=="BOOMCRAFT"
						titre_recette.setText(
								String.valueOf(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getNomRecette()));
						description_recette.setText(
								String.valueOf(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getDescription()));
						lblPlante1Recette
								.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte1() + " requis");
						lblPlante2Recette
								.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte2() + " requis");
						lblPlante3Recette
								.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte3() + " requis");
						lblPlante4Recette
								.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte4() + " requis");
					}
				}
			}
		}
	}
}