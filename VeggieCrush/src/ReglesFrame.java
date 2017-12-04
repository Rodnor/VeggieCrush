import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.omg.CORBA.INITIALIZE;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class ReglesFrame {

	private static JFrame frame=null;
	private static JPanel contentPane;

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
	
	public static void initialize() {
		frame = new JFrame("Règles du jeu");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 819, 774);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		frame.setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[120px:120px:120px][grow]", "[][][grow]"));
		
		JLabel lblFentreDeJeu = new JLabel("Fenêtre de jeu");
		panel.add(lblFentreDeJeu, "cell 0 0");
		
		JTextPane txtpnLoremIpsumDolor = new JTextPane();
		try {
			StyledDocument document = (StyledDocument)txtpnLoremIpsumDolor.getDocument();
	        document.insertString(document.getLength(), "Le principe du jeu est simple. Il vous faudra récolter le plus de plantes possibles. Pour cela, il faut créer des chemins de cases adjacentes pour des plantes de même couleur. Les cases doivent se toucher par la droite, la gauche, le haut ou le bas pour être comptabilisées dans le chemin de chaque couleur. Vous pouvez récolter 4 types de ressources et les chemins doivent obligatoirement démarrer d'un des coins de la grille. Pour que les plantes se touchent, votre seule possibilité est d'échanger la position des plantes souhaitées en cliquant sur l'une puis sur l'autre.\n\nA chaque partie, une grille de 9x9 cases est générée aléatoirement. Chaque coin de la grille sera cependant toujours de la même couleur. En haut à gauche,", null);
	        document.insertString(document.getLength(), " ce sera le point de départ pour récolter des plantes vertes. En haut à droite,", null);
	        document.insertString(document.getLength(), " ce sera le point de départ pour récolter des plantes rouge. En bas à droite,", null);
	        document.insertString(document.getLength(), " ce sera le point de départ pour récolter des plantes violettes. En bas à gauche,", null);
	        document.insertString(document.getLength(), " ce sera le point de départ pour récolter des plantes jaunes. Les chemins de couleur démarrant de tout autre endroit ne seront pas pris en compte.\n\n", null);
	        document.insertString(document.getLength(), "Votre tâche sera compliquée par plusieurs facteurs. De base, votre temps sera limité à 30 secondes, vous n'aurez droit qu'à 25 coups pour échanger les plantes et votre score démarrera à 0. Vous pourez augmenter ces quantités via des bonus en provenance des 3 autres jeux.\n\n", null);
	        document.insertString(document.getLength(), "Par défaut, chaque case du chemin rapporte 20 points. Lorsque le chemin atteint 10 cases, les points pour la couleur de celui-ci sont doublés. Mais il n'est pas garanti de récolter toutes les plantes des chemins, la récolte sera conditionnée par votre score.\n\n", null);
	        document.insertString(document.getLength(), "Si celui-ci, calculé en fin de partie, est compris entre 0 et 500, vous ne récolterez aucune plante. Entre 500 et 1000, vous récolterez 50% des plantes composants vos chemins. Entre 1000 et 2000 points, vous récolterez 100% des plantes composants vos chemins. Au délà, vous en récolterez le double.\n\n", null);
	        document.insertString(document.getLength(), "Vous savez tout maintenant, bon jeu !", null);
		} catch (BadLocationException e){
            e.printStackTrace();
        }
		
		panel.add(txtpnLoremIpsumDolor, "cell 1 2,grow");
		txtpnLoremIpsumDolor.setEditable(false);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[120px:120px:120px][grow]", "[16px][grow]"));
		
		JLabel lblFentreDeCraft = new JLabel("Fenêtre de craft");
		panel_1.add(lblFentreDeCraft, "cell 0 0,alignx left,aligny top");
		
		JTextPane textPane = new JTextPane();
		panel_1.add(textPane, "cell 1 1,grow");
	}
	
	public static JFrame getInstance() {
		if (frame == null) { 	
			initialize();
		}
		frame.setVisible(true);
		return frame;
	}
}