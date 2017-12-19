package com.bd;

/**
 * Classe permettant la connexion à la BD
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class Connecteur {
	// pour recuperer les données externalisées
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	final static Logger logger = Logger.getLogger(Connecteur.class.getName());

	public static Connection getConnexion() throws SQLException {
		try {
			// driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("pb connexion");
			e.printStackTrace();
		}

		//connexion
		final Connection con = DriverManager.getConnection(applicationProperties.getString("bd.connect.url"),
				applicationProperties.getString("bd.connect.user"), applicationProperties.getString("bd.connect.pass"));
		return con;
	}

}
