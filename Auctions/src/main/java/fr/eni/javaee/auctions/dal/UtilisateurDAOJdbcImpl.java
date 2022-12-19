package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	private static final String MODIFIER = "UPDATE Utilisateurs set pseudo = ?, set nom = ? , set prenom = ?, set email = ?, set telephone = ?, set rue = ?, set code_postal = ?, set ville = ?, set mot_de_passe = ? WHERE pseudo = ? and mot_de_passe = ?;";
	private static final String INSERT = "INSERT INTO Utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ID_CONNEXION = " SELECT * FROM utilisateurs WHERE (pseudo = ? or email = ?) and mot_de_passe = ? ;";

	@Override
	public void insert(Utilisateur utilisateur) throws SQLException, BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection()) {

			if (utilisateur == null) {
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesErreursUtilisateurDAL.INSERT_OBJECT_NULL);
				throw be;
			}

			PreparedStatement pstmt = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getCredit());
			pstmt.setBoolean(11, false);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreursUtilisateurDAL.INSERT_OBJECT_ECHEC);
			throw be;
		}
	}

	public Utilisateur verifUtilisateur(String pseudo, String mdp) throws SQLException, BusinessException {

		Utilisateur utilisateurCnx = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID_CONNEXION);
			ResultSet rs = null;
			pstmt.setString(1, pseudo);
			pstmt.setString(2, pseudo);
			pstmt.setString(3, mdp);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				utilisateurCnx = new Utilisateur();
				utilisateurCnx.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateurCnx.setPseudo(rs.getString("pseudo"));
				utilisateurCnx.setNom(rs.getString("nom"));
				utilisateurCnx.setPrenom(rs.getString("prenom"));
				utilisateurCnx.setEmail(rs.getString("email"));
				utilisateurCnx.setTelephone(rs.getString("telephone"));
				utilisateurCnx.setRue(rs.getString("rue"));
				utilisateurCnx.setCodePostal(rs.getString("code_postal"));
				utilisateurCnx.setVille(rs.getString("ville"));
				utilisateurCnx.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateurCnx.setCredit(rs.getInt("credit"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			throw be;
		}
		System.out.println(utilisateurCnx);
		return utilisateurCnx;
	}

	@Override
	public void modifier(Utilisateur utilisateur, String pseudoSession, String mdpSession) throws BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(MODIFIER);		
			
		
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());			
			if (utilisateur.getMotDePasse().equals("") || utilisateur.getMotDePasse().isBlank() || utilisateur.getMotDePasse() == null) {
				pstmt.setString(9, mdpSession);
			} else {
				pstmt.setString(9, utilisateur.getMotDePasse());
				
			}		
			pstmt.setString(10, pseudoSession);
			pstmt.setString(11, mdpSession);
		
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			throw be;
		}
	}

}
