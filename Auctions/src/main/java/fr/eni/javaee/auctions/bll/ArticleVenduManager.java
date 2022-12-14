package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactory;

/**
 * 
 * @author qswiderski2022
 *
 */


public class ArticleVenduManager {

	private static ArticleVendu instance;
	
	public static ArticleVendu getInstance() {
		if(instance == null) {
			instance = new ArticleVendu();
		}
		return instance;
	}
	
	private ArticleVenduManager() {}
	

	//Créer List ArticleVendu
	public List<ArticleVendu> selectVentes(Utilisateur u) {
		return DAOFactory.getArticleVenduDAO().selectVentes(u.getNoUtilisateur());
	}
	

	public void insertVente(ArticleVendu a) {	
		DAOFactory.getArticleVenduDAO().insertVente(a);
	}
	
	public void updateVente(ArticleVendu a) {	
		DAOFactory.getArticleVenduDAO().updateVente(a);
	}
	
	
	
	
}
