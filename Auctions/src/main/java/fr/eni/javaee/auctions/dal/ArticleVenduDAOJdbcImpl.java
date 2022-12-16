package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.be.BusinessException;

/**
 * 
 * @author qswiderski2022
 *
 */
public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres ,prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?);";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES (?,?,?,?);";

	private static final String SELECT_ARTICLES_ALL = 
			"SELECT no_article, nom_article, prix_vente, prix_initial, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Articles_vendus a"
			+ " INNER JOIN Utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_debut_encheres <= GETDATE()"
			+ " AND date_fin_encheres >= GETDATE()";
	
	private static final String SELECT_ENCHERES_EN_COURS = 
			"SELECT e.no_article as no_art, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Encheres e"
			+ " INNER JOIN Articles_vendus a ON e.no_article = a.no_article"
			+ " INNER JOIN Utilisateurs u ON  a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_debut_encheres <= GETDATE()"
			+ " AND date_fin_encheres >= GETDATE()"
			+ " AND e.no_utilisateur = ?";
	
	private static final String SELECT_ENCHERES_GAGNEES = 
			"SELECT e.no_article as no_art, nom_article, prix_vente, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Encheres e"
			+ " INNER JOIN Articles_vendus a ON e.no_article = a.no_article"
			+ " INNER JOIN Utilisateurs u ON  a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_fin_encheres < GETDATE()"
			+ " AND e.no_utilisateur = ?";

													
	/**
	 * @author mberger2022
	 */
	@Override
	public List<ArticleVendu> selectArticlesAll(String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = ajouterFiltres(SELECT_ARTICLES_ALL,filtreArticle, filtreCategorie);
						
			PreparedStatement pstmt = cnx.prepareStatement(requeteAvecFiltre);
			
			// Catégorie différente de "0-Toutes" ET article renseigné
			if (filtreCategorie != 0 && ! filtreArticle.equals("")) { 
				pstmt.setInt(1, filtreCategorie);
				pstmt.setString(2, "%"+filtreArticle+"%");
			} else if (filtreCategorie != 0) { // uniquement catégorie différente de "0-Toutes"
				pstmt.setInt(1, filtreCategorie);
			} else if (! filtreArticle.equals("")) { // uniquement article renseigné
				pstmt.setString(1, "%"+filtreArticle+"%");
			} 
						
			//3. résultat
			ResultSet rs = pstmt.executeQuery();
			
			//4. parcours du résultat 
			// (obligation de faire un next() pour se positionner sur le 1er)
			Utilisateur vendeur = null;
			ArticleVendu article = null;
			while (rs.next()) {
				int noArt = rs.getInt("no_article");
				String nomArt = rs.getString("nom_article");
				int prixVente = rs.getInt("prix_vente");
				int prixInitial = rs.getInt("prix_initial");
				if (prixVente == 0) {
					prixVente = prixInitial;
				}
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateFin, prixVente, vendeur);
				articles.add(article);
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		
		return articles;
	}
	
	
	
	/**
	 * @author mberger2022
	 */
	@Override
	public List<ArticleVendu> selectEncheresEnCours(int idUser, String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> encheres = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = ajouterFiltres(SELECT_ENCHERES_EN_COURS,filtreArticle, filtreCategorie);
						
			PreparedStatement pstmt = cnx.prepareStatement(requeteAvecFiltre);
			
			pstmt.setInt(1, idUser);
			// Catégorie différente de "0-Toutes" ET article renseigné
			if (filtreCategorie != 0 && ! filtreArticle.equals("")) { 
				pstmt.setInt(2, filtreCategorie);
				pstmt.setString(3, "%"+filtreArticle+"%");
			} else if (filtreCategorie != 0) { // uniquement catégorie différente de "0-Toutes"
				pstmt.setInt(2, filtreCategorie);
			} else if (! filtreArticle.equals("")) { // uniquement article renseigné
				pstmt.setString(2, "%"+filtreArticle+"%");
			} 
						
			//3. résultat
			ResultSet rs = pstmt.executeQuery();
			
			//4. parcours du résultat 
			// (obligation de faire un next() pour se positionner sur le 1er)
			Utilisateur vendeur = null;
			ArticleVendu article = null;
			while (rs.next()) {
				int noArt = rs.getInt("no_art");
				String nomArt = rs.getString("nom_article");
				int prixVente = rs.getInt("prix_vente");
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateFin, prixVente, vendeur);
				encheres.add(article);
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		
		return encheres;
	}
	
	/**
	 * @author mberger2022
	 */
	public List<ArticleVendu> selectEncheresGagnees(int idUser, String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> encheres = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = ajouterFiltres(SELECT_ENCHERES_GAGNEES,filtreArticle, filtreCategorie);
						
			PreparedStatement pstmt = cnx.prepareStatement(requeteAvecFiltre);
			
			pstmt.setInt(1, idUser);
			// Catégorie différente de "0-Toutes" ET article renseigné
			if (filtreCategorie != 0 && ! filtreArticle.equals("")) { 
				pstmt.setInt(2, filtreCategorie);
				pstmt.setString(3, "%"+filtreArticle+"%");
			} else if (filtreCategorie != 0) { // uniquement catégorie différente de "0-Toutes"
				pstmt.setInt(2, filtreCategorie);
			} else if (! filtreArticle.equals("")) { // uniquement article renseigné
				pstmt.setString(2, "%"+filtreArticle+"%");
			} 
						
			//3. résultat
			ResultSet rs = pstmt.executeQuery();
			
			//4. parcours du résultat 
			// (obligation de faire un next() pour se positionner sur le 1er)
			Utilisateur vendeur = null;
			ArticleVendu article = null;
			while (rs.next()) {
				int noArt = rs.getInt("no_art");
				String nomArt = rs.getString("nom_article");
				int prixVente = rs.getInt("prix_vente");
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateFin, prixVente, vendeur);
				encheres.add(article);
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		
		return encheres;
	}

	
	/**
	 * @author mberger2022
	 */
	private String ajouterFiltres(String requeteInit, String filtreArticle, int filtreCategorie) {
		String requeteAvecFiltre = requeteInit;
		if (filtreCategorie != 0) { // différent de "Toutes"
			requeteAvecFiltre = requeteAvecFiltre + " AND no_categorie = ?";
		} 
		if (! filtreArticle.equals("")) { // non vide
			requeteAvecFiltre = requeteAvecFiltre + " AND nom_article like ?";
		} 
		requeteAvecFiltre = requeteAvecFiltre + ";";
		
		return requeteAvecFiltre;
	}
	
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
