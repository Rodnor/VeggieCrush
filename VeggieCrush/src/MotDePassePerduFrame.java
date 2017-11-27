import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dao.AccountDao;
import com.entitie.Account;
import com.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Color;

public class MotDePassePerduFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField mail;
	private JFrame frame;
	private JLabel lblUnMailVous;

	/**
	 * Launch the application.
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
	
	public MotDePassePerduFrame() {
		initialize();
	}

	public void initialize() {
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
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(this);
		
		lblUnMailVous = new JLabel("Un mail vous a été envoyé");
		lblUnMailVous.setForeground(Color.BLUE);
		lblUnMailVous.setVisible(false);
		contentPane.add(lblUnMailVous, "cell 1 2,alignx center");
		
		
		
		btnEnvoyer.setActionCommand("envoyer");
		contentPane.add(btnEnvoyer, "cell 1 3,alignx center");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("envoyer")) {
				if(!mail.getText().equals("")){
					if(Utils.validate(mail.getText())) {
						AccountDao accountDao = new AccountDao();
						Account account = accountDao.getAccountByMail(mail.getText());
						
						if(account != null) {
							Utils.modfierMotDePasse(mail.getText());
							lblUnMailVous.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Cette adresse mail n'est liée à aucun compte", "Mail invalide", JOptionPane.ERROR_MESSAGE, null);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Cette adresse mail n'a pas un format valide", "Format mail invalide", JOptionPane.ERROR_MESSAGE, null);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vous devez spécifier votre adresse mail", "Champs manquant", JOptionPane.WARNING_MESSAGE, null);
				}
			}
		}
	}
}