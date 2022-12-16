package fr.eni.javaee.auctions.dal;

import java.util.List;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import fr.eni.javaee.auctions.be.BusinessException;


public interface ArticleVenduDAO {

	void insert(ArticleVendu newVente) throws BusinessException;

	
	List<ArticleVendu> selectVentes(int idUser);

	void updateVente(ArticleVendu a);
	

}
