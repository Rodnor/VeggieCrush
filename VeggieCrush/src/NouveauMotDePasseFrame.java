import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class NouveauMotDePasseFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame frame;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private String utilisateur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauMotDePasseFrame window = new NouveauMotDePasseFrame("");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NouveauMotDePasseFrame(String utilisateur) {
		this.utilisateur = utilisateur;
		initialize();
	}
	
	public void initialize() {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("valider")) {
				if(!String.valueOf(passwordField.getPassword()).equals("") && String.valueOf(passwordField_1.getPassword()).equals("")) {
					if(String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword()))) {
						// TODO Modification en base MPA
						
						MainFrame frame = new MainFrame();
						this.frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Les 2 mots de passe ne sont pas identiques", "Erreur de correspondance", JOptionPane.ERROR_MESSAGE, null);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez remplir les 2 champs de mot de passe", "Champs manquant", JOptionPane.WARNING_MESSAGE, null);
				}
			}
		}
	}
}