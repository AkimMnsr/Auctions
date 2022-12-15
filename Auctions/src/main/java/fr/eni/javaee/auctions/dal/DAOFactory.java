package fr.eni.javaee.auctions.dal;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;

public class DAOFactory {


	
	
	
	public static UtilisateurDAO getUtilisateurDAO () {
		return new UtilisateurDAOJdbcImpl();
	}

	public static ArticleVenduDAO getArticleVenduDAO() {
		return new ArticleVenduDAOJdbcImpl();
	}
	
	public static CategorieDAO getCategorieDAO() {
        return new CategorieDAOJdbcImpl();
    }
	
	

}
