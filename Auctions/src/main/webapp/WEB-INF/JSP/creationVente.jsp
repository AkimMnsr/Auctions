<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Nouvelle Vente</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">

<style type="text/css">
.page {
	text-align: center;
	align-items: flex-start;
	display: flex;
}

.AD {
	
}
</style>
</head>


<body>
	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<div class="page">

		<h2>Nouvelle vente</h2>

	<c:choose>
	<c:when test="${!empty requestScope.article}">
		<form id="formulaireUpdate" method="post" action="/Auctions/UpdateSale">
	</c:when>
	<c:otherwise>
		<form id="formulaireVente" method="post" action="/Auctions/NewSale">
	</c:otherwise>
	</c:choose>
	
			<div class=AD>
				<div>
				<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.nomArticle }">
					<label for="Article">Article:</label> 
					<input type="text"
					id="Article" name="Article" value= "${requestScope.article.nomArticle}">
				</c:when>
				<c:otherwise>
					<label for="Article">Article:</label> 
					<input type="text"
					id="Article" name="Article">
				</c:otherwise>
				</c:choose>		
				</div>
				<br>


				<div>
				<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.description }">
					<label for="Description">Description:</label> 
					<input type="text"
					 id="Description" name="Description" value= "${requestScope.article.description}">
				</c:when>
				<c:otherwise>
					<label for="Description">Description:</label>
					<input type="text"
					 id="Description" name="Description">
				</c:otherwise>
				</c:choose>
				</div>
			</div>
			<br>


			<div>
			<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.categorie }">
				<label id="categorie"> Catégorie: </label> 
				<select name="Categorie"
					id="categorie">
					<option value="">Choisir une catégorie</option>
					<option value="1">Informatique</option>
					<option value="2">Ameublement</option>
					<option value="3">Vêtement</option>
					<option value="4">Sport et Loisirs</option>
				</select>
			</c:when>
			<c:otherwise>
			<label id="categorie"> Catégorie: </label> 
			<select name="Categorie"
					id="categorie">
					<option value="">Choisir une catégorie</option>
					<option value="1">Informatique</option>
					<option value="2">Ameublement</option>
					<option value="3">Vêtement</option>
					<option value="4">Sport et Loisirs</option>
				</select>
			</c:otherwise>
			</c:choose>
			</div>
			<br>

			<div>
				<label for="photos">Photo de l'article:</label>
				 <input type="file"></input>
			</div>
			<br>

			<div>
			<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.miseAPrix}">
				<label for="prix">Mise à prix:</label> 
				<input type="number" step="1"
					value="0" min="1" name="Prix" value="${requestScope.article.miseAPrix}">
				</c:when>
			<c:otherwise>
				<label for="prix">Mise à prix:</label> 
				<input type="number" step="1"
					value="0" min="1" name="Prix">
			</c:otherwise>
			</c:choose>	
			</div>

			<br>

			<div>
			<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.dateDebEncheres}">
				<label for="DebutEnchere">Début de l'enchère:</label> 
				<input type="date" id="DebutEnchere" name="DebutEnchere" value="${requestScope.article.dateDebEncheres}">
				</c:when>
			<c:otherwise>
				<label for="DebutEnchere">Début de l'enchère:</label> 
				<input type="date" id="DebutEnchere" name="DebutEnchere">
			</c:otherwise>
			</c:choose>		
			</div>
			<br>

			<div>
			<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.dateFinEncheres}">
				<label for="FinEnchere">Fin de l'enchère:</label>
				<input type="date"	id="FinEnchere" name="FinEnchere" value="${requestScope.article.dateFinEncheres}">
				</c:when>
			<c:otherwise>
				<label for="FinEnchere">Fin de l'enchère:</label>
				<input type="date"	id="FinEnchere" name="FinEnchere">
			</c:otherwise>
			</c:choose>	
			</div>
			<br>

			<div>
				<div>
				<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.rue}">
					<label for="Rue">Rue:</label>
					<input type="text" id="Rue" name="Rue" value= "${sessionScope.article.rue}"required>
					</c:when>
				<c:otherwise>
					<label for="Rue">Rue:</label>
					<input type="text" id="Rue" name="Rue" value= "${sessionScope.utilisateur.rue}"required>
				</c:otherwise>
				</c:choose>			
				</div>
				<br>


				<div>
				<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.codePostal}">
					<label for="CodePostal">CodePostal:</label>
					<input  type="number" id="CodePostal" name="CodePostal"  value= "${sessionScope.article.codePostal}"required>
					</c:when>
				<c:otherwise>
					<label for="CodePostal">CodePostal:</label>
					<input  type="number" id="CodePostal" name="CodePostal"  value= "${sessionScope.utilisateur.codePostal}"required>
				</c:otherwise>
				</c:choose>
				</div>
				<br>


				<div>
				<c:choose>
				<c:when test="${!empty requestScope.article && !empty requestScope.article.ville}">
					<label for="Ville">Ville:</label>
					<input type="text" id="Ville" name="Ville" value= "${sessionScope.article.ville}"required>
					</c:when>
				<c:otherwise>
					<label for="Ville">Ville:</label>
					<input type="text" id="Ville" name="Ville" value= "${sessionScope.utilisateur.ville}"required>
				</c:otherwise>
				</c:choose>
				</div>
			</div>
			<br>


			<div>
			<c:if test="${!empty requestScope.article && !empty requestScope.article.noArticle}">
				<input type="number" id="idArticle" hidden ="hidden" name="idArticle" value="${requestScope.article.noArticle}">
			</c:if>
			</div>

			<c:choose>
			<c:when test="${!empty requestScope.article}">
			<button id="boutonModifier" type="submit">Modifier</button>
			</c:when>
			<c:otherwise>
			<button id="boutonEnregistrer" type="submit">Enregistrer</button>
			</c:otherwise>
			</c:choose>
			
		</form>
		
		
		
		<form action="/Auctions/WelcomePageUser">
		<input id="boutonAnnuler" type="submit" value="Annuler">
		</form>
	</div>

</body>

</html>

