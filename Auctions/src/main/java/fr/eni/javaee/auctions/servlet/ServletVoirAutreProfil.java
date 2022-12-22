package fr.eni.javaee.auctions.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.be.BusinessException;
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.Utilisateur;


@WebServlet("/ProfilOtherUser")
public class ServletVoirAutreProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int idUser = Integer.parseInt(request.getParameter("idUser"));
		
		try {
			Utilisateur userToShow = UtilisateurManager.getInstance().profilUtilisateur (idUser);
			request.setAttribute("pseudo", userToShow.getPseudo());
			request.setAttribute("nom", userToShow.getNom());
			request.setAttribute("prenom", userToShow.getPrenom());
			request.setAttribute("email", userToShow.getEmail());
			request.setAttribute("telephone", userToShow.getTelephone());
			request.setAttribute("rue", userToShow.getRue());
			request.setAttribute("codePostal", userToShow.getCodePostal());
			request.setAttribute("ville", userToShow.getVille());
		
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/voirAutreProfil.jsp");
		rd.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	
	}

}
