import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.HttpClient;
import com.utils.Utils;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;

public class CreationCompteFrame implements ActionListener {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField pseudo;
	private JTextField mail;
	private JPasswordField mdp;
	private JPasswordField mdp_confirm;
	private AccountDao adao = new AccountDao();
	private JLabel lblFaction;
	private JPanel panel;
	private JRadioButton rdbtnFaction;
	private JRadioButton rdbtnFaction_1;
	private String faction;

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
		frame.setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.setTitle("Créer un compte");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		contentPane.setLayout(new MigLayout("", "[grow][grow,center][grow]", "[][][][grow][][][][][][][grow,bottom]"));

		JLabel lblPseudo = new JLabel("Pseudo :");
		contentPane.add(lblPseudo, "cell 1 0");

		pseudo = new JTextField();
		contentPane.add(pseudo, "cell 1 1,growx");
		pseudo.setColumns(10);

		lblFaction = new JLabel("Faction : ");
		contentPane.add(lblFaction, "cell 1 2");

		panel = new JPanel();
		contentPane.add(panel, "cell 1 3,growx,aligny center");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rdbtnFaction = new JRadioButton("Lumiere");
		rdbtnFaction.addActionListener(this);
		rdbtnFaction.setActionCommand("Lumiere");
		panel.add(rdbtnFaction);

		rdbtnFaction_1 = new JRadioButton("Ombre");
		rdbtnFaction_1.addActionListener(this);
		rdbtnFaction_1.setActionCommand("Ombre");
		panel.add(rdbtnFaction_1);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnFaction);
		group.add(rdbtnFaction_1);

		JLabel lblAdresseMail = new JLabel("Adresse mail : ");
		contentPane.add(lblAdresseMail, "cell 1 4");

		mail = new JTextField();
		contentPane.add(mail, "cell 1 5,growx");
		mail.setColumns(10);

		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		contentPane.add(lblMotDePasse, "cell 1 6");

		mdp = new JPasswordField();
		contentPane.add(mdp, "cell 1 7,growx");

		JLabel lblRetapezMotDe = new JLabel("Retapez mot de passe :");
		contentPane.add(lblRetapezMotDe, "cell 1 8");

		mdp_confirm = new JPasswordField();
		contentPane.add(mdp_confirm, "cell 1 9,growx");

		JButton btnCrerUnCompte = new JButton("Créer un compte");
		btnCrerUnCompte.addActionListener(this);
		btnCrerUnCompte.setActionCommand("creer");
		contentPane.add(btnCrerUnCompte, "cell 1 10");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		StringBuilder stringBuilder = new StringBuilder();
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if (btn.getActionCommand().equals("creer")) {
				if (!pseudo.getText().equals("") && !mail.getText().equals("") && Utils.validate(mail.getText())
						&& !String.valueOf(mdp.getPassword()).equals("")
						&& !String.valueOf(mdp_confirm.getPassword()).equals("")
						&& String.valueOf(mdp_confirm.getPassword()).equals(String.valueOf(mdp.getPassword()))) {
					// TODO, controle sur la faction
					Account account = new Account();
					Account accountmail = new Account();

					accountmail = adao.getAccountByMail(mail.getText());
					account = adao.getAccountByUsername(pseudo.getText());

					if (account == null && accountmail == null) {// n'existe pas chez nous

						if (Utils.creditsExistDansUneAutreAppli(pseudo.getText(), mail.getText()) == null) { // n'existe pas ailleurs

							String uuidString = Utils.generateUuid().toString();
							account = new Account(0, uuidString, pseudo.getText(), mail.getText(), String.valueOf(mdp.getPassword()), faction, null, null, null);
							HttpClient httpClient = new HttpClient();
							JSONObject jsonObject = httpClient.postRequestWithJsonParam("https://veggiecrush.masi-henallux.be/rest_server/api/account/insert", account.getJson());
							Boolean errorInsert = false;
							try {
								if (!jsonObject.isNull("error_insert")){
									errorInsert = (Boolean) jsonObject.get("error_insert");
								}
							} catch (JSONException error) {
								error.printStackTrace();
							}
							
							System.out.println("errorinsert"+errorInsert);
							if(errorInsert == false){
								new ConnectionFrame();
							}

							this.frame.dispose();
						} else {
							// Compte existe déja ailleurs
							JOptionPane.showMessageDialog(null,
									"Ce nom d'utilisateur ou cet email est déjà utilisé. Veuillez en utiliser un autre1",
									"Utilisateur invalide", JOptionPane.ERROR_MESSAGE, null);
						}
					} else {
						// Compte deja existant chez nous
						JOptionPane.showMessageDialog(null,
								"Ce nom d'utilisateur ou cet email est déjà utilisé. Veuillez en utiliser un autre2",
								"Utilisateur invalide", JOptionPane.ERROR_MESSAGE, null);
					}
				} else {
					if (pseudo.getText().equals("")) {

						stringBuilder.append("- Vous devez choisir un nom d'utilisateur");
					}
					
					if (faction == null) {
						if (stringBuilder.length() != 0) {
							stringBuilder.append("\n");
						}
						stringBuilder.append("- Vous devez selectionner une faction !");
					}
					
					if (mail.getText().equals("")) {
						if (stringBuilder.length() != 0) {
							stringBuilder.append("\n");
						}
						stringBuilder.append("- Vous devez saisir une adresse mail");
					}
					if (!Utils.validate(mail.getText())) {
						if (stringBuilder.length() != 0) {
							stringBuilder.append("\n");
						}
						stringBuilder.append("- Cette adresse mail n'a pas un format valide");
					}
					if (String.valueOf(mdp.getPassword()).equals("")
							|| String.valueOf(mdp_confirm.getPassword()).equals("")
							|| !(String.valueOf(mdp_confirm.getPassword()).equals(String.valueOf(mdp.getPassword())))) {
						if (stringBuilder.length() != 0) {
							stringBuilder.append("\n");
						}
						stringBuilder.append("- Les 2 mots de passe ne sont pas identiques");
					}

					System.out.println(stringBuilder.toString());
					JOptionPane.showMessageDialog(null, stringBuilder.toString(), "Erreur",
							JOptionPane.ERROR_MESSAGE, null);

				}
			}
		}

		if (e.getSource() instanceof JRadioButton) {
			JRadioButton btn = (JRadioButton) e.getSource();

			if (btn.getActionCommand().equals("Ombre")) {
				faction = "Ombre";
			} else {
				faction = "Lumière";
			}
		}
	}
}
