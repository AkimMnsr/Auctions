package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
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
	
	public ArticleVendu insertVente(Utilisateur user, String nomArticle, String description, int categorie,
			Integer miseAPrix, LocalDate dateDebEncheres, LocalDate dateFinEncheres, String rue, String codePostal,
			String ville) throws BusinessException {

		// verification des données

		BusinessException be = new BusinessException();
		validerArticle(nomArticle, be);
		validerDescription(description, be);
		validerMiseAPrix(miseAPrix, be);
		validerDateDebEncheres(dateDebEncheres, be);
		validerDateFinEncheres(dateFinEncheres, dateDebEncheres, be);
		validerRue(rue, be);
		validerCodePostal(codePostal, be);
		validerVille(ville, be);
		if (be.hasErreurs()) {
			throw be;
		}

		Retrait lieuRetrait = new Retrait(rue, codePostal, ville);
		Categorie categ = new Categorie(categorie);

		ArticleVendu newVente = new ArticleVendu(nomArticle, description, dateDebEncheres, dateFinEncheres, miseAPrix,
				categ, user, lieuRetrait);

		DAOFactory.getArticleVenduDAO().insert(newVente);
		return newVente;
	}

	private void validerArticle(String nomArticle, BusinessException be) {
		if (nomArticle == null || nomArticle.isBlank() || nomArticle.length() > (30)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_NOMARTICLE_ERREUR);
		}
	}

	private void validerDescription(String description, BusinessException be) {
		if (description == null || description.isBlank() || description.length() > (300)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_DESCRIPTION_ERREUR);
		}
	}

	private void validerMiseAPrix(Integer miseAPrix, BusinessException be) {
		if (miseAPrix == null || miseAPrix <= 0) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_DESCRIPTION_ERREUR);
		}
	}

	private void validerDateDebEncheres(LocalDate dateDebEncheres, BusinessException be) {
		LocalDate dateDuJour = LocalDate.now();
		if (dateDebEncheres == null || dateDebEncheres.isBefore(dateDuJour)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_DATEDEBUTENCHERE_ERREUR);
		}
	}

	private void validerDateFinEncheres(LocalDate dateFinEncheres, LocalDate dateDebEncheres, BusinessException be) {
		if (dateFinEncheres == null || dateFinEncheres.isBefore(dateDebEncheres)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_DATEFINENCHERE_ERREUR);
		}
	}

	private void validerRue(String rue, BusinessException be) {
		if (rue == null || rue.isBlank() || rue.length() > (30)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_RUE_ERREUR);
		}
	}

	private void validerCodePostal(String codePostal, BusinessException be) {
		if (codePostal == null || codePostal.isBlank() || codePostal.length() > (15)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_CODEPOSTAL_ERREUR);
		}
	}

	private void validerVille(String ville, BusinessException be) {
		if (ville == null || ville.isBlank() || ville.length() > (30)) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLES_ARTICLE_VILLE_ERREUR);
		}
	}
	
	private void validerIdArticle(Integer idArticle, BusinessException be) {
		if (idArticle == null || idArticle <= 0) {
			be.ajouterErreur(CodeErreurBLLArticle.REGLE_ARTICLE_ID_ERREUR);
		}
	}

	public void updateVente(Utilisateur user, String nomArticle, String description, int categorie,
			Integer miseAPrix, LocalDate dateDebEncheres, LocalDate dateFinEncheres, String rue, String codePostal,
			String ville, int idArticle) throws BusinessException {
		
		
		BusinessException be = new BusinessException();
		
		validerArticle(nomArticle, be);
		validerDescription(description, be);
		validerMiseAPrix(miseAPrix, be);
		validerDateDebEncheres(dateDebEncheres, be);
		validerDateFinEncheres(dateFinEncheres, dateDebEncheres, be);
		validerRue(rue, be);
		validerCodePostal(codePostal, be);
		validerVille(ville, be);
		validerIdArticle (idArticle, be);
		
		if (be.hasErreurs()) {
			throw be;
		}

		Retrait lieuRetrait = new Retrait(rue, codePostal, ville);
		Categorie categ = new Categorie(categorie);

		ArticleVendu VenteModif = new ArticleVendu(nomArticle, description, dateDebEncheres, dateFinEncheres, miseAPrix,
				categ, user, lieuRetrait);

		DAOFactory.getArticleVenduDAO().updateVente(VenteModif);
		
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
