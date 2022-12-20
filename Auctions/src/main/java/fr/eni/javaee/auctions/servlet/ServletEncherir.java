package fr.eni.javaee.auctions.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Utilisateur;


@WebServlet("/Outbid")  /**ca veut dire Enchérir en anglais**/
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		//System.out.println("[MB]Encherir-GET : idArticle = " + idArticle);
				
		ArticleVendu enchere = ArticleVenduManager.getInstance().selectEnchereById(idArticle);
		Utilisateur gagnantVente = ArticleVenduManager.getInstance().selectMeilleureEnchereById(idArticle, enchere.getPrixVente());
		request.setAttribute("gagnantVente", gagnantVente);
		request.setAttribute("enchere", enchere);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/encherir.jsp");
		rd.forward(request, response);
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
