package fr.eni.javaee.auctions.bo;

import java.io.Serializable;
/**
 * 
 * @author mberger2022
 *
 */
public class Retrait implements Serializable {
	//ATTRIBUTS D'INSTANCE
	private String rue; 			//NOT NULL - VARCHAR(30)
	private String codePostal; 		//NOT NULL - VARCHAR(15)
	private String ville; 			//NOT NULL - VARCHAR(30)
	private ArticleVendu article;
	
	//CONSTRUCTEUR VIDE
	public Retrait( ) { }
	
	public Retrait(String rue, String codePostal, String ville, ArticleVendu article) {
		this();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.article = article;
	}



	//GETTERS ET SETTERS
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public ArticleVendu getArticle() {
		return article;
	}
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}	

	

}
