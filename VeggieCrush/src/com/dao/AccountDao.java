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

public class AccountDao {

	final static Logger logger = Logger.getLogger(AccountDao.class.getName()); //UNIXTIME(colonne_timestamp) as valeur_datetime

	private final static String QUERY_FIND_ALL = "SELECT * FROM ACCOUNT";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
	private final static String QUERY_INSERT = "INSERT INTO ACCOUNT (id_global, id_faction, username, password, email, created_at, updated_at, deleted_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
	

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
		return account;
	}

	private Account mappingAccount(final ResultSet rset) throws SQLException {
		final int id = rset.getInt("id");
		final String id_global = rset.getString("id_global");
		final String username = rset.getString("username");
		final String email = rset.getString("email");
		final String password = rset.getString("password");
		final int id_faction = rset.getInt("id_faction");
		
		Timestamp createdAT = rset.getTimestamp("created_at");
		Timestamp updatedAT = rset.getTimestamp("updated_at");
		Timestamp deletedAT = rset.getTimestamp("deleted_at");

		/**if (Utils.testDateNulleForTimstamp(createdAT)) {
			createdAT = null;
		}
		
		if (Utils.testDateNulleForTimstamp(updatedAT)) {
			updatedAT = null;
		}
		
		if (Utils.testDateNulleForTimstamp(deletedAT)) {
			deletedAT = null;
		} **/
		
		final Account account = new Account(id, id_global, username, email, password, id_faction, createdAT, updatedAT, deletedAT);
		return account;
	}
	
	public Boolean insertNewAccount(Account account) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		

		try {
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			//VALUES ('0', '85', '1', 'pouloulou', 'michard', 'ouh@lol.fr', '20130405', '2013-10-20 20:18:01', '');

			// create the mysql insert preparedstatement
			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setString(1, account.getGlobalID());
			stmt.setInt(2, account.getId_faction());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword()); // TODO
			stmt.setString(5, account.getEmail());
			stmt.setDate(6, startDate);
			stmt.setDate(7, null);//df.format(account.getCreatedAT()));
			stmt.setDate(8, null);//null);

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
