package fr.eni.javaee.auctions.dal;

public class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO () {
		return new UtilisateurDAOJdbcImpl();
	}
	
	

}
