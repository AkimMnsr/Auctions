package fr.eni.javaee.auctions.bll;

import java.sql.SQLException;
import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactory;
import fr.eni.javaee.auctions.dal.UtilisateurDAO;

public class UtilisateurManager {

	Utilisateur utilisateur;
	private static UtilisateurManager instance;

	public static UtilisateurManager getInstance() {
		if (instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}

	private UtilisateurManager() {
	}

	public Utilisateur insert(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, int credit, boolean administrateur,
			String validationMDP) throws SQLException, BusinessException {
		// 1 Verification des données

		BusinessException be = new BusinessException();
		validerPseudo(pseudo, be);
		validerNom(nom, be);
		validerPrenom(prenom, be);
		validerEmail(email, be);
		validerTelephone(telephone, be);
		validerRue(rue, be);
		validerCodePostal(codePostal, be);
		validerVille(ville, be);
		validerMotDePasse(motDePasse, be);
		validerMDPCorrect(motDePasse, validationMDP, be);
		if (be.hasErreurs()) {
			throw be;
		}

		// 1 Intégrité des données valides donc poursuite de la méthode
		Utilisateur newUser = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse,
				credit, administrateur);
		DAOFactory.getUtilisateurDAO().insert(newUser);

		return newUser;
	}
	
	public boolean uniciteIdentifiant (String pseudo, String email) {
			
		
		return DAOFactory.getUtilisateurDAO().uniciteIdentifiant(pseudo, email);
		
	}
	
	
	public Utilisateur verifUtilisateur(String pseudo, String mdp) throws SQLException, BusinessException {

		BusinessException be = new BusinessException();
		validerPseudoCNX(pseudo, be);
		validerMotDePasseCNX(mdp, be);
		if (be.hasErreurs()) {
			throw be;
		}
		return DAOFactory.getUtilisateurDAO().verifUtilisateur(pseudo, mdp);
	}
	
	public void modifier (Utilisateur utilisateur, String mdpSession, String ancienMdp, String nouveauMDP, String confirmationMdp) throws BusinessException {
		
		BusinessException be = new BusinessException();
		validerPseudo(utilisateur.getPseudo(), be);
		validerNom(utilisateur.getNom(), be);
		validerPrenom(utilisateur.getPrenom(), be);
		validerEmail(utilisateur.getEmail(), be);
		validerTelephone(utilisateur.getTelephone(), be);
		validerRue(utilisateur.getRue(), be);
		validerCodePostal(utilisateur.getCodePostal(), be);
		validerVille(utilisateur.getVille(), be);		
		try {			
		if (nouveauMDP != null && confirmationMdp != null) {
		validerMDPCorrect(nouveauMDP, confirmationMdp, be);
			if (!mdpSession.equals(ancienMdp)) {
				be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEURS_MOT_DE_PASSE_INCORRECT);
			}
		}
		
			if (be.hasErreurs()) {
				throw be;
			}
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
		
		DAOFactory.getUtilisateurDAO().modifier(utilisateur, mdpSession);
		
	}

	private void validerPseudoCNX(String pseudo, BusinessException be) {
		if (pseudo == null || pseudo.isBlank() || pseudo.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_CNX_PSEUDO);
		}
	}

	private void validerMotDePasseCNX(String motDePasse, BusinessException be) {
		if (motDePasse == null || motDePasse.isBlank() || motDePasse.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_CNX_MDP);
		}
	}

	private void validerPseudo(String pseudo, BusinessException be) {
		if (pseudo == null || pseudo.isBlank() || pseudo.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_PSEUDO_ERREUR);
		}
	}

	private void validerNom(String nom, BusinessException be) {
		if (nom == null || nom.isBlank() || nom.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_NOM_ERREUR);
		}
	}

	private void validerPrenom(String prenom, BusinessException be) {
		if (prenom == null || prenom.isBlank() || prenom.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_PRENOM_ERREUR);
		}
	}

	private void validerEmail(String email, BusinessException be) {
		if (email == null || email.isBlank() || email.length() > 20) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_EMAIL_ERREUR);
		}
	}

	private void validerTelephone(String telephone, BusinessException be) {
		if (telephone.length() > 15) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_TELEPHONE_ERREUR);
		}
	}

	private void validerRue(String rue, BusinessException be) {
		if (rue == null || rue.isBlank() || rue.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_RUE_ERREUR);
		}
	}

	private void validerCodePostal(String codePostal, BusinessException be) {
		if (codePostal == null || codePostal.isBlank() || codePostal.length() > 10) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_CODE_POSTAL_ERREUR);
		}
	}

	private void validerVille(String ville, BusinessException be) {
		if (ville == null || ville.isBlank() || ville.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_VILLE_ERREUR);
		}
	}

	private void validerMotDePasse(String motDePasse, BusinessException be) {
		if (motDePasse == null || motDePasse.isBlank() || motDePasse.length() > 30) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_MOT_DE_PASSE_ERREUR);
		}
	}

	private void validerMDPCorrect(String motDePasse, String validationMDP, BusinessException be) {
		if (!motDePasse.trim().equals(validationMDP)) {
			be.ajouterErreur(CodeErreurBLLUtilisateur.REGLES_UTILISATEUR_VALIDER_MDP_ERREUR);
		}
	}

}
