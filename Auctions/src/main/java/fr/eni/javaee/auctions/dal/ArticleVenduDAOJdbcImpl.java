package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.ArticleVendu;

/**
 * 
 * @author qswiderski2022
 *
 */

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres ,prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?);";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES (?,?,?,?);";

	@Override
	public void insert(ArticleVendu newVente) throws BusinessException {
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (newVente == null) {
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_NULL);
				throw be;
			}

			PreparedStatement pstmt = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newVente.getNomArticle());
			pstmt.setString(2, newVente.getDescription());
			pstmt.setDate(3, Date.valueOf(newVente.getDateDebEncheres()));
			pstmt.setDate(4, Date.valueOf(newVente.getDateFinEncheres()));
			pstmt.setInt(5, newVente.getMiseAPrix());
			pstmt.setInt(6, newVente.getProprietaire().getNoUtilisateur());
			pstmt.setInt(7, newVente.getCategorie().getNoCategorie());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int no_article = rs.getInt(1);
				newVente.setNoArticle(no_article);

				PreparedStatement pstmt2 = cnx.prepareStatement(INSERT_RETRAIT);
				pstmt2.setInt(1, no_article);
				pstmt2.setString(2, newVente.getLieuRetrait().getRue());
				pstmt2.setString(3, newVente.getLieuRetrait().getCodePostal());
				pstmt2.setString(4, newVente.getLieuRetrait().getVille());
				pstmt2.executeUpdate();
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_ECHEC);
			throw be;
		}
	}

	@Override
	public List<ArticleVendu> selectVentes(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateVente(ArticleVendu a) {
		// TODO Auto-generated method stub

	}

}
