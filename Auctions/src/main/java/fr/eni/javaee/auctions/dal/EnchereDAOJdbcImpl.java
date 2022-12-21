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

	@Override
	public void insertNouvelleEnchere(Enchere enchere, int idAcheteurPrec) {
		
		//RECREDITER LE CREDIT DU PRECEDENT ACHETEUR DU MONTANT DE SON ENCHERE
		
		
		//CREER UN NOUVEL ENREGISTREMENT (TABLE ENCHERES)
		
				
		//METTRE A JOUR LE PRIX DE VENTE DE L'ARTICLE
		
		
		//DECREMENTER LE CREDIT DU NOUVEL ACHETEUR DU MONTANT DE LA PROPOSITION 
		
		
		
		
		
		
		
	}

}
