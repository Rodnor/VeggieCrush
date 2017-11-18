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
import com.entitie.Inventaire;

public class InventaireDao {
	
	private final static String QUERY_FIND_ALL = "SELECT * FROM INVENTAIRE";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM INVENTAIRE WHERE id_user = ?";

	final static Logger logger = Logger.getLogger(InventaireDao.class.getName());

	public ArrayList<Inventaire> getAllInventaires() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Inventaire inventaire = new Inventaire();
			while (rset.next()) {
				inventaire = mappingInventaire(rset);
				inventaires.add(inventaire);
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
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return inventaires;
	}

	public ArrayList<Inventaire> getInventaireByIdAccount(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			Inventaire inventaire = new Inventaire();
			while (rset.next()) {
				logger.debug("MiPa, une ligne trouvée");
				inventaire = mappingInventaire(rset);
				
				inventaire = mappingInventaire(rset);
				inventaires.add(inventaire);
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
		return inventaires;
	}
	
	private Inventaire mappingInventaire(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final int id_user = rset.getInt("id_user");
		final int qte = rset.getInt("qte");
		final Inventaire inventaire = new Inventaire(id_user, id_objet, qte);
		logger.debug("construction de l'objet" + inventaire.toString());
		return inventaire;
	}

	public Boolean insertNewInventaire(Inventaire inventaire) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		
		logger.info("MiPA insertNewAccount");

		try {
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			// the mysql insert statement
			String query = "INSERT INTO ACCOUNT (id_global, id_faction, username, password, email, created_at, updated_at, deleted_at)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			//VALUES ('0', '85', '1', 'pouloulou', 'michard', 'ouh@lol.fr', '20130405', '2013-10-20 20:18:01', '');

			// create the mysql insert preparedstatement
			/*stmt = con.prepareStatement(query);
			stmt.setString(1, account.getGlobalID());
			stmt.setInt(2, account.getId_faction());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword()); // TODO
			stmt.setString(5, account.getEmail());
			stmt.setDate(6, startDate);
			stmt.setDate(7, null);//df.format(account.getCreatedAT()));
			stmt.setDate(8, null);//null); */

			// execute the preparedstatement
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					errorInsert = true;
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					errorInsert = false;
					e.printStackTrace();
				}
			}
		}
		return errorInsert;
	}
}
