package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.bd.Connecteur;
import com.entitie.Account;
import com.entitie.Objet;
import com.entitie.Recette;
import com.entitie.TypeObjet;

public class RecetteDao {
	
	private final static String QUERY_INSERT = "INSERT INTO RECETTE (id_recette, id_objet, id_faction, compo1, compo2, compo3, compo4, qte1, qte2, qte3, qte4) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_DELETE_BY_ID = "DELETE FROM RECETTE WHERE id_recette = ?";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM RECETTE WHERE id_recette = ?";

	final static Logger logger = Logger.getLogger(ObjetDao.class.getName());
	
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

	
	private Recette mappingRecette(final ResultSet rset) throws SQLException {

		final int id_recette = rset.getInt("id_recette");
		final int id_objet = rset.getInt("id_objet");
		final int id_faction = rset.getInt("id_faction");
		ObjetDao objetDao = new ObjetDao();
		
		Objet compo1 = new Objet();
		if (rset.getInt("compo1") != 0) {
			compo1 = objetDao.getObjetById(rset.getInt("compo1"));
		} else {
			compo1 = null;
		}

		Objet compo2 = new Objet();
		if (rset.getInt("compo2") != 0) {
			compo2 = objetDao.getObjetById(rset.getInt("compo2"));
		} else {
			compo2 = null;
		}
		
		Objet compo3 = new Objet();
		if (rset.getInt("compo3") != 0) {
			compo3 = objetDao.getObjetById(rset.getInt("compo3"));
		} else {
			compo3 = null;
		}
		
		Objet compo4 = new Objet();
		if (rset.getInt("compo4") != 0) {
			compo4= objetDao.getObjetById(rset.getInt("compo4"));
		} else {
			compo4 = null;
		}
		
		final int qte1 = rset.getInt("qte1");
		final int qte2 = rset.getInt("qte2");
		final int qte3 = rset.getInt("qte3");
		final int qte4 = rset.getInt("qte4");

		Recette recette = new Recette(id_recette,
				id_objet,
				id_faction,
				compo1,
				compo2,
				compo3,
				compo4,
				qte1,
				qte2,
				qte3,
				qte4);

		return recette;
	}
	
	public Boolean insertNewrecette(Recette recette) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();

			//(id_recette, id_objet, id_faction, compo1, compo2, compo3, compo4, qte1, qte2, qte3, qte4) 
			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setInt(1,recette.getIdRecette());
			stmt.setInt(2,recette.getIdObjet());
			stmt.setInt(3,recette.getIdFaction());
			
			if (recette.getCompo1() != null) {
				stmt.setInt(4,recette.getCompo1().getId_objet());
			} else {
				stmt.setInt(4,0);
			}
			
			if (recette.getCompo2() != null) {
				stmt.setInt(5,recette.getCompo2().getId_objet());
			} else {
				stmt.setInt(5,0);
			}
			
			if (recette.getCompo3() != null) {
				stmt.setInt(6,recette.getCompo3().getId_objet());
			} else {
				stmt.setInt(6,0);
			}
			
			if (recette.getCompo4() != null) {
				stmt.setInt(7,recette.getCompo4().getId_objet());
			} else {
				stmt.setInt(7,0);
			}
			
			stmt.setInt(8,recette.getQte1());
			stmt.setInt(9,recette.getQte2());
			stmt.setInt(10,recette.getQte3());
			stmt.setInt(10,recette.getQte4());

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
