package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;


/**
 * 
 * @author mberger2022
 *
 */
public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	private static final String SELECT_MEILLEURE_ENCHERE =
			"SELECT e.no_utilisateur as no_user, pseudo, credit"
			+ " FROM Encheres e"
			+ " INNER JOIN Utilisateurs u ON e.no_utilisateur = u.no_utilisateur"
			+ " WHERE no_article = ?"
			+ "   AND montant_enchere = ?;";	
	private static final String INSERT = 
			"INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, GETDATE(), ?);";
	
	
	/**
	 * @author mberger2022
	 * @param idArticle : numéro de l'article recherché
	 * @return objet de type Utilisateur correspondant à la meilleure enchère 
	 *         pour l'article dont le numéro passé en paramètre
	 */
	@Override
	public Enchere selectMeilleureEnchere(int idArticle, int montantMax) {
		Enchere enchereMax = null;	
		
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_MEILLEURE_ENCHERE);
			
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, montantMax);
											
			//3. résultat
			ResultSet rs = pstmt.executeQuery();
			
			//4. parcours du résultat 
			// (obligation de faire un next() pour se positionner sur l'enregistrement)
			if (rs.next()) {
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				int credit = rs.getInt("credit");
				
				Utilisateur gagnant = new Utilisateur(noUser, pseudo);
				gagnant.setCredit(credit);
				ArticleVendu article = DAOFactory.getArticleVenduDAO().selectById(idArticle);
				enchereMax = new Enchere(montantMax, gagnant, article); 
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		
		return enchereMax;
	}
	/**
	 * @author mberger2022
	 * @param enchere : nouvelle enchère à enregistrer en BDD
	 * @param idAcheteurPrec : numéro utilisateur du précédent enchérisseur
	 * @throws BusinessException 
	 */
	@Override
	public void insertNouvelleEnchere(Enchere enchere, int idAcheteurPrec) throws BusinessException  {
		
		if (enchere == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursEnchereDAL.INSERT_OBJECT_NULL);
			throw be;
		} else {	
			//1.connexion
			try (Connection cnx = ConnectionProvider.getConnection()) {
				
				try { //2eme try...catch utilisé pour pouvoir faire le rollback en cas de problème
					cnx.setAutoCommit(false);
						
					//RECREDITER LE CREDIT DU PRECEDENT ACHETEUR DU MONTANT DE SON ENCHERE (= PRIX DE VENTE AVANT MAJ DE L'ARTICLE)
					Utilisateur ancienAcheteur = DAOFactory.getUtilisateurDAO().profilUtilisateur(idAcheteurPrec, cnx);
					
					int nouveauCredit = 0;
					// SI MEME ACHETEUR, DEBIT-CREDIT SUR LA MEME "OPERATION"
					if (ancienAcheteur.getNoUtilisateur() == enchere.getAcheteur().getNoUtilisateur()) {
						nouveauCredit = ancienAcheteur.getCredit() 
						              + enchere.getArticle().getPrixVente()
							          - enchere.getMontantEnchere();
						System.out.println("DEBITER-CREDITER : " + ancienAcheteur.getPseudo() + " - Crédit init: " + ancienAcheteur.getCredit()
						   + " // " + enchere.getArticle().getPrixVente() + " // " + enchere.getMontantEnchere() 
						   + " // " + nouveauCredit);	
						
					} else {
						 nouveauCredit = ancienAcheteur.getCredit() + enchere.getArticle().getPrixVente();
						 System.out.println("CREDITER : " + ancienAcheteur.getPseudo() + " - Crédit init: " + ancienAcheteur.getCredit()
						   + " // " + ancienAcheteur.getCredit() + " // " + enchere.getArticle().getPrixVente() 
						   + " // " + nouveauCredit);						
					}
					ancienAcheteur.setCredit(nouveauCredit);
					DAOFactory.getUtilisateurDAO().modifierCredit(ancienAcheteur);
					
					//CREER UN NOUVEL ENREGISTREMENT (TABLE ENCHERES)
					PreparedStatement pstmt = cnx.prepareStatement(INSERT);
					pstmt.setInt(1, enchere.getAcheteur().getNoUtilisateur());
					pstmt.setInt(2, enchere.getArticle().getNoArticle());
					pstmt.setInt(3, enchere.getMontantEnchere());
					
					pstmt.executeUpdate();
							
					//METTRE A JOUR LE PRIX DE VENTE DE L'ARTICLE
					ArticleVendu article = enchere.getArticle();
					article.setPrixVente(enchere.getMontantEnchere());
					DAOFactory.getArticleVenduDAO().updatePrixVente(article);
					
					//DECREMENTER LE CREDIT DU NOUVEL ACHETEUR DU MONTANT DE LA PROPOSITION 
					//SAUF SI IDENTIQUE AU PRECEDENT ENCHERISSEUR (DEJA FAIT DANS LA MEME OPERATION)
					if (ancienAcheteur.getNoUtilisateur() != enchere.getAcheteur().getNoUtilisateur()) {
						Utilisateur nouvelAcheteur = enchere.getAcheteur();
						nouveauCredit = nouvelAcheteur.getCredit() - article.getPrixVente();
						System.out.println("DEBITER : " + nouvelAcheteur.getPseudo() + " - Crédit init: " + nouvelAcheteur.getCredit()
						   + " // " + nouvelAcheteur.getCredit() + " // " + article.getPrixVente() 
						   + " // " + nouveauCredit);
						nouvelAcheteur.setCredit(nouveauCredit);
						DAOFactory.getUtilisateurDAO().modifierCredit(nouvelAcheteur);
					}
					cnx.commit();
					
					//cnx.setAutoCommit(true) automatique à la fermeture de la connexion
				} catch (SQLException e) {
					e.printStackTrace();
					cnx.rollback();
					
					BusinessException be = new BusinessException();
					be.ajouterErreur(CodesErreursEnchereDAL.INSERT_ECHEC);
					throw be;
				}				
			} catch (SQLException e) { //catch en cas de problème de connexion seulement
				e.printStackTrace();
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesErreursEnchereDAL.INSERT_ECHEC);
				throw be;
			}
		}
	}

}
