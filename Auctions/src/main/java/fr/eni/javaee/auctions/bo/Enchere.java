package fr.eni.javaee.auctions.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @author mberger2022
 *
 */
public class Enchere implements Serializable {
	//ATTRIBUTS D'INSTANCE
	private int noEnchere; 				//IDENTITY
	private LocalDateTime dateEnchere; 	//NOT NULL
	private int montantEnchere = 0;		//NOT NULL
	private Utilisateur acheteur;		//NOT NULL
	private ArticleVendu article;  		//NOT NULL
	
	//CONSTRUCTEUR VIDE
	public Enchere () { }

	public Enchere(int montantEnchere, 
			   Utilisateur acheteur, ArticleVendu article) {
	this();
	this.montantEnchere = montantEnchere;
	this.acheteur = acheteur;
	this.article = article;
	}
	
	public Enchere(int noEnchere, LocalDateTime dateEnchere, int montantEnchere, 
				   Utilisateur acheteur, ArticleVendu article) {
		this(montantEnchere, acheteur, article);
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
	}
	
	//METHODES D'INSTANCE
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [noEnchere=");
		builder.append(getNoEnchere());
		builder.append(", dateEnchere=");
		builder.append(getDateEnchere());
		builder.append(", montantEnchere=");
		builder.append(getMontantEnchere());
		builder.append(", acheteur=");
		builder.append(getAcheteur());
		builder.append(", article=");
		builder.append(getArticle());
		builder.append("]");
		return builder.toString();
	}


	//GETTERS ET SETTERS
	public int getNoEnchere() {
		return noEnchere;
	}
	
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}
	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public Utilisateur getAcheteur() {
		return acheteur;
	}
	public void setAcheteur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}
	public ArticleVendu getArticle() {
		return article;
	}
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}
	
}
