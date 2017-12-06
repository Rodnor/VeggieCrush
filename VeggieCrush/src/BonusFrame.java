import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.utils.Bonus;
import com.utils.GestionBonus;
import com.utils.MusicPlayer;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class BonusFrame implements ActionListener {
	
	private JFrame frame;
	private static String UUID;

	private JPanel contentPane;
	private JLabel lblBonusHowob;
	private JLabel lblBonusBoomCraft;
	private JLabel lblBonusFarmVillage;
	private static boolean bonusHowob=false;
	private static boolean bonusBoomCraft=false;
	private static boolean bonusFarmVillage=false;
	private JButton btnValider;
	private GestionBonus gestionBonus = new GestionBonus();
	private ArrayList<Bonus> listeBonus;
	
	private boolean run=true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BonusFrame window = new BonusFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BonusFrame(String UUID) {
		this.UUID = UUID;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public BonusFrame() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame("Bonus disponibles");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow,center][grow]", "[grow][grow][grow][grow][center]"));
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Choisissez vos bonus :");
		contentPane.add(lblNewLabel, "cell 0 0");
		
		JToggleButton tglbtnBonusHowob = new JToggleButton("Bonus HOWOB");
		tglbtnBonusHowob.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange()==ItemEvent.SELECTED){
					lblBonusHowob.setVisible(true);
					bonusHowob = true;
				} else if(ev.getStateChange()==ItemEvent.DESELECTED){
					lblBonusHowob.setVisible(false);
					bonusHowob = false;
				}
			}
		});
		contentPane.add(tglbtnBonusHowob, "cell 0 1");
		
		lblBonusHowob = new JLabel("+15 sec au timer");
		lblBonusHowob.setVisible(false);
		contentPane.add(lblBonusHowob, "cell 1 1");
		
		JToggleButton tglbtnBonusFarmvillage = new JToggleButton("Bonus FarmVillage");
		tglbtnBonusFarmvillage.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange()==ItemEvent.SELECTED){
					lblBonusFarmVillage.setVisible(true);
					bonusFarmVillage = true;
				} else if(ev.getStateChange()==ItemEvent.DESELECTED){
					lblBonusFarmVillage.setVisible(false);
					bonusFarmVillage = false;
				}
			}
		});
		contentPane.add(tglbtnBonusFarmvillage, "cell 0 2");
		
		lblBonusFarmVillage = new JLabel("+500 pts de score");
		lblBonusFarmVillage.setVisible(false);
		contentPane.add(lblBonusFarmVillage, "cell 1 2");
		
		JToggleButton tglbtnBonusBoomCraft = new JToggleButton("Bonus BoomCraft");
		tglbtnBonusBoomCraft.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange()==ItemEvent.SELECTED){
					lblBonusBoomCraft.setVisible(true);
					bonusBoomCraft = true;
				} else if(ev.getStateChange()==ItemEvent.DESELECTED){
					lblBonusBoomCraft.setVisible(false);
					bonusBoomCraft = false;
				}
			}
		});
		contentPane.add(tglbtnBonusBoomCraft, "cell 0 3");
		
		lblBonusBoomCraft = new JLabel("+10 coups");
		lblBonusBoomCraft.setVisible(false);
		contentPane.add(lblBonusBoomCraft, "cell 1 3");
		
		btnValider = new JButton("Valider");
		btnValider.addActionListener(this);
		btnValider.setActionCommand("valider");
		contentPane.add(btnValider, "cell 0 4");
		
		Thread t = new Thread() {
			public void run() {
				while(run) {
					listeBonus = gestionBonus.recupererBonus(getUUID());
					System.out.println(getUUID());

					for (Bonus bonus : listeBonus) {
						if(bonus.getPossedeBonus()) {
							switch(bonus.getNomJeu()) {
							case "howob" : 
								tglbtnBonusHowob.setEnabled(true);
								break;
							case "farmvillage" : 
								tglbtnBonusFarmvillage.setEnabled(true);
								break;
							case "boomcraft" : 
								tglbtnBonusBoomCraft.setEnabled(true);
								break;
							}
						} else {
							switch(bonus.getNomJeu()) {
							case "howob" : 
								bonusHowob = false;
								tglbtnBonusHowob.setEnabled(false);
								break;
							case "farmvillage" : 
								bonusFarmVillage = false;
								tglbtnBonusFarmvillage.setEnabled(false);
								break;
							case "boomcraft" : 
								bonusBoomCraft = false;
								tglbtnBonusBoomCraft.setEnabled(false);
								break;
							}
						}
					}
					
					listeBonus.clear();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("valider")) {

				new MainFrame(UUID, bonusBoomCraft, bonusFarmVillage, bonusHowob);
				
				this.run=false;
				this.frame.dispose();
			}
		}
	}
	
	public static String getUUID() {
		return BonusFrame.UUID;
	}
}
