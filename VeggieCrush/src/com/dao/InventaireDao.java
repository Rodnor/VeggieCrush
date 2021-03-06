package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.bd.Connecteur;
import com.entitie.Inventaire;

/**
 * Classe permettant la gestion des INVENTAIRES
 */
public class InventaireDao {

	// requetes
	private final static String QUERY_FIND_ALL = "SELECT * FROM INVENTAIRE";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM INVENTAIRE WHERE id_global = ?";
	private final static String QUERY_FIND_BY_ID_ACCOUNT_AND_BY_ID_OBJET = "SELECT * FROM INVENTAIRE WHERE id_global = ? AND id_objet = ?";
	private final static String QUERY_INSERT = "INSERT INTO INVENTAIRE (id_objet, id_global, qte) values (?, ?, ?)";
	private final static String QUERY_UPDATE = "UPDATE INVENTAIRE SET qte = ? WHERE id_global = ? AND id_objet = ?";

	private final static Logger logger = Logger.getLogger(InventaireDao.class.getName());

	/**
	 * Permet de récupérer tous les inventaires en base
	 * 
	 * @return ArrayList<Inventaire>
	 */
	public ArrayList<Inventaire> getAllInventaires() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			// connexion
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Inventaire inventaire = new Inventaire();
			// resultats
			while (rset.next()) {
				inventaire = mappingInventaire(rset);
				inventaires.add(inventaire);
			}
			// erreurs
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

	/**
	 * Recherche tous les inventaires en fonction de l'uid de la personne
	 * 
	 * @param id
	 * @return ArrayList<Inventaire>
	 */
	public ArrayList<Inventaire> getInventaireByUuid(String id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			// traitement
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setString(1, id);

			final ResultSet rset = stmt.executeQuery();
			Inventaire inventaire = new Inventaire();
			// résultats
			while (rset.next()) {
				inventaire = mappingInventaire(rset);
				
				if (inventaire.getQte() <= 0){
					if (inventaire.getId_objet() < 4) {
						inventaires.add(inventaire);
					}
				} else {
					inventaires.add(inventaire);
				}
			}
			// erreurs
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

	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * 
	 * @param rset
	 * @return inventaire
	 * @throws SQLException
	 */
	private Inventaire mappingInventaire(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final String id_user = rset.getString("id_global");
		final int qte = rset.getInt("qte");
		final Inventaire inventaire = new Inventaire(id_user, id_objet, qte);
		return inventaire;
	}

	/**
	 * Permet de créer un nouvel inventaire
	 * 
	 * @param inventaire
	 * @return <code>false</code> si tout est ok et <code>true</code> en cas
	 *         d'erreur
	 */
	public Boolean insertNewInventaire(Inventaire inventaire) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		try {
			// connexion
			con = Connecteur.getConnexion();

			ObjetDao objetDao = new ObjetDao();

			int dejaUneLigne = objetDao.countObjectInInventaireForUuid(inventaire.getId_user(),
					inventaire.getId_objet());

			// si déjà présent en base, on modifie la quantité
			if (dejaUneLigne == 1) {
				stmt = con.prepareStatement(QUERY_UPDATE);
				int nbObjetDejaPresent = objetDao.getNbObjetByUuidAndByIdObjet(inventaire.getId_user(),
						inventaire.getId_objet());

				stmt.setInt(1, nbObjetDejaPresent + inventaire.getQte());
				stmt.setString(2, inventaire.getId_user());
				stmt.setInt(3, inventaire.getId_objet());
			} else {
				// sinon, création d'une nouvelle ligne
				stmt = con.prepareStatement(QUERY_INSERT);
				stmt.setInt(1, inventaire.getId_objet());
				stmt.setString(2, inventaire.getId_user());
				stmt.setInt(3, inventaire.getQte());
			}
			stmt.execute();
			// erreurs
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

	/**
	 * Permet de diminuer la quantité d'un objet dans la table inventaire
	 * 
	 * @param uuid
	 * @param iditem
	 * @param qteareduire
	 * @return <code>false</code> si tout est ok et <code>true</code> en cas
	 *         d'erreur
	 */
	private Boolean reductionPlante(String uuid, int iditem, int qteareduire) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		try {
			// connexion
			con = Connecteur.getConnexion();

			ObjetDao objetDao = new ObjetDao();

			stmt = con.prepareStatement(QUERY_UPDATE);
			int nbObjetDejaPresent = objetDao.getNbObjetByUuidAndByIdObjet(uuid, iditem);
			// preparation
			stmt.setInt(1, nbObjetDejaPresent - qteareduire);
			stmt.setString(2, uuid);
			stmt.setInt(3, iditem);
			stmt.execute();

			// erreurs
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

	/**
	 * Permet de modifier les lignes de l'inventaire pour les plantes utilisés
	 * et la potion créée
	 * 
	 * @param uuid
	 * @param idObjetCrafte
	 * @param qte1
	 * @param qte2
	 * @param qte3
	 * @param qte4
	 * @return <code>false</code> si tout est ok et <code>true</code> en cas
	 *         d'erreur
	 */
	public Boolean craftNewItem(String uuid, int idObjetCrafte, int qte1, int qte2, int qte3, int qte4) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		try {
			con = Connecteur.getConnexion();

			ObjetDao objetDao = new ObjetDao();

			int dejaUneLigne = objetDao.countObjectInInventaireForUuid(uuid, idObjetCrafte);

			// si déjà présent en base, on modifie la quantité
			if (dejaUneLigne == 1) {
				stmt = con.prepareStatement(QUERY_UPDATE);
				int nbObjetDejaPresent = objetDao.getNbObjetByUuidAndByIdObjet(uuid, idObjetCrafte);
				stmt.setInt(1, nbObjetDejaPresent + 1);
				stmt.setString(2, uuid);
				stmt.setInt(3, idObjetCrafte);
				// sinon, on crée une ligne
			} else {
				stmt = con.prepareStatement(QUERY_INSERT);
				stmt.setInt(1, idObjetCrafte);
				stmt.setString(2, uuid);
				stmt.setInt(3, 1);
			}
			stmt.execute();
			// on réduit la qté des plantes qui ont été utilisées
			reductionPlante(uuid, 1, qte1);
			reductionPlante(uuid, 2, qte2);
			reductionPlante(uuid, 3, qte3);
			reductionPlante(uuid, 4, qte4);

			// erreurs
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

	/**
	 * Recherche tous les inventaires en fonction de l'uid de la personne et de
	 * l'id de l'objet
	 * 
	 * @param idGlobal
	 * @param idObjet
	 * @return ArrayList<Inventaire>
	 */
	public ArrayList<Inventaire> getInventaireByUuidAndByIdObjet(String idGlobal, int idObjet) {
		Connection con = null;
		PreparedStatement stmt = null;

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID_ACCOUNT_AND_BY_ID_OBJET);
			stmt.setString(1, idGlobal);
			stmt.setInt(2, idObjet);

			Inventaire inventaire = new Inventaire();

			// resultats
			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				inventaire = mappingInventaire(rset);
				inventaires.add(inventaire);
			}
			// erreurs
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
}
