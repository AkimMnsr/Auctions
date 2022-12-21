package fr.eni.javaee.auctions.bll;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactory;
/**
 * 
 * @author mberger2022
 *
 */
public class EnchereManager {
	
	//ATTRIBUT DE CLASSE DU SINGLETON
	private static EnchereManager instance;
	
	//GETTER
	public static EnchereManager getInstance() {
		if (instance == null) {
			instance = new EnchereManager();
		}
		return instance;
	}
	
	//CONSTRUCTEUR PRIVE POUR NE PAS INSTANCIER LE SINGLETON
	private EnchereManager() {}
	

	//AUTRES METHODES D'INSTANCE
	/**
	 * Retourne la plus haute enchère faite 
	 * pour l'article dont le numéro est passé en paramètre
	 * @author mberger2022
	 * @param idArticle : numéro de l'article dont on veut connaitre l'utilisateur ayant fait la meilleure enchère
	 * @return
	 * @throws BusinessException 
	 */
	public Enchere selectMeilleureEnchere(int idArticle, int montantMax) throws BusinessException {
		Enchere enchere = null;
		BusinessException be = new BusinessException();
		if (idArticle == 0) {
			be.ajouterErreur(CodeErreurBLLEnchere.NUM_ARTICLE_OBLIGATOIRE);
		} else {	
			enchere = DAOFactory.getEnchereDAO().selectMeilleureEnchere(idArticle, montantMax);
		}
		
		if (be.hasErreurs()) {
			throw be;
		}
		return enchere;
	}
	
	/**
	 * Crée une nouvelle enchère du montant passé en paramètre
	 * pour l'utilisateur dont le numéro est passé en paramètre 
	 * et l'article dont le numéro est passé en paramètre	
	 * @param noUtilisateur
	 * @param idArticle
	 * @param proposition
	 * @throws BusinessException 
	 */
	public void insertNouvelleEnchere(int idUser, int idArticle, int montant, int idAcheteurPrec) throws BusinessException  {
		BusinessException be = new BusinessException();
		
		ArticleVendu article = null;
		Utilisateur acheteur = null;
		
		if (idArticle != 0) {
			article = ArticleVenduManager.getInstance().selectById(idArticle);
		} else {
			be.ajouterErreur(CodeErreurBLLEnchere.NUM_ARTICLE_OBLIGATOIRE);
		}
		
		if (idUser != 0) {
			acheteur = UtilisateurManager.getInstance().profilUtilisateur(idUser);	
		} else {
			be.ajouterErreur(CodeErreurBLLEnchere.NUM_UTILISATEUR_OBLIGATOIRE);
		}
		
		if (montant <= article.getPrixVente()) {
			be.ajouterErreur(CodeErreurBLLEnchere.MONTANT_INSUFFISANT);			
		}
		if (montant > acheteur.getCredit()) {
			be.ajouterErreur(CodeErreurBLLEnchere.CREDIT_INSUFFISANT);	
		}
		
		if (be.hasErreurs()) {
			throw be;
		}	
		// SI PAS D'ERREUR
		Enchere nouvelleEnchere = new Enchere(montant, acheteur, article);
		
		DAOFactory.getEnchereDAO().insertNouvelleEnchere(nouvelleEnchere, idAcheteurPrec);
		
	}	
}
