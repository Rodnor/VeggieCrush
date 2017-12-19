package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.bd.Connecteur;
import com.entitie.Account;

/**
 * Classe permettant la gestion des ACCOUNTS
 */
public class AccountDao {

	private final static Logger logger = Logger.getLogger(AccountDao.class.getName());

	// requetes
	private final static String QUERY_FIND_ALL = "SELECT * FROM ACCOUNT";
	private final static String QUERY_FIND_IN_NOUVEAU_MDP = "SELECT FLAG FROM NOUVEAU_MDP INNER JOIN ACCOUNT ON ACCOUNT.id = NOUVEAU_MDP.id WHERE username = ?";
	private final static String QUERY_FIND_BY_MAIL = "SELECT * FROM ACCOUNT WHERE email = ?";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
	private final static String QUERY_UPDATE_MOT_DE_PASSE_BY_ID = "UPDATE NOUVEAU_MDP SET FLAG = ? WHERE ID = ?";
	private final static String QUERY_FIND_BY_USERNAME = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
	private final static String QUERY_FIND_BY_UID = "SELECT * FROM ACCOUNT WHERE id_global = ?";

	/**
	 * Retourne tous les comptes
	 * 
	 * @return ArrayList<Account>
	 */
	public ArrayList<Account> getAllAccounts() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			// connexion
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Account account = new Account();
			// resulats
			while (rset.next()) {
				account = mappingAccount(rset);
				accounts.add(account);
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
		return accounts;
	}

	/**
	 * Recherche le compte en fonction de son id. Retourne <code>null</code> si
	 * pas de résultat
	 * 
	 * @param id
	 * @return account
	 */
	public Account getAccountById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
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

		if (account.getId() == 0) {
			return null;
		}
		return account;
	}

	/**
	 * Recherche le compte en fonction de son mail. Retourne <code>null</code>
	 * si pas de résultat
	 * 
	 * @param mail
	 * @return account
	 */
	public Account getAccountByMail(String mail) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_MAIL);
			stmt.setString(1, mail);

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
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

		if (account.getId() == 0) {
			return null;
		}
		return account;
	}

	/**
	 * Permet de mettre à jour la table NOUVEAU_MDP (gestion de la génération
	 * des nouveaux mot de passe)
	 * 
	 * @param id
	 * @param flag
	 * @return <code>false</code> si tout est ok et <code>true</code> en cas
	 *         d'erreur
	 */
	public Boolean updateFlag(int id, String flag) {

		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_MOT_DE_PASSE_BY_ID);

			stmt.setString(1, flag);
			stmt.setInt(2, id);
			// execution
			stmt.executeUpdate();

			// erreurs
		} catch (SQLException e) {
			errorUpdate = true;
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

		return errorUpdate;
	}

	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * 
	 * @param rset
	 * @return account
	 * @throws SQLException
	 */
	private Account mappingAccount(final ResultSet rset) throws SQLException {
		final int id = rset.getInt("id");
		final String id_global = rset.getString("id_global");
		final String username = rset.getString("username");
		final String email = rset.getString("email");
		final String password = rset.getString("password");
		final String faction = rset.getString("faction");
		Timestamp createdAT = rset.getTimestamp("created_at");
		Timestamp updatedAT = rset.getTimestamp("updated_at");
		Timestamp deletedAT = rset.getTimestamp("deleted_at");

		final Account account = new Account(id, id_global, username, email, password, faction, createdAT, updatedAT,
				deletedAT);
		return account;
	}

	/**
	 * Recherche le compte en fonction de son uuid. Retourne <code>null</code>
	 * si pas de résultat
	 * 
	 * @param mail
	 * @return account
	 */
	public Account getAccounByUid(String uid) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_UID);
			stmt.setString(1, uid);

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
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
		if (account.getId() == 0) {
			return null;
		}
		return account;
	}

	/**
	 * Recherche le compte en fonction de son username. Retourne
	 * <code>null</code> si pas de résultat
	 * 
	 * @param username
	 * @return account
	 */
	public Account getAccountByUsername(String username) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_USERNAME);
			stmt.setString(1, username.toLowerCase());

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
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

		if (account.getId() == 0) {
			return null;
		}
		return account;
	}

	/**
	 * Permet de savoir si l'utilisateur doit modifier son mot de passe lors de
	 * sa connexion
	 * 
	 * @param username
	 * @return boolean <code>true</code> s'il faut changer le mot de passe
	 *         <code>false</code> sinon
	 */
	public Boolean motDePasseAChanger(String username) {
		Connection con = null;
		PreparedStatement stmt = null;

		Boolean flag = false;
		try {
			// connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_IN_NOUVEAU_MDP);
			stmt.setString(1, username);
			// resultats
			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				String flagRetourne = rset.getString("FLAG");
				flag = flagRetourne.equals("N") ? false : true;
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
		return flag;
	}
}
