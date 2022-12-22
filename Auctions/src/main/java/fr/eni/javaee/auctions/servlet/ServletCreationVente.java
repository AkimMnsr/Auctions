package fr.eni.javaee.auctions.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.bo.Utilisateur;

@WebServlet({"/NewSale",
			 "/UpdateSale"})

public class ServletCreationVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getServletPath().equals("/UpdateSale")) {
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			System.out.println("[MB]CréationVente-GET : idArticle = " + idArticle);
			
			ArticleVendu article = ArticleVenduManager.getInstance().selectById(idArticle);
			System.out.println("[MB]CréationVente-GET article(retrait): " + article.getLieuRetrait());
			request.setAttribute("article", article); 
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/creationVente.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");

		String nomArticle = request.getParameter("Article");
		String description = request.getParameter("Description");
		System.out.println(request.getParameter("Categorie"));
		int categorie = Integer.parseInt(request.getParameter("Categorie"));
		Integer miseAPrix = Integer.parseInt(request.getParameter("Prix"));
		LocalDate dateDebEncheres = LocalDate.parse(request.getParameter("DebutEnchere"));
		LocalDate dateFinEncheres = LocalDate.parse(request.getParameter("FinEnchere"));
		String rue = request.getParameter("Rue");
		String codePostal = request.getParameter("CodePostal");
		String ville = request.getParameter("Ville");
		
		
		if (request.getServletPath().equals("/NewSale")) {
		try {
			ArticleVendu newVente = ArticleVenduManager.getInstance().insertVente(user, nomArticle, description,
					categorie, miseAPrix, dateDebEncheres, dateFinEncheres, rue, codePostal, ville);
			} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		}else {
			try {
			int idArticle = Integer.parseInt(request.getParameter("idArticle"));
			
			 ArticleVenduManager.getInstance().updateVente(user, nomArticle, description,
					categorie, miseAPrix, dateDebEncheres, dateFinEncheres, rue, codePostal, ville, idArticle);
			} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
		
		

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/accueilUtilisateur.jsp");
		rd.forward(request, response);

	}

}
