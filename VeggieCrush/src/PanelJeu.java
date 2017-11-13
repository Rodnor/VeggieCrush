import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
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

	public PanelJeu(){
		setLayout(new MigLayout("", "[][grow][][][]", "[][grow][grow][grow][grow][grow]"));

		JLabel lblTimer = new JLabel("Timer");
		add(lblTimer, "cell 0 0");

		JLabel lblScore = new JLabel("Score");
		add(lblScore, "cell 4 0");

		JPanel panel_4 = new JPanel();
		add(panel_4, "cell 1 1 1 5,grow");
		panel_4.setLayout(new GridLayout(9, 9, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		add(panel, "cell 2 2,alignx center,aligny center");

		JLabel lblX = new JLabel("x");
		add(lblX, "cell 3 2");

		JLabel label = new JLabel("0");
		add(label, "cell 4 2");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GREEN);
		add(panel_1, "cell 2 3,alignx center,aligny center");

		JLabel lblNewLabel = new JLabel("x");
		add(lblNewLabel, "cell 3 3");

		JLabel label_1 = new JLabel("0");
		add(label_1, "cell 4 3");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLUE);
		add(panel_2, "cell 2 4,alignx center,aligny center");

		JLabel lblNewLabel_1 = new JLabel("x");
		add(lblNewLabel_1, "cell 3 4");

		JLabel label_2 = new JLabel("0");
		add(label_2, "cell 4 4");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		add(panel_3, "cell 2 5,alignx center,aligny center");

		JLabel lblNewLabel_2 = new JLabel("x");
		add(lblNewLabel_2, "cell 3 5");

		JLabel label_3 = new JLabel("0");
		add(label_3, "cell 4 5");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
			{
				try {
					iconPlante = ImageIO.read(new File("images/herbe"+String.valueOf((int)((Math.random()*4)+1))+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//b[i][j] = new JButton("("+(i+1)+") "+"("+(j+1)+")");
				b[i][j] = new JButton(new ImageIcon(iconPlante));
				b[i][j].setBorderPainted(false);
				b[i][j].setFocusPainted(false);
				b[i][j].setContentAreaFilled(false);
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand("click");
				panel_4.add(b[i][j]);
			}
		}
	}

	public void paintComponent(Graphics g){

	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
						next = (ImageIcon) b[row][col].getIcon();
						b[row][col].setIcon(prev);
						prevJButton.setIcon(next);
						numClic++;
					}
				}
			}
			numClic = 0;
		}
	}
}