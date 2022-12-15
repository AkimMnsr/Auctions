package fr.eni.javaee.auctions.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.Utilisateur;



@WebServlet("/connexion")
public class ServletLoginUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/loginUtilisateur.jsp");
		rd.forward(request, response);
		}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pseudo = request.getParameter("identifiant");
		String mdp = request.getParameter("mot_de_passe");
		
		try {
			Utilisateur utilisateur = UtilisateurManager.getInstance().verifUtilisateur(pseudo, mdp);
			System.out.println(utilisateur);
			if (utilisateur != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("pseudo", pseudo);
				session.setAttribute("utilisateur", utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher("/WelcomePageUser");
				rd.forward(request, response);
			} else {
				
			}
		} catch (SQLException | BusinessException e) {			
			e.printStackTrace();
			request.setAttribute("listeCodeErreur", ((BusinessException) e).getListeCodesErreur());
		}	
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/loginUtilisateur.jsp");
		rd.forward(request, response);
	}

}
