package fr.eni.javaee.auctions.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;



@WebServlet("/ProfilCreation")
public class ServletCreationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/creationUtilisateur.jsp");
		rd.forward(request, response);
		}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String motDePasse = request.getParameter("mdp");
		int credit = 0;
		boolean administrateur = false;
		String validationMDP = request.getParameter("mdpConfirmation");
		
		try {
			Utilisateur newUser = UtilisateurManager.getInstance().insert(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse,credit,administrateur, validationMDP);
		} catch (BusinessException e) {
			request.setAttribute("listeCodeErreur", e.getListeCodesErreur());
		}
		
		
		doGet(request, response);
	}

}
