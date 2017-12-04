import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.entitie.Inventaire;

public class PopupInventaire extends JFrame implements ActionListener{

	private JPanel contentPane;

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
		
		BufferedImage iconVide=null;
		setLayout(new BorderLayout());
		
		
		try {
	  		iconVide = ImageIO.read(new File("images/vide.png"));
	  		
	  	} catch (IOException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
		InventaireDao invdao = new InventaireDao();
		ArrayList<Inventaire> inv = new ArrayList<Inventaire>();
		JPanel btnPnl1 = new JPanel();
		
		inv=invdao.getInventaireByUuid(MainFrame.getUUID());
		int cpt=0;
		for (int i=0; i<inv.size();i++) {
			if(i%6==0) {
				cpt++;
			}
		}
		
		btnPnl1.setLayout(new GridLayout(cpt,10, 25, 25));
		btnPnl1.setSize(new Dimension(500,500));
	  	
		
		
	  	ArrayList<String> projectNameList = new ArrayList<String>();
		
	    for (int index = 0; index < 6*cpt; index++) {
	        projectNameList.add("");
	    }
		
	    for (int index = 0; index < inv.size(); index++) {
	    	projectNameList.set(index, "Qte : " + inv.get(index).getQte());
	    }
	    
	    String[] projectNames = projectNameList.toArray(new String[0]);

	    JLabel[] buttons = new JLabel[projectNameList.size()];
	    BufferedImage iconPlante = null;
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
	            btnPnl1.add(buttons[i]);

	        }
	    } catch (Exception e2) {
	        JOptionPane.showMessageDialog(null, e2);
	    }
	    
	    
	    
	    JScrollPane jscroll = new JScrollPane(btnPnl1);

	    getContentPane().add(jscroll, BorderLayout.CENTER);

	    setVisible(true);
	    setSize(500, 500);
	    setResizable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
