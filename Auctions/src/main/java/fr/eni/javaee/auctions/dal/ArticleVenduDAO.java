package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.be.BusinessException;

public interface ArticleVenduDAO {
	
	List<ArticleVendu> selectArticlesAll(String filtreArticle, int filtreCategorie);
	
	List<ArticleVendu> selectEncheresEnCours(int idUser, String filtreArticle, int filtreCategorie);
	
	List<ArticleVendu> selectEncheresGagnees(int idUser, String filtreArticle, int filtreCategorie);
	

	
	//List<ArticleVendu> selectEncheresAll(int idUser, boolean encheresOuvertes);

	
	/**
	 * 
	 * @author qswiderski2022
	 * 
	 */
	
	void insert(ArticleVendu newVente) throws BusinessException;
	
	List<ArticleVendu> selectVentes(int idUser);

	void updateVente(ArticleVendu a) throws BusinessException;

	void delete(ArticleVendu a) throws BusinessException; 
	

}
