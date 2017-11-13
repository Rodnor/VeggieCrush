import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class PanelCraft extends JPanel{
	  private Color color = Color.white;
	  private String message = "";
	   
	  public PanelCraft(){
	  	setLayout(new MigLayout("", "[][grow][]", "[][grow][][][75px:75px:75px,grow][][][grow][][]"));
	  	
	  	JPanel panel_1 = new JPanel();
	  	add(panel_1, "cell 1 1,grow");
	  	
	  	JPanel panel = new JPanel();
	  	add(panel, "cell 1 4,grow");
	  	panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	  	
	  	JButton btn_inv_plante1 = new JButton("New button");
	  	
	  	
	  	JButton btn_inv_plante2 = new JButton("New button");
	  	
	  	JButton btn_inv_plante3 = new JButton("New button");
	  	
	  	
	  	JButton btn_inv_plante4 = new JButton("New button");
	  	
	  	
	  	BufferedImage iconPlante1=null;
	  	BufferedImage iconPlante2=null;
	  	BufferedImage iconPlante3=null;
	  	BufferedImage iconPlante4=null;
		try {
			iconPlante1 = ImageIO.read(new File("images\\herbe1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	try {
			iconPlante2 = ImageIO.read(new File("images\\herbe2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	try {
			iconPlante3 = ImageIO.read(new File("images\\herbe3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	try {
			iconPlante4 = ImageIO.read(new File("images\\herbe4.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	btn_inv_plante1 = new JButton(new ImageIcon(iconPlante1));
	  	btn_inv_plante1.setBorderPainted(false);
	  	btn_inv_plante1.setFocusPainted(false);
	  	btn_inv_plante1.setContentAreaFilled(false);
	  	btn_inv_plante2 = new JButton(new ImageIcon(iconPlante2));
	  	btn_inv_plante2.setBorderPainted(false);
	  	btn_inv_plante2.setFocusPainted(false);
	  	btn_inv_plante2.setContentAreaFilled(false);
	  	btn_inv_plante3 = new JButton(new ImageIcon(iconPlante3));
	  	btn_inv_plante3.setBorderPainted(false);
	  	btn_inv_plante3.setFocusPainted(false);
	  	btn_inv_plante3.setContentAreaFilled(false);
	  	btn_inv_plante4 = new JButton(new ImageIcon(iconPlante4));
	  	btn_inv_plante4.setBorderPainted(false);
	  	btn_inv_plante4.setFocusPainted(false);
	  	btn_inv_plante4.setContentAreaFilled(false);
	  	
	  	btn_inv_plante1.setSize(75, 75);
	  	btn_inv_plante2.setSize(75, 75);
	  	btn_inv_plante3.setSize(75, 75);
	  	btn_inv_plante4.setSize(75, 75);
	  	
	  	btn_inv_plante1.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent e) {
	  	        System.out.println("Plante 1");
	  	    }
	  	});
	  	btn_inv_plante2.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent e) {
	  	        System.out.println("Plante 2");
	  	    }
	  	});
	  	btn_inv_plante3.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent e) {
	  	        System.out.println("Plante 3");
	  	    }
	  	});
	  	btn_inv_plante4.addActionListener(new ActionListener() {
	  	    public void actionPerformed(ActionEvent e) {
	  	        System.out.println("Plante 4");
	  	    }
	  	});
	  	
	  	panel.add(btn_inv_plante1);
	  	panel.add(btn_inv_plante2);
	  	panel.add(btn_inv_plante3);
	  	panel.add(btn_inv_plante4);
	  	
	  	
	  	JButton btnNewButton = new JButton("Inventaire");
	  	btnNewButton.setActionCommand("Inventaire");
	  	btnNewButton.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent arg0) {
	  		}
	  	});
	  	
	  	JPanel panel_2 = new JPanel();
	  	add(panel_2, "cell 1 7,grow");
	  	add(btnNewButton, "cell 0 9");
	  	
	  	JButton btnNewButton_1 = new JButton("Crafter !");
	  	add(btnNewButton_1, "cell 2 9");}
	  
	  
	  
	  public void paintComponent(Graphics g){
	    g.setColor(this.color);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    g.setColor(Color.white);
	    g.setFont(new Font("Arial", Font.BOLD, 15));
	    g.drawString(this.message, 10, 20);
	  }
}