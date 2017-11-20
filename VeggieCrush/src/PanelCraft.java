import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.AWTException;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.entitie.Inventaire;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;

import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;

public class PanelCraft extends JPanel{
	  private Color color = Color.white;
	  private String message = "";
	  private JTable table;
	  InventaireDao inventairedao = new InventaireDao();
	  ObjetDao objetDao = new ObjetDao();
	  ArrayList<Inventaire> invs = new ArrayList<Inventaire>();
	  	
	  /**
	 *  Modification à la ligne 37
	 */
	  
	  protected JComponent makeTextPanel(String text) {
	        JPanel panel = new JPanel(false);
	        JLabel filler = new JLabel(text);
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
	        return panel;
	    }

	  
	  
	public PanelCraft(){
	  	setLayout(new MigLayout("", "[][grow][]", "[100px:100px:100px,grow][200px:200px:200px,grow][150px:150px:150px,grow][80px:80px:80px,grow][]"));
	  	
	  	BufferedImage iconPlante1=null;
	  	BufferedImage iconPlante2=null;
	  	BufferedImage iconPlante3=null;
	  	BufferedImage iconPlante4=null;
	  	BufferedImage iconInventaire=null;
	  	BufferedImage iconCraft=null;
	  	BufferedImage iconVide=null;
	  	
		try {
			iconPlante1 = ImageIO.read(new File("images/herbe1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  	try {
			iconPlante2 = ImageIO.read(new File("images/herbe2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	  	try {
			iconPlante3 = ImageIO.read(new File("images/herbe3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	  	try {
			iconPlante4 = ImageIO.read(new File("images/herbe4.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  		  	
	  	try {
	  		iconInventaire = ImageIO.read(new File("images/crate_inventaire1.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	  	try {
	  		iconCraft = ImageIO.read(new File("images/craft.png"));
	  		
	  	} catch (IOException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	  	try {
	  		iconVide = ImageIO.read(new File("images/vide.png"));
	  		
	  	} catch (IOException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	  	
	  	JPanel panel_1 = new JPanel();
	  	add(panel_1, "cell 1 0,grow");
	  	
	  	JTabbedPane tabbedPane = new JTabbedPane();
	  	
	  	JComponent panel1 = makeTextPanel("Panel #1");

	  	tabbedPane.addTab("Tab 1", panel1);

	  	JComponent panel2 = makeTextPanel("Panel #2");
	  	tabbedPane.addTab("Tab 2", panel2);

	  	JComponent panel3 = makeTextPanel("Panel #3");
	  	tabbedPane.addTab("Tab 3", panel3);

	  	JComponent panel4 = makeTextPanel(
	  	        "Panel #4");
	  	
	  	panel1.setPreferredSize(new Dimension(500, 60));
	  	panel2.setPreferredSize(new Dimension(500, 60));
	  	panel3.setPreferredSize(new Dimension(500, 60));
	  	panel4.setPreferredSize(new Dimension(500, 60));
	  	tabbedPane.addTab("Tab 4", panel4);
	  	
	  	panel_1.add(tabbedPane);
	  	
	  	JPanel panel_4 = new JPanel();
	  	add(panel_4, "cell 1 1,grow");
	  	
	  	JLabel lblNewLabel = new JLabel("Informations sur recette :");
	  	panel_4.add(lblNewLabel);
	  	
	  	invs = inventairedao.getInventaireByIdAccount(1);
	  	int nbRow = invs.size()%5;
	  
	  
	  	
	  	JPanel panel_5 = new JPanel();
	  	add(panel_5, "cell 1 2,grow");
	  	panel_5.setLayout(new BorderLayout());
	  	
	  	JPanel btnPnl1 = new JPanel();
	  	btnPnl1.setLayout(new GridBagLayout());
	  	GridBagConstraints c = new GridBagConstraints();
	  	c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1.0;
	  	
	  	ArrayList<String> projectNameList = new ArrayList<String>();
	    for (int index = 0; index < 50; index++) {
	        projectNameList.add("Qte : " + index);
	    }
	    String[] projectNames = projectNameList.toArray(new String[0]);

	    // Adding buttons to the project
	    //JButton[] buttons = new JButton[projectNameList.size()];
	    JLabel[] buttons = new JLabel[projectNameList.size()];
	    try {
	        for (int i = 0; i < projectNames.length; i++) {
	        	if(i%10==0) {
	        		c.gridwidth = GridBagConstraints.REMAINDER; //end ro
	        	    c.weightx = 0.0;            
	        	}
	            buttons[i] = new JLabel(projectNames[i]);
	            buttons[i].setIcon(new ImageIcon(iconVide));
	            buttons[i].setVerticalTextPosition(JLabel.BOTTOM);
	            buttons[i].setHorizontalTextPosition(JLabel.CENTER);
	            btnPnl1.add(buttons[i]);

	        }
	    } catch (Exception e2) {
	        JOptionPane.showMessageDialog(null, e2);
	    }
	    JScrollPane scrollPane = new JScrollPane(btnPnl1);
	    
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    panel_5.add(scrollPane,BorderLayout.CENTER);
	    
	  	
	  	
	 
	  	
	  	
	  	
	  	
	  	JPanel panel = new JPanel();
	  	add(panel, "cell 1 3,grow");
	  	panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	  	
	  	
	  	JLabel inv_plante1,inv_plante2,inv_plante3, inv_plante4;
	  	inv_plante1 = new JLabel();
	  	inv_plante2 = new JLabel();
	  	inv_plante3 = new JLabel();
	  	inv_plante4 = new JLabel();
	  	inv_plante1.setName("1");
	  	inv_plante2.setName("2");
	  	inv_plante3.setName("3");
	  	inv_plante4.setName("4");
	  	
	  	int qteplante1 = objetDao.getNbObjetByIdAccountAndByIdObjet(1, 1);
	  	int qteplante2 = objetDao.getNbObjetByIdAccountAndByIdObjet(1, 2);
	  	int qteplante3 = objetDao.getNbObjetByIdAccountAndByIdObjet(1, 3);
	  	int qteplante4 = objetDao.getNbObjetByIdAccountAndByIdObjet(1, 4);
	  	
	  	
	  	
	  	
	  	inv_plante1.setText(qteplante1+" Dispo");
	  	inv_plante1.setVerticalTextPosition(JLabel.BOTTOM);
	  	inv_plante1.setHorizontalTextPosition(JLabel.CENTER);
	  	inv_plante2.setText(qteplante2+" Dispo");
	  	inv_plante2.setVerticalTextPosition(JLabel.BOTTOM);
	  	inv_plante2.setHorizontalTextPosition(JLabel.CENTER);
	  	inv_plante3.setText(qteplante3+" Dispo");
	  	inv_plante3.setVerticalTextPosition(JLabel.BOTTOM);
	  	inv_plante3.setHorizontalTextPosition(JLabel.CENTER);
	  	inv_plante4.setText(qteplante4+" Dispo");
	  	inv_plante4.setVerticalTextPosition(JLabel.BOTTOM);
	  	inv_plante4.setHorizontalTextPosition(JLabel.CENTER);
	  	inv_plante1.setIcon(new ImageIcon(iconPlante1));
	  	inv_plante2.setIcon(new ImageIcon(iconPlante2));
	  	inv_plante3.setIcon(new ImageIcon(iconPlante3));
	  	inv_plante4.setIcon(new ImageIcon(iconPlante4));
        
        panel.add(inv_plante1);
        
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante1,
                1));
        
        int w = spinner.getWidth();   int h = spinner.getHeight();
        panel.add(spinner);
        panel.add(inv_plante2);
        
        JSpinner spinner_1 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante2,
                1));
        spinner_1.setMinimumSize(new Dimension(w*2,h));
        panel.add(spinner_1);
        panel.add(inv_plante3);
        
        JSpinner spinner_2 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante3,
                1));
        spinner_2.setMinimumSize(new Dimension(w*2,h));
        panel.add(spinner_2);
        panel.add(inv_plante4);
        
        JSpinner spinner_3 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante4,
                1));
        spinner_3.setMinimumSize(new Dimension(w*2,h));
        panel.add(spinner_3);
        
        JPanel panel_3 = new JPanel();
        add(panel_3, "cell 1 4,grow");
        
        JButton btnCraft= new JButton(new ImageIcon(iconCraft));
        btnCraft.setBorderPainted(false);
        btnCraft.setFocusPainted(false);
        btnCraft.setContentAreaFilled(false);
	  	panel_3.add(btnCraft);
	  	
	  	
	  		  	
	  }
	  
	  
	  
	  public void paintComponent(Graphics g){
	    g.setColor(this.color);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    g.setColor(Color.white);
	    g.setFont(new Font("Arial", Font.BOLD, 15));
	    g.drawString(this.message, 10, 20);
	  }
}
// MIPA BAS