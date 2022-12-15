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
		//Utilisateur user = (Utilisateur)session.getAttribute("user");
		Utilisateur user = new Utilisateur("marieb44", "BERGER", "Marie", "mb@aol.com", 
                "0612345678", "4 rue James Lloyd", "44300", "Nantes", "bidule", 345, false);
		session.setAttribute("user", user);
	
		if (user == null) {
			System.out.println("GET - Pas d'utilisateur connecté");
		} else {
			System.out.println("GET - Utilisateur : " + user.getPseudo());
		}
		
		
		//lister les catégories
		List<Categorie> categories = CategorieManager.getInstance().selectAll();
	/*	List<Categorie> categories = new ArrayList<Categorie>();
		Categorie categ = new Categorie(1, "Informatique");
		categories.add(categ);
		categ = new Categorie(2, "Ameublement");
		categories.add(categ);
		categ = new Categorie(3, "Vêtement");
		categories.add(categ);
		categ = new Categorie(4, "Sport&Loisirs");
		categories.add(categ);
	*/
		
		request.setAttribute("categories", categories);
		
		List<ArticleVendu> encheres = null;
		if (user == null) {
			String filtreArticle = "";
			if (request.getAttribute("rechArticle") != null) {
				filtreArticle = (String)request.getAttribute("rechArticle");
				System.out.println("filtreArticle : " + filtreArticle);
			}
			int filtreCateg = 0;
			if (request.getAttribute("categChoisie") != null) {
				filtreCateg = (int)request.getAttribute("categChoisie");
				System.out.println("filtreCateg : " + filtreCateg);
			}
			
			//lister toutes les enchères en cours (avec filtres éventuels)
			encheres = ArticleVenduManager.getInstance().selectArticlesAll(filtreArticle, filtreCateg);
		}
		else {					
			//lister toutes les enchères en cours de l'utilisateur connecté
			//List<ArticleVendu> encheres = ArticleVenduManager.getInstance().selectEncheres(user.getNoUtilisateur());
			encheres = new ArrayList<ArticleVendu>();
			Categorie categ = new Categorie(2, "Ameublement");
			user = new Utilisateur("marieb44", "BERGER", "Marie", "mb@aol.com", 
					               "0612345678", "4 rue James Lloyd", "44300", "Nantes", "bidule", 345, false);
			ArticleVendu article = new ArticleVendu(1, "BOUCHON Cafetière Nespresso", "Cafetière MAGIMIX à capsules  Nespresso, automatique , 2 tailles de tasses", 
					 				LocalDate.parse("2022-12-27"), LocalDate.parse("2023-03-31"), 45, categ, user);
			encheres.add(article);
			categ = new Categorie(4, "Sport&Loisirs");
			article = new ArticleVendu(2, "BOUCHON Nike AirForceOne blanches", "Tennis blanches taille 41, très peu portées", 
	 				LocalDate.parse("2022-12-30"), LocalDate.parse("2023-04-15"), 65, categ, user);
			encheres.add(article);
		}
		
		request.setAttribute("encheres", encheres);
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/accueilUtilisateur.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String articleSaisi = request.getParameter("rechArticle");
		int categSaisie = Integer.parseInt(request.getParameter("categChoisie"));
		System.out.println("articleSaisi : " + articleSaisi);
		System.out.println("categSaisie : " + categSaisie);
		
		request.setAttribute("rechArticle", articleSaisi);
		request.setAttribute("categChoisie", categSaisie);
		
		doGet(request, response);
	}

}
