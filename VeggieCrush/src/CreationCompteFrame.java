import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.Utils;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class CreationCompteFrame implements ActionListener {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField pseudo;
	private JTextField mail;
	private JPasswordField mdp;
	private JPasswordField mdp_confirm;
	private AccountDao adao = new AccountDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreationCompteFrame window = new CreationCompteFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CreationCompteFrame() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
	    frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.setTitle("Créer un compte");
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		
		contentPane.setLayout(new MigLayout("", "[grow][grow,center][grow]", "[][][][][][][][][grow,bottom]"));
		
		JLabel lblPseudo = new JLabel("Pseudo :");
		contentPane.add(lblPseudo, "cell 1 0");
		
		pseudo = new JTextField();
		contentPane.add(pseudo, "cell 1 1,growx");
		pseudo.setColumns(10);
		
		JLabel lblAdresseMail = new JLabel("Adresse mail : ");
		contentPane.add(lblAdresseMail, "cell 1 2");
		
		mail = new JTextField();
		contentPane.add(mail, "cell 1 3,growx");
		mail.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		contentPane.add(lblMotDePasse, "cell 1 4");
		
		mdp = new JPasswordField();
		contentPane.add(mdp, "cell 1 5,growx");
		
		JLabel lblRetapezMotDe = new JLabel("Retapez mot de passe :");
		contentPane.add(lblRetapezMotDe, "cell 1 6");
		
		mdp_confirm = new JPasswordField();
		contentPane.add(mdp_confirm, "cell 1 7,growx");
		
		JButton btnCrerUnCompte = new JButton("Créer un compte");
		btnCrerUnCompte.addActionListener(this);
		btnCrerUnCompte.setActionCommand("creer");
		contentPane.add(btnCrerUnCompte, "cell 1 8");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("creer")) {
				if(!pseudo.getText().equals("") && !mail.getText().equals("") && !String.valueOf(mdp.getPassword()).equals("") && !String.valueOf(mdp_confirm.getPassword()).equals("")) {
					// test regexp sur adresse mail + test sur pseudo ou UUID
					// test si personne existe dans la BD et dans celle des autres. Si non, on insère. Si oui, message comme quoi personne existe déjà
					// test sur les mdp s'ils sont identiques
					Account account = new Account();
					
					String securePass = Utils.get_SHA_512_SecurePassword(String.valueOf(mdp.getPassword()));

					account = adao.getAccountByUsername(pseudo.getText());
					if (account == null){ 
						if(Utils.usernameExistDansUneAutreAppli(pseudo.getText(), securePass) == null) {// ()
							//rajouter le test sur les autre appli Utils.usernameExistDansUneAutreAppli(pseudo.getText());
							// creer un compte
							
							String uuidString = Utils.generateUuid().toString();
							System.out.println("On peut créer avec uuid "+uuidString);
						} else { 
							System.out.println("On ne peut pas créer, existe deja chez les autres ");
							// erreur, existe chez les autres + retourne le nom de l'Appli
						}
						
						// créer 
					} else {
						System.out.println("On ne peut pas créer, existe déjà");

						// erreur , deja existant chez nous
						
					}
					//Account account = new Account(id, id_global, username, email, password, id_faction, created_at, updated_at, deleted_at);
					//adao.insertNewAccount(account);
					
					// message compte créé
					
					this.frame.dispose();
				} else {
					// popup manque des champs
				}
			}
		}
	}

}
