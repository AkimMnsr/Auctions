package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.be.BusinessException;
/**
 * 
 * @author mberger2022
 *
 */
public interface ArticleVenduDAO {
	/**
	 * @author mberger2022
	 */
	List<ArticleVendu> selectArticlesAll(int idUser, String filtreArticle, int filtreCategorie);
	
	List<ArticleVendu> selectEncheresEnCours(int idUser, String filtreArticle, int filtreCategorie);
	
	List<ArticleVendu> selectEncheresGagnees(int idUser, String filtreArticle, int filtreCategorie);
	
	List<ArticleVendu> selectVentesParam(int idUser, String pseudoUser, String filtreArticle, int filtreCategorie, boolean achatsVentes, 
			               boolean mesVentesEnCours, boolean mesVentesNonDebutees, boolean mesVentesTerminees);
	
	ArticleVendu selectEnchereById(int idArticle);
	
	Utilisateur selectMeilleureEnchereById(int idArticle, int montantMax);
		
	/**
	 * @author qswiderski2022
	 * 
	 */
	void insert(ArticleVendu newVente) throws BusinessException;
	
	void updateVente(ArticleVendu a);



	

	
	

}
