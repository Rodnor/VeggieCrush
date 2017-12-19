import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.dao.AccountDao;
import com.utils.Utils;

import net.miginfocom.swing.MigLayout;

/**
 * Classe responsable de la création de la fenêtre permettant de se connecter au
 * jeu.
 * 
 * @author Tristan
 *
 */
public class ConnectionFrame implements ActionListener {

	// Variables de classe
	private JFrame frame;
	private JPanel contentPane;
	private JTextField tf_pseudo;
	private JPasswordField passwordField;
	private JButton btnCrerMonCompte;
	private boolean flag = false;

	/**
	 * Lanceur de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// On cherche à savoir si le serveur est joignable. Si oui,
					// la fenêtre est instanciée
					if (Utils.canConnectApi()) {
						ConnectionFrame window = new ConnectionFrame();
						window.frame.setVisible(true);
						// Si le serveur n'est pas joignable, un message
						// d'erreur est affiché
					} else {
						JOptionPane.showMessageDialog(null,
								"Les serveurs du jeu sont innaccesibles. Veuillez verifier votre connexion internet.",
								"Echec de connexion", JOptionPane.ERROR_MESSAGE, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructeur de la frame.
	 */
	public ConnectionFrame() {
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 */
	private void initialize() {
		// Paramétrages de la fenêtre
		frame = new JFrame();
		frame.setTitle("Connexion");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[grow,center][grow,center]", "[30px][30px][30px][30px][30px][grow]"));

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
		// Ajout d'un MouseListener qui permet de changer l'image du curseur au
		// survol du label "Mot de passe oublié ?"
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
				new MotDePassePerduFrame();
			}
		});
		contentPane.add(lblMDPOublie, "cell 1 5,grow");

		btnCrerMonCompte = new JButton("Créer mon compte");
		btnCrerMonCompte.addActionListener(this);
		btnCrerMonCompte.setActionCommand("creer");
		contentPane.add(btnCrerMonCompte, "cell 0 5");
	}

	/**
	 * Méthode lancée lors de l'activation d'un composant muni d'un listener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si le composant est un JButton
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			// Si le bouton est le bouton "connexion"
			if (btn.getActionCommand().equals("connexion")) {
				// Création du DAO permettant d'intéragir avec la table Account
				AccountDao adao = new AccountDao();

				// Si le pseudo et le mot de passe ne sont pas vides
				if (!tf_pseudo.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("")) {
					// On cherche dans notre base et dans la base des autres
					// groupes si le compte de l'utilisateur existe
					String uuidTrouveAutre = Utils.signinDansUneAutreAppli(tf_pseudo.getText(),
							String.valueOf(passwordField.getPassword()));
					String uuidTrouve = Utils.signinVeggie(tf_pseudo.getText(),
							String.valueOf(passwordField.getPassword()));

					// Si le compte existe, on cherche si le compte est en
					// demande de mot de passe perdu ou non
					if (uuidTrouveAutre != null || uuidTrouve != null) {
						flag = adao.motDePasseAChanger(tf_pseudo.getText());

						// S'il n'y a pas de problème de mot de passe
						if (!flag) {
							// On lance la fenêtre de bonus avec le bon
							// identifiant utilisateur en fonction d'où le
							// compte se situe puis on ferme la fenêtre courante
							if (uuidTrouveAutre != null) {
								new BonusFrame(uuidTrouveAutre);
							} else {
								new BonusFrame(uuidTrouve);
							}
							this.frame.dispose();
							// Si une demande de mot de passe perdu a été
							// formulée, on oriente le joueur sur la page pour
							// choisir le nouveau mot de passe
						} else {
							new NouveauMotDePasseFrame(tf_pseudo.getText());
						}
						// Si le compte utilisateur n'existe chez personne,
						// message d'erreur
					} else {
						JOptionPane.showMessageDialog(null,
								"Votre nom d'utilisateur et/ou votre mot de passe sont invalides",
								"Identifiants invalides", JOptionPane.ERROR_MESSAGE, null);
					}
					// Si le pseudo est le mot de passe sont vides, message
					// d'erreur
				} else {
					JOptionPane.showMessageDialog(null, "Les champs doivent être remplis", "Champs manquants",
							JOptionPane.WARNING_MESSAGE, null);
				}
				// Si le bouton "créer" est cliqué, on lance la fenêtre de
				// création de compte
			} else if (btn.getActionCommand().equals("creer")) {
				new CreationCompteFrame();
			}
		}
	}
}