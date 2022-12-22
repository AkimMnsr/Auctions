package fr.eni.javaee.auctions.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;


@WebServlet("/WelcomePageUser")
public class ServletAccueilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Utilisateur user = null;
		if (session != null) {
			user = (Utilisateur)session.getAttribute("utilisateur");
		}
	
		if (user == null) {
			System.out.println("[MB]Accueil-GET - Pas d'utilisateur connecté");
		} else {
			System.out.println("[MB]Accueil-GET - Utilisateur : " + user.getPseudo());
		}
				
		//lister les catégories
		List<Categorie> categories = CategorieManager.getInstance().selectAll();
		categories.add(new Categorie(0, "Toutes"));
		request.setAttribute("categories", categories);
		
		//récupération des filtres article et categorie (connecté ou pas)
		String filtreArticle = "";
		if (request.getAttribute("rechArticle") != null) {
			filtreArticle = (String)request.getAttribute("rechArticle");
			System.out.println("[MB]Accueil-GET - filtreArticle : " + filtreArticle);
		}
		int filtreCateg = 0;
		if (request.getAttribute("categChoisie") != null) {
			filtreCateg = (int)request.getAttribute("categChoisie");
			System.out.println("[MB]Accueil-GET - filtreCateg : " + filtreCateg);
		} else {
			//initialisation à valeur "Toutes" si pas de valeur déjà saisie
			request.setAttribute("categChoisie", 0);
		}
		String achatsVentes = "achats"; //TO DO A REMPLACER PAR UNE CONSTANTE INT OU BOOLEEN
		if (request.getParameter("achatsVentes") != null) {	
			achatsVentes = (String)request.getParameter("achatsVentes");
			System.out.println("[MB]Accueil-GET - achatsVentes : " + achatsVentes);
		}
		
		List<ArticleVendu> encheres = null;
		if (user == null) { // non connecté
			//lister toutes les articles en cours de vente (avec filtres éventuels)
			encheres = ArticleVenduManager.getInstance().selectAchatsAll(0, filtreArticle, filtreCateg);
		}
		else {	//connecté
			//lister toutes les enchères en cours
			
			if (achatsVentes.equals("achats")) { // Achats cochée
				boolean encheresOuvertes = false;  
				boolean mesEncheresEnCours = false;
				boolean mesEncheresGagnees = false;
				
				if (request.getAttribute("encheresOuvertes") == null && 
					request.getAttribute("mesEncheresEnCours") == null &&
					request.getAttribute("mesEncheresGagnees") == null) {
					encheresOuvertes = true;
				}
				
				if (request.getAttribute("encheresOuvertes") != null) {		
					encheresOuvertes = (boolean)request.getAttribute("encheresOuvertes");
					System.out.println("[MB]Accueil-GET - encheresOuvertes : " + encheresOuvertes
							           + ", " + user.getNoUtilisateur()
		                               + ", " + filtreArticle + ", " + filtreCateg);								
				}
				if (encheresOuvertes) {
					encheres = ArticleVenduManager.getInstance().selectAchatsAll(user.getNoUtilisateur(), filtreArticle, filtreCateg);
				}
				
				//lister toutes les enchères en cours de l'utilisateur connecté
				if (request.getAttribute("mesEncheresEnCours") != null) {		
					mesEncheresEnCours = (boolean)request.getAttribute("mesEncheresEnCours");
					System.out.println("[MB]Accueil-GET - mesEncheresEnCours : " + mesEncheresEnCours 
							            + ", " + user.getNoUtilisateur()
							            + ", " + filtreArticle + ", " + filtreCateg);								
				}
				if (mesEncheresEnCours) {
					encheres = ArticleVenduManager.getInstance().selectAchatsEnCours(user.getNoUtilisateur(), filtreArticle, filtreCateg);		
				}	
				
				//lister toutes les enchères gagnées de l'utilisateur connecté
				if (request.getAttribute("mesEncheresGagnees") != null) {		
					mesEncheresGagnees = (boolean)request.getAttribute("mesEncheresGagnees");
					System.out.println("[MB]Accueil-GET - mesEncheresGagnees : " + mesEncheresGagnees
				            + ", " + user.getNoUtilisateur()
				            + ", " + filtreArticle + ", " + filtreCateg);									
				}
				if (mesEncheresGagnees) {
					encheres = ArticleVenduManager.getInstance().selectAchatsGagnes(user.getNoUtilisateur(), filtreArticle, filtreCateg);		
				}
			} else { // "Ventes" cochée
					
				boolean mesVentesEnCours = false;
				boolean mesVentesNonDebutees = false;  
				boolean mesVentesTerminees = false;
				
				if (request.getAttribute("mesVentesEnCours") == null && 
					request.getAttribute("mesVentesNonDebutees") == null &&
					request.getAttribute("mesVentesTerminees") == null) {
					mesVentesEnCours = true;
					request.setAttribute("mesVentesEnCours", mesVentesEnCours);
				}
				if (request.getAttribute("mesVentesEnCours") != null) {
					mesVentesEnCours = (boolean)request.getAttribute("mesVentesEnCours");
				}
				if (request.getAttribute("mesVentesTerminees") != null) {
					mesVentesTerminees = (boolean)request.getAttribute("mesVentesTerminees");
				}
				if (mesVentesNonDebutees || mesVentesTerminees) {
					System.out.println("Partie VENTES : mesVentesEnCours, mesVentesTerminees NON GERE");  
				} else {
					encheres = ArticleVenduManager.getInstance().selectVentesParam(user.getNoUtilisateur(), user.getPseudo(),
							   filtreArticle, filtreCateg,
							   achatsVentes.equals("achats"), mesVentesEnCours,
							   mesVentesNonDebutees, mesVentesTerminees);
				}
			}
		}
		
		request.setAttribute("encheres", encheres);
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/accueilUtilisateur.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("[MB]Accueil-POST - rechArticle : " + request.getParameter("rechArticle"));
		//String articleSaisi = ""; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("rechArticle") != null) {
			String articleSaisi = request.getParameter("rechArticle");
			//System.out.println("[MB]Accueil-POST ATTR- articleSaisi : " + articleSaisi);
			request.setAttribute("rechArticle", articleSaisi);
		}
		//System.out.println("[MB]Accueil-POST - categSaisie : " + request.getParameter("categChoisie"));
		//int categSaisie = 0; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("categChoisie") != null) {
			int categSaisie = Integer.parseInt(request.getParameter("categChoisie"));
			//System.out.println("[MB]Accueil-POST ATTR- categSaisie : " + categSaisie);
			request.setAttribute("categChoisie", categSaisie);
		}	
		//System.out.println("[MB]Accueil-POST - achatsVentes : " + request.getParameter("achatsVentes"));
		//String achatsVentes = "achats"; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("achatsVentes") != null) {			
			String achatsVentes = request.getParameter("achatsVentes");
			//System.out.println("[MB]Accueil-POST ATTR- achatsVentes : "+ achatsVentes);
			request.setAttribute("achatsVentes", achatsVentes);
		} 		
		
		//System.out.println("[MB]Accueil-POST - mesEncheresEnCours : " + request.getParameter("mesEncheresEnCours"));
		if (request.getParameter("mesEncheresEnCours") != null) {
			boolean mesEncheresEnCours = (request.getParameter("mesEncheresEnCours").equals("mesEncheresEnCours") ? true : false);			
			//System.out.println("[MB]Accueil-POST ATTR- mesEncheresEnCours : " + mesEncheresEnCours);
			request.setAttribute("mesEncheresEnCours", mesEncheresEnCours);
		}
		//System.out.println("[MB]Accueil-POST - mesEncheresGagnees : " + request.getParameter("mesEncheresGagnees"));
		if (request.getParameter("mesEncheresGagnees") != null) {
			boolean mesEncheresGagnees = (request.getParameter("mesEncheresGagnees").equals("mesEncheresGagnees") ? true : false);
			//System.out.println("POST ATTR- mesEncheresGagnees : " + mesEncheresGagnees);
			request.setAttribute("mesEncheresGagnees", mesEncheresGagnees);
		}
		//System.out.println("[MB]Accueil-POST - encheresOuvertes : " + request.getParameter("encheresOuvertes"));
		if (request.getParameter("encheresOuvertes") != null) {
			boolean encheresOuvertes = (request.getParameter("encheresOuvertes").equals("encheresOuvertes") ? true : false);			
			//System.out.println("[MB]Accueil-POST ATTR- encheresOuvertes : " + encheresOuvertes);
			request.setAttribute("encheresOuvertes", encheresOuvertes);
		} 			
		
		//System.out.println("[MB]Accueil-POST - mesVentesEnCours : " + request.getParameter("mesVentesEnCours"));
		if (request.getParameter("mesVentesEnCours") != null) {
			boolean mesVentesEnCours = (request.getParameter("mesVentesEnCours").equals("mesVentesEnCours") ? true : false);			
			//System.out.println("[MB]Accueil-POST ATTR- mesVentesEnCours : " + mesVentesEnCours);
			request.setAttribute("mesVentesEnCours", mesVentesEnCours);
		}
		//System.out.println("[MB]Accueil-POST - mesVentesNonDebutees : " + request.getParameter("mesVentesNonDebutees"));
		if (request.getParameter("mesVentesNonDebutees") != null) {
			boolean mesVentesNonDebutees = (request.getParameter("mesVentesNonDebutees").equals("mesVentesNonDebutees") ? true : false);
			//System.out.println("[MB]Accueil-POST ATTR- mesVentesNonDebutees : " + mesVentesNonDebutees);
			request.setAttribute("mesVentesNonDebutees", mesVentesNonDebutees);
		}
		//System.out.println("[MB]Accueil-POST - mesVentesTerminees : " + request.getParameter("mesVentesTerminees"));
		if (request.getParameter("mesVentesTerminees") != null) {
			boolean mesVentesTerminees = (request.getParameter("mesVentesTerminees").equals("mesVentesTerminees") ? true : false);			
			//System.out.println("[MB]Accueil-POST ATTR- mesVentesTerminees : " + mesVentesTerminees);
			request.setAttribute("mesVentesTerminees", mesVentesTerminees);
		} 		
		
		doGet(request, response);
	}

}
