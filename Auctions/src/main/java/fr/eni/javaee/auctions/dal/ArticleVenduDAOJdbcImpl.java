package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
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

	//rajout distanciel
	 private static final String UPDATE_ARTICLE = "UPDATE Articles SET nom_article = ?, description = ?, date_debut_encheres = ?, " +
	            "date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_categorie = ? WHERE no_article = ?";
	 private static final String UPDATE_RETRAIT = "UPDATE Retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	 private static final String DELETE_ARTICLE = "DELETE FROM Articles WHERE no_article = ?";
	
	
	private static final String SELECT_ARTICLES_ALL = 
			"SELECT no_article, nom_article, prix_vente, prix_initial, date_debut_encheres, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Articles_vendus a"
			+ " INNER JOIN Utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_debut_encheres <= GETDATE()"
			+ " AND date_fin_encheres >= GETDATE()";		
	
	private static final String SELECT_ACHATS_EN_COURS = 
			"SELECT e.no_article as no_art, nom_article, prix_vente, date_debut_encheres, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Encheres e"
			+ " INNER JOIN Articles_vendus a ON e.no_article = a.no_article"
			+ " INNER JOIN Utilisateurs u ON  a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_debut_encheres <= GETDATE()"
			+ " AND date_fin_encheres >= GETDATE()"
			+ " AND e.no_utilisateur = ?";
	
	private static final String SELECT_ACHATS_GAGNES = 
			"SELECT e.no_article as no_art, nom_article, prix_vente, date_debut_encheres, date_fin_encheres, a.no_utilisateur as no_user, pseudo"
			+ " FROM Encheres e"
			+ " INNER JOIN Articles_vendus a ON e.no_article = a.no_article"
			+ " INNER JOIN Utilisateurs u ON  a.no_utilisateur = u.no_utilisateur"
			+ " WHERE date_fin_encheres < GETDATE()"
			+ " AND e.no_utilisateur = ?";

	private static final String SELECT_BY_ID = 
			"SELECT nom_article, description, libelle, prix_vente, prix_initial, date_debut_encheres, date_fin_encheres,"
			+ " r.rue as rue, r.code_postal as cp, r.ville as ville, a.no_utilisateur as no_user, pseudo"
			+ " FROM Articles_vendus a"
			+ " INNER JOIN Utilisateurs u ON a.no_utilisateur = u.no_utilisateur"
			+ " INNER JOIN Categories c ON a.no_categorie = c.no_categorie"
			+ " INNER JOIN Retraits r on a.no_article = r.no_article"
			+ " WHERE a.no_article = ?;";	
			
	private static final String UPDATE_PRIX =
			"UPDATE Articles_vendus SET prix_vente = ? WHERE no_article = ?;";
	
	/**
	 * @author mberger2022
	 */
	@Override
	public List<ArticleVendu> selectAchatsAll(int idUser, String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> articles = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = SELECT_ARTICLES_ALL;
			
			if (idUser != 0) {
				requeteAvecFiltre = requeteAvecFiltre + " AND a.no_utilisateur != ?";
			}
			requeteAvecFiltre = ajouterFiltres(requeteAvecFiltre, filtreArticle, filtreCategorie);
						
			PreparedStatement pstmt = cnx.prepareStatement(requeteAvecFiltre);
			
			//utilisateur renseigné si connecté
			if (idUser != 0) {				
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
			} else {
				if (filtreCategorie != 0 && ! filtreArticle.equals("")) { 			
					pstmt.setInt(1, filtreCategorie);
					pstmt.setString(2, "%"+filtreArticle+"%");
				} else if (filtreCategorie != 0) { // uniquement catégorie différente de "0-Toutes"
					pstmt.setInt(1, filtreCategorie);
				} else if (! filtreArticle.equals("")) { // uniquement article renseigné
					pstmt.setString(1, "%"+filtreArticle+"%");
				} 
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
				LocalDate dateDeb = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateDeb, dateFin, prixVente, vendeur);
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
	public List<ArticleVendu> selectAchatsEnCours(int idUser, String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> encheres = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = ajouterFiltres(SELECT_ACHATS_EN_COURS, filtreArticle, filtreCategorie);
						
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
				LocalDate dateDeb = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateDeb, dateFin, prixVente, vendeur);
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
	public List<ArticleVendu> selectAchatsGagnes(int idUser, String filtreArticle, int filtreCategorie) {
		List<ArticleVendu> encheres = new ArrayList<ArticleVendu>();
		
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			String requeteAvecFiltre = ajouterFiltres(SELECT_ACHATS_GAGNES,filtreArticle, filtreCategorie);
						
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
				LocalDate dateDeb = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				vendeur = new Utilisateur(noUser, pseudo);
				article = new ArticleVendu(noArt, nomArt, dateDeb, dateFin, prixVente, vendeur);
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
	@Override
	public List<ArticleVendu> selectVentesParam(int idUser, String pseudoUser, String filtreArticle, int filtreCategorie, 
			                                    boolean achatsVentes, boolean mesVentesEnCours,
			                                    boolean mesVentesNonDebutees, boolean mesVentesTerminees) {
		List<ArticleVendu> ventes = new ArrayList<ArticleVendu>();
	
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			//2. requête
			String requete = construireRequeteVentes(filtreArticle, filtreCategorie, achatsVentes, 
													 mesVentesEnCours, mesVentesNonDebutees, mesVentesTerminees);
			
			requete = ajouterFiltres(requete, filtreArticle, filtreCategorie);
						
			PreparedStatement pstmt = cnx.prepareStatement(requete);
					
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
				int noArt = rs.getInt("no_article");
				String nomArt = rs.getString("nom_article");
				int prixVente = rs.getInt("prix_vente");
				int prixInitial = rs.getInt("prix_initial");
				if (prixVente == 0) {
					prixVente = prixInitial;
				}
				LocalDate dateDeb = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				int noUser = rs.getInt("no_utilisateur");
							
				vendeur = new Utilisateur(noUser, pseudoUser);
				article = new ArticleVendu(noArt, nomArt, dateDeb, dateFin, prixVente, vendeur);
				ventes.add(article);
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		

		return ventes;
	}

	/**
	 * @author mberger2022
	 */
	private String construireRequeteVentes(String filtreArticle, int filtreCategorie, boolean achatsVentes,
			boolean mesVentesEnCours, boolean mesVentesNonDebutees, boolean mesVentesTerminees) {
		String requete = "";
		if (mesVentesEnCours && !mesVentesNonDebutees && !mesVentesTerminees) {			
			requete = "SELECT no_article, nom_article, prix_vente, prix_initial, date_debut_encheres, date_fin_encheres, no_utilisateur"
			        + " FROM Articles_vendus "
				    + " WHERE date_debut_encheres <= GETDATE()"
				    + " AND date_fin_encheres >= GETDATE()"
				    + " AND no_utilisateur = ?";
		} else if (mesVentesNonDebutees) {
			requete = "SELECT no_article, nom_article, prix_vente, prix_initial, date_debut_encheres, date_fin_encheres, no_utilisateur"
			        + " FROM Articles_vendus "
				    + " WHERE date_debut_encheres > GETDATE()"
				    + " AND no_utilisateur = ?";
		}
		
		return requete;
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
	
	/**
	 * @author mberger2022
	 * @param idArticle : numéro de l'article recherché
	 * @return objet de type ArticleVendu correspondant au numéro passé en paramètre
	 */	
	@Override
	public ArticleVendu selectById(int idArticle) {
		ArticleVendu article = null;
		
		System.out.println("DAL idArticle: " + idArticle);
		//1.connexion
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//2. requête
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			
			pstmt.setInt(1, idArticle);
											
			//3. résultat
			ResultSet rs = pstmt.executeQuery();
			
			//4. parcours du résultat 
			// (obligation de faire un next() pour se positionner sur l'enregistrement)
			if (rs.next()) {
				String nomArt = rs.getString("nom_article");
				String description = rs.getString("description");
				String categLibelle = rs.getString("libelle");
				int prixVente = rs.getInt("prix_vente");
				int prixInitial = rs.getInt("prix_initial");
				LocalDate dateDeb = rs.getDate("date_debut_encheres").toLocalDate();
				LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
				String rue = rs.getString("rue");
				String codePostal = rs.getString("cp");
				String ville = rs.getString("ville");
				int noUser = rs.getInt("no_user");
				String pseudo = rs.getString("pseudo");
				
				System.out.println("DAL retrait :" + rue + ", " + codePostal + "," + ville);
				Utilisateur vendeur = new Utilisateur(noUser, pseudo);
				Categorie categorie = new Categorie(categLibelle);
				Retrait retrait = new Retrait(rue, codePostal, ville);
				article = new ArticleVendu(nomArt, description, dateDeb, dateFin, prixInitial, categorie, vendeur, retrait);
				article.setNoArticle(idArticle);
				article.setPrixVente(prixVente);						
			}
		} catch (SQLException e) {
			//erreur connexion base
			e.printStackTrace();
		}		
		System.out.println("DAL article: " + article);
		return article;
	}
	
	/**
	 * @author mberger2022
	 * @param objet de type ArticleVendu dont on souhaite mettre à jour le prix
	 * @throws BusinessException 
	 */	
	public void updatePrixVente (ArticleVendu article) throws BusinessException {
				
		if (article == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_NULL);
			throw be;
		}
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_PRIX);
			pstmt.setInt(1, article.getPrixVente());
			pstmt.setInt(2, article.getNoArticle() );
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_ECHEC);
			throw be;
		}		
	}
	
	
	/**
	 * @author qswiderski2022
	 * 
	 */
	@Override
	public void insert(ArticleVendu newVente) throws BusinessException {
		ResultSet rs = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (newVente == null) {
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_NULL);
				throw be;
			}

			PreparedStatement pstmtArticle = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			pstmtArticle.setString(1, newVente.getNomArticle());
			pstmtArticle.setString(2, newVente.getDescription());
			pstmtArticle.setDate(3, Date.valueOf(newVente.getDateDebEncheres()));
			pstmtArticle.setDate(4, Date.valueOf(newVente.getDateFinEncheres()));
			pstmtArticle.setInt(5, newVente.getMiseAPrix());
			pstmtArticle.setInt(6, newVente.getProprietaire().getNoUtilisateur());
			pstmtArticle.setInt(7, newVente.getCategorie().getNoCategorie());
			pstmtArticle.executeUpdate();

			rs = pstmtArticle.getGeneratedKeys();
			if (rs.next()) {
				int no_article = rs.getInt(1);
				newVente.setNoArticle(no_article);

				PreparedStatement pstmtRetrait = cnx.prepareStatement(INSERT_RETRAIT);
				pstmtRetrait.setInt(1, no_article);
				pstmtRetrait.setString(2, newVente.getLieuRetrait().getRue());
				pstmtRetrait.setString(3, newVente.getLieuRetrait().getCodePostal());
				pstmtRetrait.setString(4, newVente.getLieuRetrait().getVille());
				pstmtRetrait.executeUpdate();
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursArticleDAL.INSERT_OBJECT_ECHEC);
			throw be;
		}
	}


	/**
	 * @author qswiderski2022
	 */
	@Override
	public void updateVente(ArticleVendu a) throws BusinessException{

		        if(a.getNoArticle()==0) {
		            BusinessException businessException = new BusinessException();
		            businessException.ajouterErreur(CodesErreursArticleDAL.UPDATE_ARTICLE_NULL_ECHEC);
		            throw businessException;
		        }

		        try(Connection con = ConnectionProvider.getConnection()) {

		            PreparedStatement pstmtArticle = con.prepareStatement(UPDATE_ARTICLE);
		            PreparedStatement pstmtRetrait = con.prepareStatement(UPDATE_RETRAIT);

		            pstmtArticle.setString(1, a.getNomArticle());
		            pstmtArticle.setString(2, a.getDescription());
		            pstmtArticle.setDate(3, Date.valueOf(a.getDateDebEncheres()));
		            pstmtArticle.setDate(4, Date.valueOf(a.getDateFinEncheres()));
		            pstmtArticle.setInt(5, a.getMiseAPrix());
		            pstmtArticle.setInt(6, a.getPrixVente());
		            pstmtArticle.setInt(7, a.getCategorie().getNoCategorie());
		            pstmtArticle.setInt(8, a.getNoArticle());

		            pstmtArticle.executeUpdate();

		            pstmtRetrait.setString(1, a.getLieuRetrait().getRue());
		            pstmtRetrait.setString(2, a.getLieuRetrait().getCodePostal());
		            pstmtRetrait.setString(3, a.getLieuRetrait().getVille());
		            pstmtRetrait.setInt(4, a.getNoArticle());

		            pstmtArticle.close();
		            pstmtRetrait.close();

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            BusinessException businessException = new BusinessException();
		            businessException.ajouterErreur(CodesErreursArticleDAL.UPDATE_ARTICLE_ECHEC);
		            throw businessException;
		        }
		    }
	
    
    public void delete(ArticleVendu a) throws BusinessException{

        if( a.getNoArticle()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreursArticleDAL.DELETE_ARTICLE_NULL);
            throw businessException;
        }

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pstmtArticle = con.prepareStatement(DELETE_ARTICLE);

            pstmtArticle.setInt(1, a.getNoArticle());
            pstmtArticle.executeUpdate();

            pstmtArticle.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreursArticleDAL.DELETE_ARTICLE_ECHEC);
            throw businessException;
        }
    }

	
	
	}
	

