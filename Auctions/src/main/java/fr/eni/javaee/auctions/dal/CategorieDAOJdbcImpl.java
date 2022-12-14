package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.dal.ConnectionProvider;

/**
 * 
 * @author qswiderski2022
 *
 */

public class CategorieDAOJdbcImpl implements CategorieDAO {

	
	private static final String SELECT_ALL = "SELECT libelle FROM CATEGORIES;";
	
	@Override
	public List<Categorie> selectAll() {
		List<Categorie> categories = new ArrayList<>();
		
		
		try(Connection cnx = ConnectionProvider.getConnection()) {

		Statement stmt = cnx.createStatement();			
	
		ResultSet rs = stmt.executeQuery(SELECT_ALL);
		
		while(rs.next()) {
			int no_categorie = rs.getInt("no_categorie");
			String libelle = rs.getString("libelle");
			
		Categorie c = new Categorie (no_categorie, libelle);
			categories.add(c);
		}
		
		}catch (SQLException e) {
			e.printStackTrace();
		
		
		}return categories;
	}

}
