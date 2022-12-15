<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<title>ENI-Enchères : Welcome Page</title>
<style type="text/css">
body {
	text-align: center;
}

</style>
</head>
<body>
	<%--Include de la balise Header--%>
	<c:choose>
		<c:when test="${!empty sessionScope.user }">
			<%@include file="/WEB-INF/INCLUDE/headerUtilisateur.jsp" %>
			<br>
			${sessionScope.user.pseudo }
		</c:when>	
		<c:otherwise>
			<%@include file="/WEB-INF/INCLUDE/headerVisiteur.jsp" %>
		</c:otherwise>
	</c:choose>	
		
	<h2>Liste des enchères</h2>
	
	<h3>Filtres :</h3>
	<form method="POST" action="${pageContext.request.contextPath }/WelcomePageUser">
		<div>
			<input type="search" id="recherche" name="rechArticle" 
				   placeholder="Le nom de l'article contient..." size="50">
		</div>
		<div>			
			<label id="categorie">Catégorie : </label>
			<select id="categorie" name="categChoisie">
				<option value=0>Toutes</option> 
				<c:if test="${!empty requestScope.categories }">
					<c:forEach var="c" items="${requestScope.categories }">
						<option value="${c.noCategorie }">${c.libelle }</option> 
					</c:forEach>
				</c:if>	
			</select>	
		</div>
		<div>
			<input type="submit" value="Rechercher">					
		</div>
	</form>	
	
	<c:if test="${!empty sessionScope.user }">
		<!-- Gestion des cases à cocher Ventes/Achats si utilisateur connecté -->
		<div>
		<input type="radio" name ="achats_ventes" id="achats" value="achats" checked>
			<label for="achats">Achats</label>
			<div>
				<input type="checkbox" name ="achats" id="encheresOuvertes" value="ench_ouvertes" checked>
					<label for="encheresOuvertes">enchères ouvertes</label>
				<input type="checkbox" name ="achats" id="mesEncheresEnCours" value="ench_oen_cours" checked>
					<label for="encheresOuvertes">enchères ouvertes</label>					
			</div>	
		</div>	
		<div>
		<input type="radio" name ="achats_ventes" id="ventes" value="ventes">
			<label for="ventes">Ventes</label>
		</div>
	</c:if>	
		
	
	<c:forEach var="e" items="${requestScope.encheres }">
		<div>
			<strong>${e.nomArticle }</strong>
			<br>
			Prix : ${e.prixVente } €
			<br>
			Fin de l'enchère : ${e.dateFinEncheres }
			<br>
			Vendeur : ${e.proprietaire.pseudo }
		</div>	
	</c:forEach>
	
	
	
</body>
</html>