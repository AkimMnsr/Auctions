<%@page import="fr.eni.javaee.auctions.messages.LecteurMessagesEnchere"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Enchère</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
</head>
<body>
	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<h2>Détail vente</h2>
	
	<c:if test="${!empty requestScope.erreurs }">
		<c:forEach var="err" items="${requestScope.erreurs }">
		 	<p style="color: red;">ERREUR : ${LecteurMessagesEnchere.getMessageErreur(err) }</p> 
		</c:forEach>
	</c:if>
		
	<c:choose>
	<c:when test="${!empty requestScope.enchere }">
		<div>
			<strong>${requestScope.enchere.article.nomArticle }</strong>
		</div>
		<div>
			<label for="description">Description : </label>
			<input type="text" id="description" disabled="disabled"
			       value="${requestScope.enchere.article.nomArticle }">      
		</div>
		<div>
			<label for="categorie">Catégorie : </label>
			<input type="text" id="categorie" disabled="disabled"
		 		   value="${requestScope.enchere.article.categorie.libelle }">
		</div>
		<div>
			<label for="offre">Meilleure offre :</label>
			<c:choose>
			<c:when test="${!empty requestScope.enchere.acheteur }">
				<input type="text" id="offre" disabled="disabled"
		               value="${requestScope.enchere.article.prixVente } points par ${requestScope.enchere.acheteur.pseudo }">
			</c:when>
			<c:otherwise>
				<input type="text" id="offre" disabled="disabled"
		               value="${requestScope.enchere.article.prixVente } points">
			</c:otherwise>
			</c:choose>
		</div>
		<div>
			<label for="miseAPrix">Mise à prix : </label>
			<input type="text" id="miseAPrix" disabled="disabled"
		 		   value="${requestScope.enchere.article.miseAPrix } points">		 
		</div>
		<div>
			<label for="finEnchere">Fin de l'enchère : </label>
			<input type="date" id="finEnchere" disabled="disabled"
		 		   value="${requestScope.enchere.article.dateFinEncheres }">				
		</div>
		<div>
			<label for="retrait">Retrait : </label>
			<textarea id="retrait" disabled="disabled">${requestScope.enchere.article.lieuRetrait.rue }
${requestScope.enchere.article.lieuRetrait.codePostal } ${requestScope.enchere.article.lieuRetrait.ville }</textarea>
		</div>
		<div>
			<label for="vendeur">Vendeur : </label>
			<input type="text" id="vendeur" disabled="disabled"
				   value="${requestScope.enchere.article.proprietaire.pseudo }">
		</div>
<!--  		
		<div>
			<label for="credit">Mon crédit : </label>
			<c:choose>
			<c:when test="${!empty sessionScope.utilisateur }">
				<input type="text" id="credit" disabled="disabled"
				       value="${sessionScope.utilisateur.credit } points">
			</c:when>
			<c:otherwise>
				  <input type="text" id="credit" disabled="disabled"
				       value="Non renseigné">     
			</c:otherwise>
			</c:choose>
		</div>
-->		
	</c:when>	
	<c:otherwise>
		<h3>AUCUNE VENTE A AFFICHER !</h3>
	</c:otherwise>
	</c:choose>
	
	<form method="POST" action="${pageContext.request.contextPath }/Outbid?idArticle=${requestScope.enchere.article.noArticle }&idAcheteurPrec=${requestScope.enchere.acheteur.noUtilisateur }">	      
		<label for="proposition">Ma proposition : </label>	
		<input type="number" id="proposition" name="proposition">
		<input type="submit" value="Enchérir">		
	</form>
	
</body>
</html>