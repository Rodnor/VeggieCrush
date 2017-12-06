package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;
import com.bd.Connecteur;
import com.entitie.Account;
import com.utils.Utils;

/**
 * Classe permettant la gestion des ACCOUNTS
 */
public class AccountDao {
	
	private final static Logger logger = Logger.getLogger(AccountDao.class.getName());
	
	private final static String QUERY_FIND_ALL = "SELECT * FROM ACCOUNT";
	private final static String QUERY_FIND_IN_NOUVEAU_MDP = "SELECT FLAG FROM NOUVEAU_MDP INNER JOIN ACCOUNT ON ACCOUNT.id = NOUVEAU_MDP.id WHERE username = ?";
	private final static String QUERY_FIND_BY_MAIL = "SELECT * FROM ACCOUNT WHERE email = ?";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
	private final static String QUERY_INSERT = "INSERT INTO ACCOUNT (id_global, faction, username, password, email, created_at, updated_at, deleted_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_UPDATE_PASSWORD_BY_ID = "UPDATE ACCOUNT SET password = ?, updated_at = ?  WHERE id = ?";
	private final static String QUERY_UPDATE_MOT_DE_PASSE_BY_ID = "UPDATE NOUVEAU_MDP SET FLAG = ? WHERE ID = ?";
	private final static String QUERY_FIND_BY_USERNAME = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
	private final static String QUERY_FIND_BY_UID = "SELECT * FROM ACCOUNT WHERE id_global = ?";

	/**
	 * Retourne tout les comptes 
	 * @return ArrayList<Account>
	 */
	public ArrayList<Account> getAllAccounts() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Account account = new Account();
			while (rset.next()) {
				account = mappingAccount(rset);
				accounts.add(account);
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
		return accounts;
	}

	/**
	 * Recherche le compte en fonction de son id. Retourne <code>null</code> si pas de résultat  
	 * @param id
	 * @return account
	 */
	public Account getAccountById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
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
		
		if (account.getId() == 0){
			return null;
		}
		return account;
	}
	
	/**
	 * Recherche le compte en fonction de son mail. Retourne <code>null</code> si pas de résultat  
	 * @param mail
	 * @return account
	 */
	public Account getAccountByMail(String mail) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_MAIL);
			stmt.setString(1, mail);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
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
		
		if (account.getId() == 0){
			return null;
		}
		return account;
	}
	
	
	
	
	/**
	 * Permet de mettre à jour la table NOUVEAU_MDP (gestion de la génération des nouveaux mot de passe) 
	 * @param id
	 * @param flag
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas d'erreur
	 */
	public Boolean updateFlag(int id, String flag) {

		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_MOT_DE_PASSE_BY_ID);
		
			stmt.setString(1, flag);
			stmt.setInt(2, id);

			stmt.executeUpdate();
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
	 * Permet de créer une ligne dans la table NOUVEAU_MDP (gestion de la génération des nouveaux mot de passe) 
	 * @param id
	 * @param flag
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas d'erreur
	 */
	/*public Boolean createFlag(int id, String flag) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(INSERT_MOT_DE_PASSE_BY_ID);
			stmt.setInt(1, id);		
			stmt.setString(2, flag);

			stmt.executeUpdate();
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
*/
	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
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
		
		final Account account = new Account(id, id_global, username, email, password, faction, createdAT, updatedAT, deletedAT);
		return account;
	}
	
	/**
	 * Permet d'insérer une nouvelle ligne dans la table ACCOUNT
	 * 
	 * @param account
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public Boolean insertNewAccount(Account account) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			Date startDate = new java.sql.Date(calendar.getTime().getTime());

			// create the mysql insert preparedstatement
			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setString(1, account.getId_global());
			stmt.setString(2, account.getFaction());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword());
			stmt.setString(5, account.getEmail());
			stmt.setDate(6, startDate);
			stmt.setDate(7, null);
			stmt.setDate(8, null);

			stmt.execute();
			
			Account accountInsere = getAccountByMail(account.getEmail());
			//createFlag(accountInsere.getId(), "N");
			
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
	 * Recherche le compte en fonction de son uuid. Retourne <code>null</code> si pas de résultat  
	 * @param mail
	 * @return account
	 */
	public Account getAccounByUid(String uid) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_UID);
			stmt.setString(1, uid);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
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
		if (account.getId() == 0){
			return null;
		}
		return account;
	}
	
	/**
	 * Recherche le compte en fonction de son username. Retourne <code>null</code> si pas de résultat  
	 * @param username
	 * @return account
	 */
	public Account getAccountByUsername(String username) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_USERNAME);
			stmt.setString(1, username.toLowerCase());

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
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
		
		if (account.getId() == 0){
			return null;
		}
		return account;
	}

	/**
	 * Permet de savoir si l'utilisateur doit modifier son mot de passe lors de sa connexion
	 * @param username
	 * @return boolean <code>true</code> s'il faut changer le mot de passe <code>false</code> sinon
	 */
	public Boolean motDePasseAChanger(String username){
		Connection con = null;
		PreparedStatement stmt = null;
		
		Boolean flag = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_IN_NOUVEAU_MDP);
			stmt.setString(1, username);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				String flagRetourne = rset.getString("FLAG");
				
				flag = flagRetourne.equals("N") ? false : true;
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
		return flag;
	}
}
