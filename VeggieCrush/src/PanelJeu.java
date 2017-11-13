import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelJeu extends JPanel implements ActionListener {

	private JButton b[][] = new JButton[9][9];
	private int numClic=0;
	private ImageIcon prev;
	private ImageIcon next;
	private JButton prevJButton;
	private BufferedImage iconPlante=null;
	private int nombreCoups=10;
	private int nombreCoupsBonus=0;
	private int tempsBonus=0;
	private int tempsBase=30;
	private int tempsTotal=tempsBase+tempsBonus;
	private int scoreBonus=0;
	private int herbe1Bonus=0;
	private int herbe2Bonus=0;
	private int herbe3Bonus=0;
	private int herbe4Bonus=0;
	private JLabel nbCoupsRestants;
	private JLabel lblTimer;
	private Thread t;
	private boolean stopGame=false;

	public PanelJeu(){
		setLayout(new MigLayout("", "[][grow][][][][grow]", "[][][grow][grow][grow][grow][grow]"));

		lblTimer = new JLabel("Temps restant : "+String.valueOf(tempsTotal));
		add(lblTimer, "cell 0 0");

		JLabel lblScore = new JLabel(String.valueOf("Score : "+(0+scoreBonus)));
		add(lblScore, "cell 5 0");

		nbCoupsRestants = new JLabel(String.valueOf("Nombre de coups restants : "+(nombreCoups+nombreCoupsBonus)));
		add(nbCoupsRestants, "cell 0 1");

		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 0 2 2 5,alignx center,aligny center");
		panel_4.setLayout(new GridLayout(9, 9, -14, -10));

		ImageIcon icon=null;
		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe1.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 2 3,alignx center,aligny center");

		JLabel lblX = new JLabel("x");
		add(lblX, "cell 3 3");

		JLabel label = new JLabel(String.valueOf(0+herbe1Bonus));
		add(label, "cell 4 3");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe2.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 2 4,alignx center,aligny center");

		JLabel lblNewLabel = new JLabel("x");
		add(lblNewLabel, "cell 3 4");

		JLabel label_1 = new JLabel(String.valueOf(0+herbe2Bonus));
		add(label_1, "cell 4 4");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe3.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 2 5,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("x");
		add(lblNewLabel_1, "cell 3 5");

		JLabel label_2 = new JLabel(String.valueOf(0+herbe3Bonus));
		add(label_2, "cell 4 5");

		try {
			icon = new ImageIcon(ImageIO.read(new File("images/herbe4.jpg")));
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		thumb = new JLabel();
		thumb.setIcon(icon);
		add(thumb, "cell 2 6,alignx center,aligny center");

		JLabel lblNewLabel_2 = new JLabel("x");
		add(lblNewLabel_2, "cell 3 6");

		JLabel label_3 = new JLabel(String.valueOf(0+herbe4Bonus));
		add(label_3, "cell 4 6");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
			{
				try {
					iconPlante = ImageIO.read(new File("images/herbe"+String.valueOf((int)((Math.random()*4)+1))+".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				b[i][j] = new JButton(new ImageIcon(iconPlante));
				b[i][j].setBorderPainted(false);
				b[i][j].setFocusPainted(false);
				b[i][j].setContentAreaFilled(false);
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand("click");
				panel_4.add(b[i][j]);
			}
		}

		t = new Thread() {
			public void run() {
				while(tempsTotal>=0 && stopGame==false) {
					lblTimer.setText("Temps restant : "+String.valueOf(tempsTotal));
					try {
						Thread.sleep(1000);
						tempsTotal--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Fin de la partie
					if(tempsTotal == 0) {
						System.out.println("Fin du game !");
					}
				}
			}
		};
		t.start();
		
		Thread t2 = new Thread() {
			public void run() {
				while(!stopGame) {
					if(nombreCoups==0){
						stopGame=true;
						System.out.println("Fin du game !");
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

	public void paintComponent(Graphics g){

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(nombreCoups>0) {
			if(numClic == 0) {
				for (int row = 0; row < 9; row++) {
					for (int col = 0; col < 9; col++) {
						if (b[row][col] == e.getSource()){
							prev = (ImageIcon) b[row][col].getIcon();
							prevJButton = b[row][col];
							numClic++;
						}
					}
				}
			} else {
				for (int row = 0; row < 9; row++) {
					for (int col = 0; col < 9; col++) {
						if (b[row][col] == e.getSource()){
							if(b[row][col] != prevJButton) {
								next = (ImageIcon) b[row][col].getIcon();
								b[row][col].setIcon(prev);
								prevJButton.setIcon(next);
								numClic++;
								nombreCoups--;
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