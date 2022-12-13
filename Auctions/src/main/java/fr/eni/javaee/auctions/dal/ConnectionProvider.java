package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {

	public static DataSource dataSource;
	
	//bloc exécuté 1 seule fois lors du 1er appel à la classe (soit en passant par une instance (new), soit méthode static, soit attribut static)
	static {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
}



