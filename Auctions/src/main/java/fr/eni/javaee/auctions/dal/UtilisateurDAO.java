package fr.eni.javaee.auctions.dal;

import java.sql.SQLException;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.Utilisateur;

public interface UtilisateurDAO {
	
	void insert (Utilisateur utilisateur) throws BusinessException;	

	Utilisateur verifUtilisateur(String pseudo, String mdp) throws SQLException;
	
}
