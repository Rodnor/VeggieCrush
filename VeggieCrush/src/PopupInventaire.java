import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.dao.InventaireDao;
import com.dao.ObjetDao;
import com.entitie.Inventaire;
import com.entitie.Objet;

public class PopupInventaire extends JFrame implements ActionListener{

	private JPanel contentPane;
	private Thread t;
	private boolean run=true;
	ArrayList<Inventaire> inv;
	ArrayList<String> projectNameList;
	JLabel[] buttons;
	String[] projectNames;
	BufferedImage iconPlante = null;
	BufferedImage iconVide=null;
	JScrollPane jscroll;
	JPanel btnPnl1;
	boolean affichagefenetre=true;
	ObjetDao objetdao = new ObjetDao();
	Objet objet;
	/**
	 * Create the frame.
	 */
	public PopupInventaire() {
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setTitle("Inventaire");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		       	run=false;
		    }
		});
		
		setLayout(new BorderLayout());

	    
	    setSize(500, 500);
	    setResizable(false);
		
		try {
	  		iconVide = ImageIO.read(new File("images/vide.png"));
	  		
	  	} catch (IOException e) {
	  		e.printStackTrace();
	  	}
		
		InventaireDao invdao = new InventaireDao();

		
		
		t = new Thread() {
			public void run() {
				while(run) {
					objet=null;
					btnPnl1 = new JPanel();
					getContentPane().removeAll();
					
					inv = new ArrayList<Inventaire>();
					inv=invdao.getInventaireByUuid(MainFrame.getUUID());
					int cpt=0;
					for (int i=0; i<inv.size();i++) {
						if(i%6==0) {
							cpt++;
						}
					}
					
					btnPnl1.setLayout(new GridLayout(cpt,10, 25, 25));
					btnPnl1.setSize(new Dimension(500,500));
				  	

				  	projectNameList = new ArrayList<String>();
					
				    for (int index = 0; index < 6*cpt; index++) {
				        projectNameList.add("");
				    }
					
				    for (int index = 0; index < inv.size(); index++) {
				    	projectNameList.set(index, "Qte : " + inv.get(index).getQte());
				    }
				    
				    projectNames = projectNameList.toArray(new String[0]);
			
				    buttons = new JLabel[projectNameList.size()];
				    
				    try {
				        for (int i = 0; i < projectNames.length; i++) {
				        	buttons[i] = new JLabel(projectNames[i]);
				        	if(i<inv.size()) {
				        		if(i<4) {
				        			iconPlante = ImageIO.read(new File("images/herbe"+String.valueOf(i+1)+".jpg"));
					        		buttons[i].setIcon(new ImageIcon(iconPlante));
				        		} else {
				        			iconPlante = ImageIO.read(new File("images/recette"+String.valueOf(inv.get(i).getId_objet()-4)+".png"));
					        		buttons[i].setIcon(new ImageIcon(iconPlante));
				        		}
				        		
				        	} else {
				        		buttons[i].setIcon(new ImageIcon(iconVide));
				        	}
				            buttons[i].setVerticalTextPosition(JLabel.BOTTOM);
				            buttons[i].setHorizontalTextPosition(JLabel.CENTER);
				            /*System.out.println(String.valueOf(inv.get(i).getId_objet()));
				            objet=objetdao.getObjetById(inv.get(i).getId_objet());
				            if(i<inv.size()) {
					            System.out.println(objet.toString());
					            buttons[i].setToolTipText(objet.getNom_objet());
				            }*/
				            btnPnl1.add(buttons[i]);
			
				        }
				    } catch (Exception e2) {
				        JOptionPane.showMessageDialog(null, e2);
				    }
				    
				    jscroll = new JScrollPane(btnPnl1);
				    
				    getContentPane().add(jscroll, BorderLayout.CENTER);
				    
				    if(affichagefenetre) {
				    	setVisible(true);
				    	affichagefenetre=false;
				    }   					
			        
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!run) {

			        t.interrupt();
				}
			}
		};
		t.start();
	   
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
