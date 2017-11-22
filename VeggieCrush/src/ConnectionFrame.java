import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class ConnectionFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tf_pseudo;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame frame = new ConnectionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConnectionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("Connexion");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
		contentPane.setLayout(new MigLayout("", "[grow][200px,grow][grow]", "[30px][30px][30px][30px][30px][grow]"));
		
		JLabel lblPseudo = new JLabel("Pseudo :");
		lblPseudo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPseudo, "cell 1 0,grow");
		
		tf_pseudo = new JTextField();
		contentPane.add(tf_pseudo, "cell 1 1,grow");
		tf_pseudo.setColumns(10);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe :");
		lblMotDePasse.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMotDePasse, "cell 1 2,grow");
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField, "cell 1 3,growx");
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(this);
		btnConnexion.setActionCommand("connexion");
		contentPane.add(btnConnexion, "cell 1 4,grow");
		
		JLabel lblMDPOublie = new JLabel("Mot de passe oublié ?");
		lblMDPOublie.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblMDPOublie.setHorizontalAlignment(SwingConstants.RIGHT);
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
				System.out.println("Coucou");
			}
		});
		contentPane.add(lblMDPOublie, "cell 1 5,grow");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();

			if(btn.getActionCommand().equals("connexion")) {
				if(tf_pseudo.getText().equals("chaton") && String.valueOf(passwordField.getPassword()).equals("biche")) {
					MainFrame frame = new MainFrame();
					this.dispose();
				}
			}
		}
	}

}
