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
	
	<c:choose>
	<c:when test="${!empty requestScope.enchere }">
		<div>
			<strong>${requestScope.enchere.nomArticle }</strong>
		</div>
		<div>
			<label for="description">Description : </label>
			<input type="text" id="description" disabled="disabled"
			       value="${requestScope.enchere.nomArticle }">      
		</div>
		<div>
			<label for="categorie">Catégorie : </label>
			<input type="text" id="categorie" disabled="disabled"
		 		   value="${requestScope.enchere.categorie.libelle }">
		</div>
		<div>
			<label for="offre">Meilleure offre :</label>
			<c:choose>
			<c:when test="${!empty requestScope.gagnantVente }">
				<input type="text" id="offre" disabled="disabled"
		               value="${requestScope.enchere.prixVente } points par ${requestScope.gagnantVente.pseudo }">
			</c:when>
			<c:otherwise>
				<input type="text" id="offre" disabled="disabled"
		               value="${requestScope.enchere.prixVente } points">
			</c:otherwise>
			</c:choose>
		</div>
		<div>
			<label for="miseAPrix">Mise à prix : </label>
			<input type="text" id="miseAPrix" disabled="disabled"
		 		   value="${requestScope.enchere.miseAPrix } points">		 
		</div>
		<div>
			<label for="finEnchere">Fin de l'enchère : </label>
			<input type="text" id="finEnchere" disabled="disabled"
		 		   value="${requestScope.enchere.dateFinEncheres }">				
		</div>
		<div>
			<label for="retrait">Retrait : </label>
			<textarea id="retrait" disabled="disabled"> 
		 		 ${requestScope.enchere.lieuRetrait.rue }
		 		 ${requestScope.enchere.lieuRetrait.codePostal } ${requestScope.enchere.lieuRetrait.ville }
		    </textarea>
		</div>
		<div>
			<label for="vendeur">Vendeur : </label>
			<input type="text" id="vendeur" disabled="disabled"
				   value="${requestScope.enchere.proprietaire.pseudo }">
		</div>
	</c:when>	
	<c:otherwise>
		<h3>AUCUNE VENTE A AFFICHER !</h3>
	</c:otherwise>
	</c:choose>
	
	<form method="POST" action="${pageContext.request.contextPath }/Outbid">	      
		<label for="proposition">Ma proposition : </label>	
		<input type="number" id="proposition" name="proposition">
		<input type="submit" value="Enchérir">		
	</form>
	
</body>
</html>