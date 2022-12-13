package fr.eni.javaee.auctions.bo;

import java.io.Serializable;
/**
 * 
 * @author mberger2022
 *
 */
public class Categorie implements Serializable {
	//ATTRIBUTS D'INSTANCE
	private int noCategorie; 	//IDENTITY
	private String libelle; 	//NOT NULL - VARCHAR(30)
	
	//CONSTRUCTEUR VIDE
	public Categorie () { }
		
	public Categorie(int noCategorie, String libelle) {
		this();
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	//METHODES D'INSTANCE
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [noCategorie=");
		builder.append(getNoCategorie());
		builder.append(", libelle()=");
		builder.append(getLibelle());
		builder.append("]");
		return builder.toString();
	}

	//GETTERS ET SETTERS
	public int getNoCategorie() {
		return noCategorie;
	}
	
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	
	

}
