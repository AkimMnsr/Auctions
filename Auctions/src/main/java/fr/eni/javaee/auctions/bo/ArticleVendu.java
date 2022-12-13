package fr.eni.javaee.auctions.bo;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * 
 * @author mberger2022
 *
 */
public class ArticleVendu implements Serializable {
	//ATTRIBUTS D'INSTANCE
	private int noArticle;  			//IDENTITY
	private String nomArticle;  		//NOT NULL - VARCHAR(30)
	private String description; 		//NOT NULL - VARCHAR(300)
	private LocalDate dateDebEncheres; 	//NOT NULL
	private LocalDate dateFinEncheres; 	//NOT NULL
	private int miseAPrix; 				//NULL
	private int prixVente; 				//NULL
	private Categorie categorie;
	private Utilisateur vendeur;
	private Utilisateur acquereur;
	private Retrait lieuRetrait;
	
	//CONSTRUCTEUR VIDE
	public ArticleVendu () { }
		
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebEncheres,
			            LocalDate dateFinEncheres, int miseAPrix, Categorie categorie, Utilisateur vendeur) {
		this();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebEncheres = dateDebEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.categorie = categorie;
		this.vendeur = vendeur;
		//initialisation du lieu de retrait avec l'adresse du vendeur
		this.lieuRetrait = new Retrait(vendeur.getRue(), vendeur.getCodePostal(),
									   vendeur.getVille(), this);
	}

	//METHODES D'INSTANCE
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleVendu [noArticle=");
		builder.append(getNoArticle());
		builder.append(", nomArticle=");
		builder.append(getNomArticle());
		builder.append(", description=");
		builder.append(getDescription());
		builder.append(", dateDebEncheres=");
		builder.append(getDateDebEncheres());
		builder.append(", dateFinEncheres=");
		builder.append(getDateFinEncheres());
		builder.append(", miseAPrix=");
		builder.append(getMiseAPrix());
		builder.append(", prixVente=");
		builder.append(getPrixVente());
		builder.append(", vendeur=");
		builder.append(getVendeur());
		builder.append(", acquereur=");
		builder.append(getAcquereur());
		builder.append(", retrait)=");
		builder.append(getLieuRetrait());
		builder.append("]");
		return builder.toString();
	}

	//GETTERS ET SETTERS
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDateDebEncheres() {
		return dateDebEncheres;
	}
	public void setDateDebEncheres(LocalDate dateDebEncheres) {
		this.dateDebEncheres = dateDebEncheres;
	}
	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}
	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	public int getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Utilisateur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}
	public Utilisateur getAcquereur() {
		return acquereur;
	}
	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}
	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}
	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}
		
}
