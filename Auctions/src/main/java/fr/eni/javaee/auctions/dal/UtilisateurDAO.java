package fr.eni.javaee.auctions.dal;

import java.sql.SQLException;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.Utilisateur;

public interface UtilisateurDAO {
	
	void insert (Utilisateur utilisateur) throws SQLException, BusinessException;	

	Utilisateur verifUtilisateur(String pseudo, String mdp) throws SQLException, BusinessException;

	void modifier(Utilisateur utilisateur, String mdpSession) throws BusinessException;
	
	public boolean uniciteIdentifiant (String pseudo, String email);

	void delete(Utilisateur utilisateur);

	Utilisateur profilUtilisateur(int idUser) throws BusinessException;
	
	public void modifierCredit (Utilisateur utilisateur);
}
