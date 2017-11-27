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
import com.dao.RecetteDao;
import com.entitie.Inventaire;
import com.entitie.Recette;
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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.Rectangle;

public class PanelCraft extends JPanel implements ActionListener{
	  private Color color = Color.white;
	  private String message = "";
	  private JTable table;
	  private InventaireDao inventairedao = new InventaireDao();
	  private ObjetDao objetDao = new ObjetDao();
	  private ArrayList<Inventaire> invs = new ArrayList<Inventaire>();
	  private RecetteDao recettedao = new RecetteDao();
	  private ArrayList<Recette> listeAllRecette = new ArrayList<Recette>();
	  private ArrayList<Recette> listeRecetteHOWOB = new ArrayList<Recette>();
	  private ArrayList<Recette> listeRecetteFARMVILLAGE = new ArrayList<Recette>();
	  private ArrayList<Recette> listeRecetteBOOMCRAFT = new ArrayList<Recette>();
	  BufferedImage iconPlante1=null;
	  BufferedImage iconPlante2=null;
	  BufferedImage iconPlante3=null;
	  BufferedImage iconPlante4=null;
	  JLabel lblPlante1Recette;
	  JLabel lblPlante2Recette;
	  JLabel lblPlante3Recette;
	  JLabel lblPlante4Recette;
	  JLabel titre_recette;
	  JLabel description_recette;
	  	
	  /**
	 *  Modification Ã  la ligne 37
	 */
	  
	  protected JComponent makeTextPanel(/*String text*/ int size) {
	        JPanel panel = new JPanel(false);
	        /*JLabel filler = new JLabel(text);
	        filler.setHorizontalAlignment(JLabel.CENTER);*/
	        panel.setLayout(new GridLayout(1, size));
	        //panel.add(filler);
	        return panel;
	    }

	  
	  
	public PanelCraft(){
	  	setLayout(new MigLayout("", "[][grow][]", "[150px:150px:150px][grow][80px:80px:80px,grow][]"));
	  	
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
	  	

	  	listeAllRecette=recettedao.getRecettes();
	  	
	  	for (Recette recette : listeAllRecette) {
	  		if(recette.getType().equals("H")) {
	  			listeRecetteHOWOB.add(recette);
	  			
	  		} else {
	  			if(recette.getType().equals("F")) {
	  				listeRecetteFARMVILLAGE.add(recette);
	  			} else {
	  				listeRecetteBOOMCRAFT.add(recette);
	  			}
	  		}
		}
	  	System.out.println(listeAllRecette);
	  	System.out.println(listeRecetteHOWOB);
	  	System.out.println(listeRecetteFARMVILLAGE);
	  	System.out.println(listeRecetteBOOMCRAFT);
	  	
	  	JPanel panel_listeRecette = new JPanel();
	  	add(panel_listeRecette, "cell 1 0,grow");
	  	
	  	JTabbedPane tabbedPane = new JTabbedPane();
	  	
	  	JComponent panel1 = makeTextPanel(10);
	  	
	  	JPanel btnPnl1 = new JPanel();

	    // Adding buttons to the project
	  	JButton[] buttons1 = new JButton[listeRecetteBOOMCRAFT.size()];
	    try {
	        for (int i = 0; i < listeRecetteBOOMCRAFT.size(); i++) {
	            buttons1[i] = new JButton();
	            buttons1[i].setIcon(new ImageIcon(iconVide));
	            buttons1[i].setName("BOOMCRAFT-"+i);
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

	    // Adding buttons to the project
	  	JButton[] buttons2 = new JButton[listeRecetteFARMVILLAGE.size()];
	    try {
	        for (int i = 0; i < listeRecetteFARMVILLAGE.size(); i++) {
	            buttons2[i] = new JButton();
	            buttons2[i].setIcon(new ImageIcon(iconVide));
	            buttons2[i].setName("FARMVILLAGE-"+i);
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

	    // Adding buttons to the project
	    JButton[] buttons3 = new JButton[listeRecetteHOWOB.size()];
	    try {
	        for (int i = 0; i < listeRecetteHOWOB.size(); i++) {
	            buttons3[i] = new JButton();
	            buttons3[i].setIcon(new ImageIcon(iconVide));
	            buttons3[i].setName("HOWOB-"+i);
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
	  	
	  	panel1.setPreferredSize(new Dimension(500, 90));
	  	panel2.setPreferredSize(new Dimension(500, 90));
	  	panel3.setPreferredSize(new Dimension(500, 90));
	  	panel_listeRecette.add(tabbedPane);
	  	
	  	invs = inventairedao.getInventaireByIdAccount(1);
	  	int nbRow = invs.size()%5;
	  	
	  	JPanel panel_infoRecette = new JPanel();
	  	add(panel_infoRecette, "cell 1 1,grow");
	  	panel_infoRecette.setLayout(new BorderLayout(0, 0));
	  	
	  	JLabel lblNewLabel = new JLabel("Information sur la recette      ");
	  	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
	  	lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
	  	panel_infoRecette.add(lblNewLabel, BorderLayout.NORTH);
	  	
	  	JPanel panelComposantRecette = new JPanel();
	  	panel_infoRecette.add(panelComposantRecette, BorderLayout.WEST);
	  	panelComposantRecette.setLayout(new GridLayout(2,1,0,-200));
	  	panelComposantRecette.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
	  	
	  	JLabel listecomporecette = new JLabel("Composants nécessaires");
	  	listecomporecette.setVerticalAlignment(SwingConstants.TOP);
	  	listecomporecette.setAlignmentX(Component.CENTER_ALIGNMENT);
	  	listecomporecette.setSize(new Dimension(200, 25));
	  	listecomporecette.setPreferredSize(new Dimension(200, 25));
	  	listecomporecette.setMinimumSize(new Dimension(250, 25));
	  	listecomporecette.setMaximumSize(new Dimension(250, 25));
	  	listecomporecette.setFont(new Font("Tahoma", Font.BOLD, 15));
	  	
	  	panelComposantRecette.add(listecomporecette);
	  	
	  	JPanel panelListeCompo = new JPanel();
	  	panelListeCompo.setLayout(new GridLayout(2, 2, 25, 25));
	  	
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
	  	
	  	JPanel panel_1 = new JPanel();
	  	panel_1.setSize(new Dimension(600, 250));
	  	panel_1.setPreferredSize(new Dimension(600, 250));
	  	panel_1.setMinimumSize(new Dimension(600, 250));
	  	panel_1.setMaximumSize(new Dimension(600, 250));
	  	panel_infoRecette.add(panel_1, BorderLayout.EAST);
	  	panel_1.setLayout(new MigLayout("", "[600px]", "[40px][125px]"));
	  	
	  	titre_recette = new JLabel("");
	  	titre_recette.setFont(new Font("Tahoma", Font.BOLD, 15));
	  	panel_1.add(titre_recette, "cell 0 0,alignx left,aligny top");
	  	
	  	description_recette = new JLabel("");
	  	panel_1.add(description_recette, "cell 0 1");

	  	
	  	JPanel panel_listeRessources = new JPanel();
	  	add(panel_listeRessources, "cell 1 2,grow");
	  	panel_listeRessources.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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
        
        panel_listeRessources.add(inv_plante1);
        
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante1,
                1));
        
        int w = spinner.getWidth();   int h = spinner.getHeight();
        panel_listeRessources.add(spinner);
        panel_listeRessources.add(inv_plante2);
        
        JSpinner spinner_1 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante2,
                1));
        spinner_1.setMinimumSize(new Dimension(w*2,h));
        panel_listeRessources.add(spinner_1);
        panel_listeRessources.add(inv_plante3);
        
        JSpinner spinner_2 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante3,
                1));
        spinner_2.setMinimumSize(new Dimension(w*2,h));
        panel_listeRessources.add(spinner_2);
        panel_listeRessources.add(inv_plante4);
        
        JSpinner spinner_3 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, qteplante4,
                1));
        spinner_3.setMinimumSize(new Dimension(w*2,h));
        panel_listeRessources.add(spinner_3);
        
        JPanel panel_btCraft = new JPanel();
        add(panel_btCraft, "cell 1 3,grow");
        
        JButton btnInventaire= new JButton(new ImageIcon(iconInventaire));
        panel_btCraft.add(btnInventaire);
        btnInventaire.setBorderPainted(false);
        btnInventaire.setFocusPainted(false);
        btnInventaire.setContentAreaFilled(false);
        
        btnInventaire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupInventaire inventaire = new PopupInventaire();
				inventaire.setVisible(true);
			}
		});
        
        JButton btnCraft= new JButton(new ImageIcon(iconCraft));
        btnCraft.setBorderPainted(false);
        btnCraft.setFocusPainted(false);
        btnCraft.setContentAreaFilled(false);
	  	panel_btCraft.add(btnCraft);
	  	
	  	
	  		  	
	  }
	
	  public void paintComponent(Graphics g){
	    g.setColor(this.color);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    g.setColor(Color.white);
	    g.setFont(new Font("Arial", Font.BOLD, 15));
	    g.drawString(this.message, 10, 20);
	  }



	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			String nombtn=btn.getName();
			String[] str = nombtn.split("-");
			if(str[0].equals("HOWOB")) {
				titre_recette.setText(String.valueOf(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getNomRecette()));
				description_recette.setText(String.valueOf(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getDescription()));
				lblPlante1Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte1()+" requis");
				lblPlante2Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte2()+" requis");
				lblPlante3Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte3()+" requis");
				lblPlante4Recette.setText(listeRecetteHOWOB.get(Integer.valueOf(str[1])).getQte4()+" requis");
			} else {
				if (str[0].equals("FARMVILLAGE")) {
					titre_recette.setText(String.valueOf(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getNomRecette()));
					description_recette.setText(String.valueOf(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getDescription()));
					lblPlante1Recette.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte1()+" requis");
					
					lblPlante2Recette.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte2()+" requis");
					lblPlante3Recette.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte3()+" requis");
					lblPlante4Recette.setText(listeRecetteFARMVILLAGE.get(Integer.valueOf(str[1])).getQte4()+" requis");
				} else {
					//str[0]=="BOOMCRAFT"
					titre_recette.setText(String.valueOf(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getNomRecette()));
					description_recette.setText(String.valueOf(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getDescription()));
					lblPlante1Recette.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte1()+" requis");
					lblPlante2Recette.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte2()+" requis");
					lblPlante3Recette.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte3()+" requis");
					lblPlante4Recette.setText(listeRecetteBOOMCRAFT.get(Integer.valueOf(str[1])).getQte4()+" requis");
				}
			}
			
		}
		
	}
}
// MIPA BAS