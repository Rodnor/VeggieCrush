import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.Utils;

import net.miginfocom.swing.MigLayout;

/**
 * Classe responsable de la création de la fenêtre permettant de signaler la
 * perte de son mot de passe.
 * 
 * @author Tristan
 *
 */
public class MotDePassePerduFrame implements ActionListener {

	// Variables de classe
	private JPanel contentPane;
	private JTextField mail;
	private JFrame frame;
	private JLabel lblUnMailVous;
	private JButton btnEnvoyer;

	/**
	 * Lanceur de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotDePassePerduFrame window = new MotDePassePerduFrame();
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
	public MotDePassePerduFrame() {
		initialize();
	}

	/**
	 * Méthode créant les composants graphiques de la frame et permettant de les
	 * disposer.
	 */
	public void initialize() {
		// Paramétrages de la fenêtre
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][grow]", "[center][center][][grow,bottom]"));

		frame.setTitle("Récupérer mot de passe");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		JLabel lblAdresseMail = new JLabel("Adresse Mail : ");
		contentPane.add(lblAdresseMail, "cell 1 0,alignx center");

		mail = new JTextField();
		contentPane.add(mail, "cell 1 1,growx");
		mail.setColumns(10);

		btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(this);
		btnEnvoyer.setActionCommand("envoyer");
		contentPane.add(btnEnvoyer, "cell 1 3,alignx center");

		lblUnMailVous = new JLabel("Un mail vous a été envoyé");
		lblUnMailVous.setForeground(Color.BLUE);
		lblUnMailVous.setVisible(false);
		contentPane.add(lblUnMailVous, "cell 1 2,alignx center");
	}

	/**
	 * Méthode lancée lors de l'activation d'un composant muni d'un listener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si le composant est un JButton
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			// Si le bouton est le bouton "envoyer"
			if (btn.getActionCommand().equals("envoyer")) {
				// Si le champ mail n'est pas vide et que le format du mail est
				// valide
				if (!mail.getText().equals("")) {
					if (Utils.validate(mail.getText())) {

						// On créé un DAO permettant d'interagir avec la table
						// Account et on récupère le compte de l'utilisateur via
						// son adresse mail
						AccountDao accountDao = new AccountDao();
						Account account = accountDao.getAccountByMail(mail.getText());

						// Si le compte a été trouvé
						if (account != null) {
							// On lance la méthode permettant d'envoyer un mail
							// à l'utilisateur avec un nouveau mot de passe
							Utils.modfierMotDePasse(mail.getText());
							lblUnMailVous.setVisible(true);
							// On change l'aspect du bouton pour permettre à
							// l'utilisateur de comprendre qu'il peut quitter la
							// page par ce dernier
							btnEnvoyer.setText("Quitter");
							btnEnvoyer.setActionCommand("quitter");
							// Message d'erreur indiquant que l'adresse mail
							// n'est liée à aucun compte
						} else {
							JOptionPane.showMessageDialog(null, "Cette adresse mail n'est liée à aucun compte",
									"Mail invalide", JOptionPane.ERROR_MESSAGE, null);
						}
						// Message d'erreur indiquant un mauvais format
						// d'adresse mail
					} else {
						JOptionPane.showMessageDialog(null, "Cette adresse mail n'a pas un format valide",
								"Format mail invalide", JOptionPane.ERROR_MESSAGE, null);
					}
					// Message d'erreur indiquant un champ vide
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez spécifier votre adresse mail", "Champs manquant",
							JOptionPane.WARNING_MESSAGE, null);
				}
				// Si le bouton est le bouton "quitter" on ferme la fenêtre
				// courante
			} else if (btn.getActionCommand().equals("quitter")) {
				this.frame.dispose();
			}
		}
	}
}