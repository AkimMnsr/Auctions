package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactory;

/**
 * @author qswiderski2022
 */
public class ArticleVenduManager {

	//ATTRIBUT DE CLASSE DU SINGLETON
	private static ArticleVenduManager instance;
	
	//GETTER
	public static ArticleVenduManager getInstance() {
		if (instance == null) {
			instance = new ArticleVenduManager();
		}
		return instance;
	}
	
	//CONSTRUCTEUR PRIVE POUR NE PAS INSTANCIER LE SINGLETON
	private ArticleVenduManager() {}
	

	//AUTRES METHODES D'INSTANCE
	
	
	public List<ArticleVendu> selectVentes(int idUser) {
		return DAOFactory.getArticleVenduDAO().selectVentes(idUser);
	}
	
	public void insertVente(ArticleVendu a) {	
		DAOFactory.getArticleVenduDAO().insertVente(a);
	}
	
	public void updateVente(ArticleVendu a) {	
		DAOFactory.getArticleVenduDAO().updateVente(a);
	}
	
	/**
	 * Liste de toutes les articles en cours d'enchère 
	 * @author mberger2022
	 * @param filtreArticle : contenu de la zone "l'article contient..."
	 * @param filtreCategorie : numéro de la catégorie sélectionnée (0 si toutes)
	 * @return
	 */
	public List<ArticleVendu> selectArticlesAll(String filtreArticle, int filtreCategorie) {
						
		return DAOFactory.getArticleVenduDAO().selectArticlesAll(filtreArticle, filtreCategorie);
	}
	
	/**
	 * Liste de toutes les enchères en cours de l'utilisateur connecté
	 * dont le numéro est passé en paramètre
	 * @author mberger2022
	 * @param idUser : numéro de l'utilisateur connecté
	 * @param filtreArticle : contenu de la zone "l'article contient..."
	 * @param filtreCategorie : numéro de la catégorie sélectionnée (0 si toutes)
	 * @return 
	 */
	public List<ArticleVendu> selectEncheresEnCours(int idUser, String filtreArticle, int filtreCategorie) {
		
		return DAOFactory.getArticleVenduDAO().selectEncheresEnCours(idUser, filtreArticle, filtreCategorie);
	}

	/**
	 * Liste de toutes les enchères terminées et gagnées par l'utilisateur connecté
	 * dont le numéro est passé en paramètre
	 * @author mberger2022
	 * @param idUser : numéro de l'utilisateur connecté
	 * @param filtreArticle : contenu de la zone "l'article contient..."
	 * @param filtreCategorie : numéro de la catégorie sélectionnée (0 si toutes)
	 * @return 
	 */
	public List<ArticleVendu> selectEncheresGagnees(int idUser, String filtreArticle, int filtreCategorie) {
		
		return DAOFactory.getArticleVenduDAO().selectEncheresGagnees(idUser, filtreArticle, filtreCategorie);
	}
	
	
}
