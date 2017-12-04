import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class ReglesFrame {

	private JFrame frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReglesFrame window = new ReglesFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReglesFrame() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame("Règles du jeu");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		JLabel lblFentreDeJeu = new JLabel("Fenêtre de jeu");
		panel.add(lblFentreDeJeu, "cell 0 0");
		
		JTextPane txtpnLoremIpsumDolor = new JTextPane();
		txtpnLoremIpsumDolor.setText("\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ac tellus at lorem lobortis consequat sit amet ut dui. Praesent non tempus nunc. Donec ut fermentum sem. Pellentesque aliquet mi quis nisl bibendum interdum. Maecenas pretium aliquet lacinia. Vestibulum a mi quis nibh dictum gravida. Ut eu mi sodales, tincidunt nisi id, eleifend sem. In elementum pellentesque nisi, ac mattis tellus vestibulum non. Maecenas ultrices augue vitae dui condimentum pellentesque. Vivamus dictum dui quis turpis rutrum, in interdum elit varius.\n\nDonec consequat ornare odio nec congue. Nunc eu enim quis dolor fermentum aliquam. Curabitur placerat risus nec sapien dapibus, non lacinia ipsum vulputate. In vel imperdiet libero. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Vestibulum a ultricies odio, in imperdiet enim. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Ut felis orci, mattis fermentum ligula ut, sagittis tincidunt ex.\n\nAliquam porttitor leo ut quam convallis vulputate. Etiam auctor erat ante, convallis rhoncus orci euismod ut. Etiam non rutrum ipsum. Maecenas aliquam magna nec cursus tincidunt. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque et rhoncus ante. Pellentesque quis ante quis neque pharetra placerat. Duis rhoncus tincidunt eros, vitae eleifend lacus fermentum id. Donec efficitur, lacus sit amet suscipit hendrerit, ante nunc dictum sem, quis egestas sapien nulla ac lacus. Pellentesque gravida est sit amet nibh laoreet imperdiet. Aliquam vehicula mattis auctor. Nulla a porta est. Integer mattis urna in mattis dictum. ");
		panel.add(txtpnLoremIpsumDolor, "cell 1 2,grow");
		txtpnLoremIpsumDolor.setEditable(false);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 1,grow");
	}

}
