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
import com.entitie.Inventaire;
import com.entitie.Objet;
import com.entitie.TypeObjet;

public class ObjetDao {
	private final static String QUERY_FIND_ALL = "SELECT * FROM OBJET";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM OBJET WHERE id_objet = ?";
	private final static String QUERY_FIND_BY_ID_ACCOUNT = "SELECT * FROM ACCOUNT "
															+ "INNER JOIN INVENTAIRE ON ACCOUNT.id = INVENTAIRE.id_user "
															+ "INNER JOIN OBJET ON INVENTAIRE.id_objet = OBJET.id_objet " + "WHERE INVENTAIRE.id_user = ?";
	private final static String QUERY_COUNT_BY_ID_ACCOUNT_AND_BY_ID_OBJET = 	"SELECT count(*) as nombre_objet FROM INVENTAIRE WHERE id_user = ? AND id_objet = ?";
	private final static String QUERY_INSERT = "INSERT INTO OBJET (id_objet, name_objet, type_objet) values (?, ?, ?)";
	private final static String QUERY_DELETE_BY_ID = "DELETE FROM OBJET WHERE id_objet = ?";


	final static Logger logger = Logger.getLogger(ObjetDao.class.getName());

	public ArrayList<Objet> getAllObjets() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Objet> objets = new ArrayList<Objet>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Objet objet = new Objet();
			while (rset.next()) {
				objet = mappingObjet(rset);
				objets.add(objet);
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
		return objets;
	}

	public Objet getObjetById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Objet objet = new Objet();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);
			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				logger.debug("MiPa, une ligne trouvée");
				objet = mappingObjet(rset);
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
		return objet;
	}

	public ArrayList<Objet> getObjetByIdAccount(int idAccount) {
		logger.debug("MiPa getObjetByIdAccount" + idAccount);
		Connection con = null;
		PreparedStatement stmt = null;

		ArrayList<Objet> objets = new ArrayList<Objet>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID_ACCOUNT);
			stmt.setInt(1, idAccount);
			logger.error("DEBUG REQ" + stmt.toString());

			Objet objet = new Objet();

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				logger.debug("MiPa, une ligne trouvée");
				objet = mappingObjet(rset);
				objets.add(objet);
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
		return objets;
	}

	private Objet mappingObjet(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final String nom_objet = rset.getString("name_objet");
		final String nom_type_objet = rset.getString("type_objet");
		final TypeObjet type_objet = TypeObjet.valueOf(nom_type_objet);// TypeObjet.POTION;
		final Objet objet = new Objet(id_objet, nom_objet, type_objet);

		logger.debug("construction de l'objet" + objet.toString());

		return objet;
	}

	public Boolean insertNewObjet(Objet objet) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();

			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setInt(1, objet.getId_objet());
			stmt.setString(2, objet.getNom_objet());
			stmt.setString(3, objet.getType_objet().getNom());

			stmt.execute();
		} catch (SQLException e) {
			errorInsert = true;
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
		return errorInsert;
	}
	
	public int getNbObjetByIdAccountAndByIdObjet(int idAccount, int idObjet) {
		logger.debug("MiPa getObjetByIdAccount"+idAccount);
		Connection con = null;
		PreparedStatement stmt = null;

		int nombre = 0; 

		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_COUNT_BY_ID_ACCOUNT_AND_BY_ID_OBJET);
			stmt.setInt(1, idAccount);
			stmt.setInt(2, idObjet);
			logger.error("DEBUG REQ"+stmt.toString());

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				logger.debug("MiPa, une ligne trouvée");
				nombre = rset.getInt("nombre_objet");
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
		return nombre;
	}
	
	public Boolean deleteObjetByIdObjet (int id_objet) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();

			stmt = con.prepareStatement(QUERY_DELETE_BY_ID);
			stmt.setInt(1,id_objet);

			stmt.execute();
		} catch (SQLException e) {
			errorInsert = true;
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
		return errorInsert;
	}
}
