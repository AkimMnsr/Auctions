package fr.eni.javaee.auctions.dal;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * 
 * @author mberger2022
 *
 */
public interface EnchereDAO {

	Enchere selectMeilleureEnchere(int idArticle, int montantMax);
	
	public void insertNouvelleEnchere(Enchere enchere, int idAcheteurPrec) throws BusinessException;
}
