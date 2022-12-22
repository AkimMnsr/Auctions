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
import fr.eni.javaee.auctions.dal.UtilisateurDAO;


@WebServlet("/ProfilModification")
public class ServletModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/modifierProfil.jsp");
		rd.forward(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();	
		Utilisateur userMdp = (Utilisateur) session.getAttribute("utilisateur");
		String mdpSession = userMdp.getMotDePasse();
		
		int idSession = userMdp.getNoUtilisateur();
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");			
		String ancienMdp = request.getParameter("mdp");
		String nouveauMdp = request.getParameter("nouveauMdp");
		String confirmationMDP = request.getParameter("confirmationMDP");
		
		
		Utilisateur utilisateurModif = new Utilisateur (idSession, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, nouveauMdp);
		System.out.println(utilisateurModif);
		try {
		UtilisateurManager.getInstance().modifier(utilisateurModif,mdpSession, ancienMdp, nouveauMdp, confirmationMDP);
		RequestDispatcher rd = request.getRequestDispatcher("/ProfilUser");
		rd.forward(request, response);
			
		} catch (BusinessException e) {			
			e.printStackTrace();
			request.setAttribute("listeCodeErreur", e.getListeCodesErreur());	
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/modifierProfil.jsp");
			rd.forward(request, response);
		}
		
		
		
		
		
	}

}
