package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.bd.Connecteur;
import com.entitie.Account;
import com.entitie.Objet;
import com.entitie.Recette;
import com.entitie.TypeObjet;

/**
 * Classe permettant la gestion des RECETTES
 */
public class RecetteDao {

	private final static String QUERY_INSERT = "INSERT INTO RECETTE  (id_objet, nom_recette, type, description, qte_plante1, qte_plante2, qte_plante3, qte_plante4) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_DELETE_BY_ID = "DELETE FROM RECETTE WHERE id_recette = ?";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM RECETTE WHERE id_recette = ?";
	private final static String QUERY_FIND_ALL = "SELECT * FROM RECETTE";
	private final static String QUERY_FIND_ID_RECETTE_BY_COMPO = "SELECT id_objet FROM RECETTE where qte_plante1=? and qte_plante2=? and qte_plante3=? and qte_plante4=?";

	private final static Logger logger = Logger.getLogger(ObjetDao.class.getName());

	/**
	 * Permet de recherche une recette en fonction de son id
	 * 
	 * @param idRecette
	 * @return recette
	 */
	public Recette getRecetteById(int idRecette) {
		Connection con = null;
		PreparedStatement stmt = null;
		Recette recette = new Recette();

		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, idRecette);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				recette = mappingRecette(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return recette;
	}
	
	/**
	 * Permet de recherche une recette en fonction de son id
	 * 
	 * @param idRecette
	 * @return recette
	 */
	public int getIdRecetteByComposants(int qte1, int qte2, int qte3, int qte4) {
		Connection con = null;
		PreparedStatement stmt = null;
		int resultat=0;		
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_ID_RECETTE_BY_COMPO);
			stmt.setInt(1, qte1);
			stmt.setInt(2, qte2);
			stmt.setInt(3, qte3);
			stmt.setInt(4, qte4);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				resultat = rset.getInt("id_objet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return resultat;
	}

	/**
	 * Permet de récupérer toutes les recettes de la base
	 * 
	 * @return ArrayList<Recette>
	 */
	public ArrayList<Recette> getRecettes() {
		Connection con = null;
		Statement stmt = null;
		Recette recette = new Recette();
		ArrayList<Recette> recettes = new ArrayList<Recette>();

		try {
			con = Connecteur.getConnexion();
			stmt = con.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			while (rset.next()) {
				recette = mappingRecette(rset);
				recettes.add(recette);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return recettes;
	}

	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * 
	 * @param rset
	 * @return recette
	 * @throws SQLException
	 */
	private Recette mappingRecette(final ResultSet rset) throws SQLException {

		final int id_recette = rset.getInt("id_recette");
		final int id_objet = rset.getInt("id_objet");
		final String nom_recette = rset.getString("nom_recette");
		final String type = rset.getString("type");
		final String descritpion = rset.getString("description");
		final int qte1 = rset.getInt("qte_plante1");
		final int qte2 = rset.getInt("qte_plante2");
		final int qte3 = rset.getInt("qte_plante3");
		final int qte4 = rset.getInt("qte_plante4");

		Recette recette = new Recette(id_recette, id_objet, nom_recette, type, descritpion, qte1, qte2, qte3, qte4);

		return recette;
	}

	/**
	 * Permet d'insérer une nouvelle ligne dans la table RECETTE
	 * 
	 * @param objet
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public Boolean insertNewrecette(Recette recette) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();

			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setInt(1, recette.getIdObjet());
			stmt.setString(2, recette.getNomRecette());
			stmt.setString(3, recette.getType());
			stmt.setString(4, recette.getDescription());
			stmt.setInt(5, recette.getQte1());
			stmt.setInt(6, recette.getQte2());
			stmt.setInt(7, recette.getQte3());
			stmt.setInt(8, recette.getQte4());

			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			errorInsert = true;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return errorInsert;
	}
}
