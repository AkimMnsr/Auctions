package fr.eni.javaee.auctions.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.EnchereManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;


@WebServlet("/Outbid")  /**ca veut dire Enchérir en anglais**/
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		Utilisateur user = null;
		if (session != null) {
			user = (Utilisateur)session.getAttribute("utilisateur");
		}
		
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		//System.out.println("[MB]Encherir-GET : idArticle = " + idArticle);
				
		ArticleVendu article = ArticleVenduManager.getInstance().selectById(idArticle);
		Enchere meilleureEnchere = null;
		try {
			meilleureEnchere = EnchereManager.getInstance().selectMeilleureEnchere(idArticle, article.getPrixVente());
		} catch (BusinessException be) {
			System.out.println("[MB]Encherir-GET : listeErreurs = " + be.getListeCodesErreur());
			request.setAttribute("erreurs", be.getListeCodesErreur());
		}
		if (meilleureEnchere == null) {
			meilleureEnchere = new Enchere(0,LocalDateTime.now(), 0, null, article);
			/*int noEnchere, LocalDateTime dateEnchere, int montantEnchere, 
				   Utilisateur acheteur, ArticleVendu article*/
		} 
		request.setAttribute("enchere", meilleureEnchere);

		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/encherir.jsp");
		rd.forward(request, response);
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idArticle = 0;
		if (request.getParameter("idArticle") != null) {
		    idArticle = Integer.parseInt(request.getParameter("idArticle"));
			//System.out.println("[MB]Encherir-POST : idArticle = " + idArticle);
		}
		int proposition = 0;
		if (request.getParameter("proposition") != null) {
			proposition = Integer.parseInt(request.getParameter("proposition"));
			//System.out.println("[MB]Encherir-POST : proposition = " + proposition);
		}
		int idAcheteurPrec = 0;
		if (request.getParameter("idAcheteurPrec") != null && !request.getParameter("idAcheteurPrec").isBlank()) {			
			idAcheteurPrec = Integer.parseInt(request.getParameter("idAcheteurPrec"));
			//System.out.println("[MB]Encherir-POST : idAcheteurPrec = " + idAcheteurPrec);
		}
		
		HttpSession session = request.getSession(false);
		Utilisateur user = null;
		if (session != null) {
			user = (Utilisateur)session.getAttribute("utilisateur");
		}
		
		try {
			EnchereManager.getInstance().insertNouvelleEnchere(user.getNoUtilisateur(), idArticle, proposition, idAcheteurPrec);
			
			
			response.sendRedirect(request.getContextPath()+ "/WelcomePageUser");
		
		} catch (BusinessException be) {
			System.out.println("[MB]Encherir-POST : listeErreurs = " + be.getListeCodesErreur());
			request.setAttribute("erreurs", be.getListeCodesErreur());
			
			doGet(request, response);
		}
		
		
	}

}
