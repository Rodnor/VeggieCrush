import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
// MIPA COMMENTAIRE
public class MainFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("Récupérer matières", null, panel1, null);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTest = new JLabel("test");
		panel1.add(lblTest);
		
		JPanel panel2 = new JPanel();
		tabbedPane.addTab("Crafter", null, panel2, null);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTest2 = new JLabel("test 2");
		panel2.add(lblTest2);
		
		JPanel panel3 = new JPanel();
		tabbedPane.addTab("Demandes", null, panel3, null);
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTest3 = new JLabel("test3");
		panel3.add(lblTest3);
	}

}
