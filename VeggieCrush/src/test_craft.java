import java.awt.Color;

import javax.swing.JFrame;

import javax.swing.JTabbedPane;


public class test_craft extends JFrame {

  private JTabbedPane onglet;

   

  public test_craft(){

    this.setLocationRelativeTo(null);

    this.setTitle("Gérer vos conteneurs");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setSize(400, 200);

      

    //Création de plusieurs Panneau

    Panneau[] tPan = {   new Panneau(Color.RED), new Panneau(Color.GREEN), new Panneau(Color.BLUE)};

      

    //Création de notre conteneur d'onglets

    onglet = new JTabbedPane();

    int i = 0;

    for(Panneau pan : tPan){

      //Méthode d'ajout d'onglet

      onglet.add("Onglet n° "+(++i), pan);

      //Vous pouvez aussi utiliser la méthode addTab

      //onglet.addTab("Onglet n° "+(++i), pan);


    }

    //On passe ensuite les onglets au content pane

    this.getContentPane().add(onglet);

    this.setVisible(true);

  }

   

  public static void main(String[] args){

	  test_craft fen = new test_craft();
	  fen.setBounds(100, 100, 800, 600);
	  fen.setResizable(false);

  }   

}