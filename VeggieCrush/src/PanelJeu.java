import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

public class PanelJeu extends JPanel implements ActionListener {

	private JButton b[][] = new JButton[9][9];
	private JPanel canvas;
	private JLabel nbHerbe1;
	private JLabel nbHerbe2;
	private JLabel nbHerbe3;
	private JLabel nbHerbe4;
	private int numClic=0;
	private ImageIcon prev;
	private ImageIcon next;
	private JButton prevJButton;
	private BufferedImage iconPlante=null;
	private int nombreCoups=25;
	private int nombreCoupsBonus=0;
	private int tempsBonus=0;
	private int tempsBase=3;
	private int tempsTotal=tempsBase+tempsBonus;
	private int scoreBonus=0;
	private int herbe1Bonus=0;
	private int herbe2Bonus=0;
	private int herbe3Bonus=0;
	private int herbe4Bonus=0;
	private JLabel nbCoupsRestants;
	private JLabel lblTimer;
	private boolean gameRunning=false;
	private boolean canSwitch=false;
	private int row;
	private int col;
	private ArrayList<JButton> herbe1 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe2 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe3 = new ArrayList<JButton>();
	private ArrayList<JButton> herbe4 = new ArrayList<JButton>();
	private JLabel lblScore;
	private int pointDeBase=20;
	private JButton btnJouer;
	private int multiplicateurBonus1=1;
	private int multiplicateurBonus2=1;
	private int multiplicateurBonus3=1;
	private int multiplicateurBonus4=1;

	public PanelJeu(){

		setLayout(new MigLayout("", "[][][grow][][][][grow]", "[][][grow][grow][grow][grow][grow][]"));

		lblTimer = new JLabel("Temps restant : "+String.valueOf(tempsTotal));
		add(lblTimer, "cell 0 0");

		lblScore = new JLabel("Score : "+String.valueOf(0+scoreBonus));
		add(lblScore, "cell 6 0");

		nbCoupsRestants = new JLabel("Nombre de coups restants : "+String.valueOf(nombreCoups+nombreCoupsBonus));
		add(nbCoupsRestants, "cell 0 1");

		canvas = new JPanel();
		add(canvas, "cell 0 2 3 5,alignx center,aligny center");
		canvas.setLayout(new GridLayout(9, 9, -14, -10));

		ImageIcon icon=null;
		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe1.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 3,alignx center,aligny center");

		JLabel lblX = new JLabel("x");
		add(lblX, "cell 4 3");

		nbHerbe1 = new JLabel(String.valueOf(0+herbe1Bonus));
		add(nbHerbe1, "cell 5 3");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe2.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 4,alignx center,aligny center");

		JLabel lblNewLabel = new JLabel("x");
		add(lblNewLabel, "cell 4 4");

		nbHerbe2 = new JLabel(String.valueOf(0+herbe2Bonus));
		add(nbHerbe2, "cell 5 4");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe3.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 5,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("x");
		add(lblNewLabel_1, "cell 4 5");

		nbHerbe3= new JLabel(String.valueOf(0+herbe3Bonus));
		add(nbHerbe3, "cell 5 5");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe4.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 3 6,alignx center,aligny center");

		JLabel lblNewLabel_2 = new JLabel("x");
		add(lblNewLabel_2, "cell 4 6");

		nbHerbe4 = new JLabel(String.valueOf(0+herbe4Bonus));
		add(nbHerbe4, "cell 5 6");

		btnJouer = new JButton("Jouer !");
		btnJouer.addActionListener(this);
		btnJouer.setActionCommand("jouer");
		add(btnJouer, "cell 1 7");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("jouer")) {
				// On remplis le canevas
				fillCanvas();

				// On met une herbe par coin
				fillCanvasCorners();

				startThreads();

				btnJouer.setVisible(false);
			} else {
				if(nombreCoups>0 && gameRunning) {
					if(numClic == 0) {
						for (int row = 0; row < 9; row++) {
							for (int col = 0; col < 9; col++) {
								if (b[row][col] == e.getSource()) { // et pas première case
									prev = (ImageIcon) b[row][col].getIcon();
									prevJButton = b[row][col];
									this.col=col;
									this.row=row;
									numClic++;
								}
							}
						}
					} else {
						for (int row = 0; row < 9; row++) {
							for (int col = 0; col < 9; col++) {
								if (b[row][col] == e.getSource()){
									if(row+1 == this.row && col == this.col || row-1 == this.row && col == this.col || row == this.row && col+1 == this.col || row == this.row && col-1 == this.col) {
										canSwitch=true;
									}

									if(b[row][col] != prevJButton && canSwitch) {
										next = (ImageIcon) b[row][col].getIcon();
										String tmp = b[row][col].getName();

										b[row][col].setIcon(prev);
										b[row][col].setName(prevJButton.getName());

										prevJButton.setIcon(next);
										prevJButton.setName(tmp);

										nombreCoups--;
										canSwitch=false;
									}
								}
							}
						}
						numClic = 0;
					}
					nbCoupsRestants.setText("Nombre de coups restants : "+String.valueOf(nombreCoups));
				}
			}
		}
	}

	public void startThreads() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gameRunning = true;

		Thread t = new Thread() {
			public void run() {
				while(tempsTotal>=0 && gameRunning) {
					lblTimer.setText("Temps restant : "+String.valueOf(tempsTotal));
					try {
						Thread.sleep(1000);
						tempsTotal--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Fin de la partie
					if(tempsTotal == 0) {
						lblTimer.setText("Temps restant : 0");
						gameRunning = false;
						finDePartie();
						sendDatasToDatabase();
					}
				}
			}
		};
		t.start();

		Thread t2 = new Thread() {
			public void run() {
				while(gameRunning) {
					if(nombreCoups==0){
						gameRunning = false;
						finDePartie();
						sendDatasToDatabase();
					}
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

	public void fillCanvas() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
			{
				int rand = (int)((Math.random()*4)+1);
				try {
					iconPlante = ImageIO.read(new File("images/herbe"+String.valueOf(rand)+".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				b[i][j] = new JButton(new ImageIcon(iconPlante));
				b[i][j].setBorderPainted(false);
				b[i][j].setFocusPainted(false);
				b[i][j].setContentAreaFilled(false);
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand("click");
				b[i][j].setName(String.valueOf(rand));
				canvas.add(b[i][j]);
			}
		}

		b[0][0].removeActionListener(this);
		b[0][8].removeActionListener(this);
		b[8][0].removeActionListener(this);
		b[8][8].removeActionListener(this);
	}

	public void fillCanvasCorners() {
		try {
			b[0][0].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe1.jpg"))));
			b[0][0].setName("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			b[0][8].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe2.jpg"))));
			b[0][8].setName("2");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			b[8][0].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe3.jpg"))));
			b[8][0].setName("3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			b[8][8].setIcon(new ImageIcon(ImageIO.read(new File("images/herbe4.jpg"))));
			b[8][8].setName("4");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void finDePartie() {
		System.out.println("Fin du game !");

		explorer(b, b[0][0], 0, 0, herbe1);
		explorer(b, b[0][8], 0, 8, herbe2);
		explorer(b, b[8][0], 8, 0, herbe3);
		explorer(b, b[8][8], 8, 8, herbe4);

		nbHerbe1.setText(String.valueOf(herbe1.size()));
		nbHerbe2.setText(String.valueOf(herbe2.size()));
		nbHerbe3.setText(String.valueOf(herbe3.size()));
		nbHerbe4.setText(String.valueOf(herbe4.size()));

		if(herbe1.size()>=10) {
			multiplicateurBonus1=2;
		}

		if(herbe2.size()>=10) {
			multiplicateurBonus2=2;
		}

		if(herbe3.size()>=10) {
			multiplicateurBonus3=2;
		}

		if(herbe4.size()>=10) {
			multiplicateurBonus4=2;
		}

		lblScore.setText("Score : "+(herbe1.size()*pointDeBase*multiplicateurBonus1+herbe2.size()*pointDeBase*multiplicateurBonus2+herbe3.size()*pointDeBase*multiplicateurBonus3+herbe4.size()*pointDeBase*multiplicateurBonus4));
		
		// on reset les éléments du jeu
		resetElements();
	}

	public void explorer(JButton graphe[][], JButton bouton, int rowCurrentButton, int colCurrentButton, ArrayList<JButton> liste) {
		liste.add(bouton);

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if(row<9 && row>=0 && col>=0 && col<9) {
					if(row+1 == rowCurrentButton && col == colCurrentButton || row-1 == rowCurrentButton && col == colCurrentButton || row == rowCurrentButton && col+1 == colCurrentButton || row == rowCurrentButton && col-1 == colCurrentButton) {
						if(!liste.contains(graphe[row][col]) && graphe[row][col].getName().equals(bouton.getName())) {
							explorer(graphe, graphe[row][col], row, col, liste);
						}
					}
				}
			}
		}
	}
	
	public void resetElements() {
		tempsTotal = 3;
		nombreCoups = 25;
				
		canvas.removeAll();
		revalidate();
		repaint();
		
		lblScore.setText("Score : "+(0+scoreBonus));
		nbCoupsRestants.setText("Nombre de coups restants : "+String.valueOf(nombreCoups+nombreCoupsBonus));
		nbHerbe1.setText(String.valueOf(0+herbe1Bonus));
		nbHerbe2.setText(String.valueOf(0+herbe2Bonus));
		nbHerbe3.setText(String.valueOf(0+herbe3Bonus));
		nbHerbe4.setText(String.valueOf(0+herbe4Bonus));
		lblTimer.setText("Temps restant : "+String.valueOf(tempsTotal));
		
		herbe1.clear();
		herbe2.clear();
		herbe3.clear();
		herbe4.clear();
		
		btnJouer.setVisible(true);
	}

	public void sendDatasToDatabase() {
		System.out.println("Datas sent to database");
	}
}