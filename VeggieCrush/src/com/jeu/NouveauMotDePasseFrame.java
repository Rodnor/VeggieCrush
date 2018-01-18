package com.jeu;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.HttpClient;

import net.miginfocom.swing.MigLayout;

/**
 * Classe responsable de la création de la fenêtre permettant de changer le mot
 * de passe d'un utilisateur.
 * 
 * @author Tristan
 *
 */
public class NouveauMotDePasseFrame implements ActionListener {

	// Variables de classe
	private JPanel contentPane;
	private JFrame frame;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private String utilisateur;
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * Lanceur de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauMotDePasseFrame window = new NouveauMotDePasseFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructeur de la frame.
	 */
	public NouveauMotDePasseFrame() {
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 * 
	 * @param utilisateur
	 *            String contenant l'identifiant de l'utilisateur instanciant la
	 *            fenêtre.
	 */
	public NouveauMotDePasseFrame(String utilisateur) {
		this.utilisateur = utilisateur;
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 */
	public void initialize() {
		// Paramétrages de la fenêtre
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow,center][grow]", "[][][][][grow,bottom]"));

		JLabel lblNouveauMotDe = new JLabel("Nouveau mot de passe :");
		contentPane.add(lblNouveauMotDe, "cell 1 0");

		passwordField = new JPasswordField();
		contentPane.add(passwordField, "cell 1 1,growx");

		JLabel lblRetapezLeMot = new JLabel("Retapez le mot de passe :");
		contentPane.add(lblRetapezLeMot, "cell 1 2");

		passwordField_1 = new JPasswordField();
		contentPane.add(passwordField_1, "cell 1 3,growx");

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(this);
		btnValider.setActionCommand("valider");
		contentPane.add(btnValider, "cell 1 4");

		frame.setTitle("Réinitialiser le mot de passe");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Méthode lancée lors de l'activation d'un composant muni d'un listener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si le composant est un JButton
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			// Si le bouton est le bouton "valider"
			if (btn.getActionCommand().equals("valider")) {
				// Si les champs mot de passe 1 et 2 ne sont pas vides et s'ils
				// correspondent
				if (!String.valueOf(passwordField.getPassword()).equals("")
						&& !String.valueOf(passwordField_1.getPassword()).equals("")) {
					if (String.valueOf(passwordField.getPassword())
							.equals(String.valueOf(passwordField_1.getPassword()))) {

						// On créé un DAO permettant d'intéragir avec la table
						// Account
						AccountDao accountDao = new AccountDao();
						Account account = new Account();
						// On cherche le compte de l'utilisateur courant
						account = accountDao.getAccountByUsername(utilisateur);
						// accountDao.updatePasswordById(account.getId(), );

						// On construit la requête vers l'API qui se charge
						// d'envoyer les paramètres de modification
						HttpClient httpClient = new HttpClient();
						JSONObject jsonEnvoi = new JSONObject();
						try {
							jsonEnvoi.put("id", account.getId());
							jsonEnvoi.put("password", String.valueOf(passwordField.getPassword()));

						} catch (JSONException error) {
							error.printStackTrace();
						}

						// On envoie la requête
						httpClient.postRequestWithJsonParamAndToken(
								applicationProperties.getString("api.url.account.insert.update.password"), jsonEnvoi,
								applicationProperties.getString("api.token.secure"));

						// On indique dans la base que le compte de cet
						// utilisateur n'est plus à modifier, on lance la
						// fenêtre suivante et on ferme la fenêtre courante
						accountDao.updateFlag(account.getId(), "N");
						new BonusFrame(account.getId_global());
						this.frame.dispose();
						// Si les 2 mots de passe ne correspondent pas, message
						// d'erreur
					} else {
						JOptionPane.showMessageDialog(null, "Les 2 mots de passe ne sont pas identiques",
								"Erreur de correspondance", JOptionPane.ERROR_MESSAGE, null);
					}
					// Si l'un des 2 champs est vide, message d'erreur
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez remplir les 2 champs de mot de passe",
							"Champs manquant", JOptionPane.WARNING_MESSAGE, null);
				}
			}
		}
	}
}