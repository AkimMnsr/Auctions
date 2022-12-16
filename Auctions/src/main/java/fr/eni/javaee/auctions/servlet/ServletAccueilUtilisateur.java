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

		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
	/*	Utilisateur user = new Utilisateur("marieb44", "BERGER", "Marie", "mb@aol.com", 
                "0612345678", "4 rue James Lloyd", "44300", "Nantes", "bidule", 345, false);
		user.setNoUtilisateur(10);
		session.setAttribute("user", user);*/
	
		if (user == null) {
			System.out.println("GET - Pas d'utilisateur connecté");
		} else {
			System.out.println("GET - Utilisateur : " + user.getPseudo());
		}
				
		//lister les catégories
		List<Categorie> categories = CategorieManager.getInstance().selectAll();
		categories.add(new Categorie(0, "Toutes"));
		request.setAttribute("categories", categories);
		
		//récupération des filtres article et categorie (connecté ou pas)
		String filtreArticle = "";
		if (request.getAttribute("rechArticle") != null) {
			filtreArticle = (String)request.getAttribute("rechArticle");
			System.out.println("GET - filtreArticle : " + filtreArticle);
		}
		int filtreCateg = 0;
		if (request.getAttribute("categChoisie") != null) {
			filtreCateg = (int)request.getAttribute("categChoisie");
			System.out.println("GET - filtreCateg : " + filtreCateg);
		}
		
		List<ArticleVendu> encheres = null;
		if (user == null) { // non connecté
			//lister toutes les articles en cours de vente (avec filtres éventuels)
			encheres = ArticleVenduManager.getInstance().selectArticlesAll(filtreArticle, filtreCateg);
		}
		else {	//connecté
			//lister toutes les enchères en cours
			boolean encheresOuvertes = false;  
			boolean mesEncheresEnCours = false;
			boolean mesEncheresGagnees = false;
			
			if (request.getAttribute("encheresOuvertes") != null) {		
				encheresOuvertes = (boolean)request.getAttribute("encheresOuvertes");
				System.out.println("GET - encheresOuvertes : " + encheresOuvertes
						           + ", " + user.getNoUtilisateur()
	                               + ", " + filtreArticle + ", " + filtreCateg);								
			}
			if (encheresOuvertes) {
				encheres = ArticleVenduManager.getInstance().selectArticlesAll(filtreArticle, filtreCateg);
			}
			
			//lister toutes les enchères en cours de l'utilisateur connecté
			if (request.getAttribute("mesEncheresEnCours") != null) {		
				mesEncheresEnCours = (boolean)request.getAttribute("mesEncheresEnCours");
				System.out.println("GET - mesEncheresEnCours : " + mesEncheresEnCours 
						            + ", " + user.getNoUtilisateur()
						            + ", " + filtreArticle + ", " + filtreCateg);								
			}
			if (mesEncheresEnCours) {
				encheres = ArticleVenduManager.getInstance().selectEncheresEnCours(user.getNoUtilisateur(), filtreArticle, filtreCateg);		
			}	
			
			//lister toutes les enchères terminées et gagnée de l'utilisateur connecté
			if (request.getAttribute("mesEncheresGagnees") != null) {		
				mesEncheresGagnees = (boolean)request.getAttribute("mesEncheresGagnees");
				System.out.println("GET - mesEncheresGagnees : " + mesEncheresGagnees
			            + ", " + user.getNoUtilisateur()
			            + ", " + filtreArticle + ", " + filtreCateg);									
			}
			if (mesEncheresGagnees) {
				encheres = ArticleVenduManager.getInstance().selectEncheresGagnees(user.getNoUtilisateur(), filtreArticle, filtreCateg);		
			}				
		}
		
		request.setAttribute("encheres", encheres);
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/accueilUtilisateur.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("POST - rechArticle : " + request.getParameter("rechArticle"));
		String articleSaisi = ""; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("rechArticle") != null) {
			articleSaisi = request.getParameter("rechArticle");
		}
		//System.out.println("POST - categSaisie : " + request.getParameter("categChoisie"));
		int categSaisie = 0; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("categChoisie") != null) {
			categSaisie = Integer.parseInt(request.getParameter("categChoisie"));
		}	
		//System.out.println("POST - achatsVentes : " + request.getParameter("achatsVentes"));
		String achatsVentes = "achats"; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("achatsVentes") != null) {			
			achatsVentes = request.getParameter("achatsVentes");
		} 		
		
		//System.out.println("POST - mesEncheresEnCours : " + request.getParameter("mesEncheresEnCours"));
		boolean mesEncheresEnCours = false;
		if (request.getParameter("mesEncheresEnCours") != null) {
			mesEncheresEnCours = (request.getParameter("mesEncheresEnCours").equals("mesEncheresEnCours") ? true : false);			
		}
		//System.out.println("POST - mesEncheresGagnees : " + request.getParameter("mesEncheresGagnees"));
		boolean mesEncheresGagnees = false;
		if (request.getParameter("mesEncheresGagnees") != null) {
			mesEncheresGagnees = (request.getParameter("mesEncheresGagnees").equals("mesEncheresGagnees") ? true : false);			
		}
		//System.out.println("POST - encheresOuvertes : " + request.getParameter("encheresOuvertes"));
		boolean encheresOuvertes = false; // valeur par défaut en cas de 1er appel après la connexion
		if (request.getParameter("encheresOuvertes") != null) {
			encheresOuvertes = (request.getParameter("encheresOuvertes").equals("encheresOuvertes") ? true : false);			
		} 
		
		if (achatsVentes.equals("achats") && !mesEncheresEnCours && !mesEncheresGagnees && !encheresOuvertes) {
			encheresOuvertes = true;
		}
		
		//System.out.println("POST ATTR- articleSaisi : " + articleSaisi);
		request.setAttribute("rechArticle", articleSaisi);
		//System.out.println("POST ATTR- categSaisie : " + categSaisie);
		request.setAttribute("categChoisie", categSaisie);
		
		//System.out.println("POST ATTR- achatsVentes : "+ achatsVentes);
		request.setAttribute("achatsVentes", achatsVentes);
		//System.out.println("POST ATTR- encheresOuvertes : " + encheresOuvertes);
		request.setAttribute("encheresOuvertes", encheresOuvertes);
		//System.out.println("POST ATTR- mesEncheresEnCours : " + mesEncheresEnCours);
		request.setAttribute("mesEncheresEnCours", mesEncheresEnCours);
		//System.out.println("POST ATTR- mesEncheresGagnees : " + mesEncheresGagnees);
		request.setAttribute("mesEncheresGagnees", mesEncheresGagnees);
		
		doGet(request, response);
	}

}
