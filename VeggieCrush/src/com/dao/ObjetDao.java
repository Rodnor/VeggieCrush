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
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM OBJET WHERE ID = ?";
	private final static String QUERY_FIND_BY_ID_ACCOUNT = 	"SELECT * FROM ACCOUNT " + 
															"INNER JOIN INVENTAIRE ON ACCOUNT.id = INVENTAIRE.id_user " + 
															"INNER JOIN OBJET ON INVENTAIRE.id_objet = OBJET.id_objet " + 
															"WHERE INVENTAIRE.id_user = ?";

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
	
	public ArrayList<Objet> getObjetByIdAccount(int id) {
		logger.debug("MiPa getObjetByIdAccount"+id);
		Connection con = null;
		PreparedStatement stmt = null;

		ArrayList<Objet> objets = new ArrayList<Objet>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID_ACCOUNT);
			stmt.setInt(1, id);
			logger.error("DEBUG REQ"+stmt.toString());

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
	
	/**public ArrayList<Inventaire> getInventaireByIdAccount(int id) {
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
	 */
	
	  private Objet mappingObjet (final ResultSet rset) throws SQLException {
	        final int id_objet = rset.getInt("id_objet");
	        final String nom_objet = rset.getString("name_objet");
	        final String nom_type_objet = rset.getString("type_objet");
	        final TypeObjet type_objet =  TypeObjet.valueOf(nom_type_objet);// TypeObjet.POTION;
	        final Objet objet = new Objet(id_objet, nom_objet, type_objet);     
	        
	        logger.debug("construction de l'objet"+objet.toString());
	        
	        return objet;
	    }
	  
		public Boolean insertNewObjet(Objet objet) {
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
