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

/**
 * Classe permettant la gestion des OBJETS
 */
public class ObjetDao {
	private final static String QUERY_FIND_ALL = "SELECT * FROM OBJET";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM OBJET WHERE id_objet = ?";
	private final static String QUERY_FIND_BY_ID_ACCOUNT = "SELECT * FROM ACCOUNT INNER JOIN INVENTAIRE ON ACCOUNT.id = INVENTAIRE.id_global INNER JOIN OBJET ON INVENTAIRE.id_objet = OBJET.id_objet "
			+ "WHERE INVENTAIRE.id_global = ?";
	private final static String QUERY_COUNT_BY_ID_ACCOUNT_AND_BY_ID_OBJET = "SELECT qte FROM INVENTAIRE WHERE id_global = ? AND id_objet = ?";
	private final static String QUERY_PRESENT_BY_ID_ACCOUNT_AND_BY_ID_OBJET = "SELECT count(*) as nb FROM INVENTAIRE WHERE id_global = ? AND id_objet = ?";
	private final static String QUERY_INSERT = "INSERT INTO OBJET (id_objet, name_objet, type_objet, puissance_objet) values (?, ?, ?, ?)";
	private final static String QUERY_DELETE_BY_ID = "DELETE FROM OBJET WHERE id_objet = ?";

	final static Logger logger = Logger.getLogger(ObjetDao.class.getName());

	/**
	 * Permet de récupérer tous les objets en base
	 * 
	 * @return ArrayList<Objet>
	 */
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

	/**
	 * Permet de recuperer un objet en fonction de son id
	 * 
	 * @param id
	 * @return objet
	 */
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

	/**
	 * Permet de récupérer tous les objets d'un compte en fonction de l'uuid
	 * 
	 * @param uuid
	 * @return ArrayList<Objet>
	 */
	public ArrayList<Objet> getObjetByUuid(String uuid) {
		Connection con = null;
		PreparedStatement stmt = null;

		ArrayList<Objet> objets = new ArrayList<Objet>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID_ACCOUNT);
			stmt.setString(1, uuid);

			Objet objet = new Objet();

			final ResultSet rset = stmt.executeQuery();
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

	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * 
	 * @param rset
	 * @return objet
	 * @throws SQLException
	 */
	private Objet mappingObjet(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final String nom_objet = rset.getString("name_objet");
		final String nom_type_objet = rset.getString("type_objet");
		final TypeObjet type_objet = TypeObjet.valueOf(nom_type_objet);// TypeObjet.POTION;
		final int puissance_objet = rset.getInt("puissance_objet");
		final Objet objet = new Objet(id_objet, nom_objet, type_objet, puissance_objet);
		return objet;
	}

	/**
	 * Permet d'insérer une nouvelle ligne dans la table OBJET
	 * 
	 * @param objet
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
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
			stmt.setInt(3, objet.getPuissance_objet());

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

	/**
	 * Permet de récupérer le nombre d'objets par uuid et pas objet
	 * 
	 * @param uuid
	 * @param idObjet
	 * @return nombre
	 */
	public int getNbObjetByUuidAndByIdObjet(String uuid, int idObjet) {
		Connection con = null;
		PreparedStatement stmt = null;

		int nombre = 0;

		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_COUNT_BY_ID_ACCOUNT_AND_BY_ID_OBJET);
			stmt.setString(1, uuid);
			stmt.setInt(2, idObjet);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				nombre = rset.getInt("qte");
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

	/**
	 * Permet de compter le nombre d'objet présent en base pour un uuid
	 * 
	 * @param idAccount
	 * @param idObjet
	 * @return nombre
	 */
	public int countObjectInInventaireForUuid(String idAccount, int idObjet) {
		Connection con = null;
		PreparedStatement stmt = null;

		int nombre = 0;

		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_PRESENT_BY_ID_ACCOUNT_AND_BY_ID_OBJET);
			stmt.setString(1, idAccount);
			stmt.setInt(2, idObjet);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				nombre = rset.getInt("nb");
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

	/**
	 * Permet de supprimer un objet en base selon son identifiant
	 * 
	 * @param id_objet
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public Boolean deleteObjetByIdObjet(int id_objet) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();

			stmt = con.prepareStatement(QUERY_DELETE_BY_ID);
			stmt.setInt(1, id_objet);

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
