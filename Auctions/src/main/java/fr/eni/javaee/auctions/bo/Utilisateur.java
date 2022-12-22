package fr.eni.javaee.auctions.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bll.UtilisateurManager;

/**
 * 
 * @author mberger2022
 *
 */
public class Utilisateur implements Serializable {
	// ATTRIBUT D'INSTANCE
	private int noUtilisateur; // IDENTITY
	private String pseudo; // NOT NULL - VARCHAR(30)
	private String nom; // NOT NULL - VARCHAR(30)
	private String prenom; // NOT NULL - VARCHAR(30)
	private String email; // NOT NULL - VARCHAR(20)=>à augmenter ?
	private String telephone; // NULL - VARCHAR(15)
	private String rue; // NOT NULL - VARCHAR(30)
	private String codePostal; // NOT NULL - VARCHAR(10)
	private String ville; // NOT NULL - VARCHAR(30)
	private String motDePasse; // NOT NULL - VARCHAR(30)
	private int credit = 0; // NOT NULL
	private boolean administrateur = false; // NOT NULL
	private List<Enchere> encheres;

	// CONSTRUCTEUR VIDE
	public Utilisateur() {
		this.encheres = new ArrayList<Enchere>();
	}

	/**
    * @author mberger2022
    */
   public Utilisateur(int noUtilisateur) {
       this();
       this.noUtilisateur = noUtilisateur;
   }
   /**
    * @author mberger2022
    */
   public Utilisateur(int noUtilisateur, String pseudo) {
       this(noUtilisateur);
       this.pseudo = pseudo;
   }


	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;

	}
	
	

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville) {
		this();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse) {
		this();		
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
	}
	
	

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse) {
		this();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
	}

	// METHODES D'INSTANCE
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [noUtilisateur=");
		builder.append(getNoUtilisateur());
		builder.append(", pseudo=");
		builder.append(getPseudo());
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(getPrenom());
		builder.append(", email=");
		builder.append(getEmail());
		builder.append(", telephone=");
		builder.append(getTelephone());
		builder.append(", rue=");
		builder.append(getRue());
		builder.append(", codePostal=");
		builder.append(getCodePostal());
		builder.append(", ville=");
		builder.append(getVille());
		builder.append(", motDePasse=");
		builder.append(getMotDePasse());
		builder.append(", credit=");
		builder.append(getCredit());
		builder.append(", administrateur=");
		builder.append(administrateur);
		builder.append(", encheres=");
		builder.append(encheres);
		builder.append("]");
		return builder.toString();
	}

	public void ajouterEnchere(Enchere e) {
		if (e != null) {
			encheres.add(e);
		}
	}

	// GETTERS ET SETTERS
	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

}
