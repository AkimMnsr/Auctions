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
		<c:when test="${!empty sessionScope.utilisateur }">
			<%@include file="/WEB-INF/INCLUDE/headerUtilisateur.jsp" %>
		</c:when>	
		<c:otherwise>
			<%@include file="/WEB-INF/INCLUDE/headerVisiteur.jsp" %>
		</c:otherwise>
	</c:choose>	
		
	<h2>Liste des enchères</h2>
	
	<h3>Filtres :</h3>
	<form method="POST" action="${pageContext.request.contextPath }/WelcomePageUser">
		<div>
			<c:choose>
		    <c:when test="${empty requestScope.rechArticle }" >
		    	<input type="search" id="recherche" name="rechArticle" 
				       placeholder="Le nom de l'article contient..." size="50">
		    </c:when>
		    <c:otherwise>
				<input type="search" id="recherche" name="rechArticle" 
				       size="50" value="${requestScope.rechArticle}">
			</c:otherwise>
			</c:choose>
		</div>
		<div>			
			<label id="categorie">Catégorie : </label>
			<select id="categorie" name="categChoisie">							
				<c:if test="${!empty requestScope.categories }">
					<c:forEach var="c" items="${requestScope.categories }">
						<c:choose>
		    			<c:when test="${!empty requestScope.categChoisie && c.noCategorie == requestScope.categChoisie }">							
							<option value="${c.noCategorie }" selected>
						</c:when>	
						<c:otherwise>
							<option value="${c.noCategorie }">
						</c:otherwise>    	
						</c:choose>				
						${c.libelle }</option> 
					</c:forEach>
				</c:if>	
			</select>	
		</div>
		<!-- Gestion des cases à cocher Ventes/Achats si utilisateur connecté -->
		<c:if test="${!empty sessionScope.utilisateur}">
		<div>
		    <c:choose>
		    <c:when test="${empty requestScope.achatsVentes || (!empty requestScope.achatsVentes && requestScope.achatsVentes == 'achats')}">
				<input type="radio" name ="achatsVentes" id="achats" value="achats" checked>
			</c:when>
			<c:otherwise>
				<input type="radio" name ="achatsVentes" id="achats" value="achats">
			</c:otherwise>    
			</c:choose>    
				<label for="achats">Achats</label>
				<div>
					<c:choose>
					<c:when test="${empty requestScope.achatsVentes || (! empty requestScope.encheresOuvertes && requestScope.encheresOuvertes == true) }">
						<input type="checkbox" name ="encheresOuvertes" id="encheresOuvertes" value="encheresOuvertes" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="encheresOuvertes" id="encheresOuvertes" value="encheresOuvertes">
					</c:otherwise>
					</c:choose>	
							<label for="encheresOuvertes">enchères ouvertes</label>
					<c:choose>		
					<c:when test="${!empty requestScope.mesEncheresEnCours && requestScope.mesEncheresEnCours == true }">
						<input type="checkbox" name ="mesEncheresEnCours" id="mesEncheresEnCours" value="mesEncheresEnCours" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="mesEncheresEnCours" id="mesEncheresEnCours" value="mesEncheresEnCours">
					</c:otherwise>		
					</c:choose>
							<label for="mesEncheresEnCours">mes enchères en cours</label>	
					<c:choose>		
					<c:when	test="${!empty requestScope.mesEncheresGagnees && requestScope.mesEncheresGagnees == true }">					
						<input type="checkbox" name ="mesEncheresGagnees" id="mesEncheresGagnees" value="mesEncheresGagnees" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="mesEncheresGagnees" id="mesEncheresGagnees" value="mesEncheresGagnees">
					</c:otherwise>		
					</c:choose>
							<label for="mesEncheresGagnees">mes enchères remportées</label>	
				</div>	
		</div>	
		<div>
			<input type="radio" name ="achats_ventes" id="ventes" value="ventes">
				
				
				<label for="ventes">Mes Ventes</label>
				<div>
					<input type="checkbox" name ="ventesEnCours" id="ventesEnCours" value="ventesEnCours">
						<label for="ventesEnCours">ventes en cours</label>
					<input type="checkbox" name ="ventesFutures" id="ventesFutures" value="ventesFutures">
						<label for="ventesFutures">ventes non débutées</label>					
					<input type="checkbox" name ="ventesTerminees" id="ventesTerminees" value="ventesTerminees">
						<label for="ventesTerminees">ventes terminées</label>	
				</div>	
		</div>
		</c:if>					
		<div>
			<input type="submit" value="Rechercher">					
		</div>
	</form>	
	
	<c:forEach var="e" items="${requestScope.encheres }">
		<div>
			<strong>${e.nomArticle }</strong>
			<br>
			Prix : ${e.prixVente } points
			<br>
			Fin de l'enchère : ${e.dateFinEncheres }
			<br>
			Vendeur : <a href="${pageContext.request.contextPath }/ProfilOtherUser?idUser=${e.proprietaire.noUtilisateur }">
						${e.proprietaire.pseudo }</a>
			
		</div>	
	</c:forEach>
	
	
	
</body>
</html>