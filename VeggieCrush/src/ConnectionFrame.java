import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.Utils;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPasswordField;

public class ConnectionFrame implements ActionListener {
	
	private JFrame frame;
	private JPanel contentPane;
	private JTextField tf_pseudo;
	private JPasswordField passwordField;
	private JButton btnCrerMonCompte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame window = new ConnectionFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConnectionFrame() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Connexion");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
	    contentPane.setLayout(new MigLayout("", "[grow,center][grow,center]", "[30px][30px][30px][30px][30px][grow]"));
	    
	    frame.setVisible(true);
	    frame.setResizable(false);
	    frame.setBounds(100, 100, 450, 300);		
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
		
		JLabel lblPseudo = new JLabel("Pseudo :");
		lblPseudo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPseudo, "cell 0 0 2 1,alignx center,growy");
		
		tf_pseudo = new JTextField();
		contentPane.add(tf_pseudo, "cell 0 1 2 1,alignx center");
		tf_pseudo.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMotDePasse, "cell 0 2 2 1");
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		Dimension dim = passwordField.getSize();
		dim.setSize(130, 0);
		passwordField.setPreferredSize(dim);
		contentPane.add(passwordField, "cell 0 3 2 1,alignx center,aligny center");
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(this);
		btnConnexion.setActionCommand("connexion");
		contentPane.add(btnConnexion, "cell 0 4 2 1,alignx center,growy");
		
		JLabel lblMDPOublie = new JLabel("Mot de passe oublié ?");
		lblMDPOublie.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblMDPOublie.setHorizontalAlignment(SwingConstants.CENTER);
		lblMDPOublie.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblMDPOublie.setCursor(Cursor.getDefaultCursor());
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lblMDPOublie.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// déclenchement de la procédure MDP oublié
				System.out.println("Coucou");
			}
		});
		contentPane.add(lblMDPOublie, "cell 1 5,grow");
		
		btnCrerMonCompte = new JButton("Créer mon compte");
		btnCrerMonCompte.addActionListener(this);
		btnCrerMonCompte.setActionCommand("creer");
		contentPane.add(btnCrerMonCompte, "cell 0 5");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("connexion")) {
				AccountDao adao = new AccountDao();
				
				String securePass = Utils.get_SHA_512_SecurePassword(passwordField.getText());
				//Account account = adao.getAccountById(1);
				Account account = adao.getAccountByUsername(tf_pseudo.getText());
				
				// test champs vides
				// test utilisateur présent dans la BD ou celle des autres
				// test correspondance username + MDP
				// TODO verfifier mot de passe dans une autre API
				if(account != null && Utils.usernameExistDansUneAutreAppli(tf_pseudo.getText(), securePass) == null && tf_pseudo.getText().equals(account.getUsername()) && securePass.equals(account.getPassword())) {
					MainFrame frame = new MainFrame();
					this.frame.dispose();
				} else {
					// popup échec connexion
					
					System.out.println("MiPa");
				}
			} else if (btn.getActionCommand().equals("creer")) {
				CreationCompteFrame creationCompteFrame = new CreationCompteFrame();
			}
		}
	}

}