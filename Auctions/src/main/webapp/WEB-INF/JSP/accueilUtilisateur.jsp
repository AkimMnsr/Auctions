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
<script>

</script>
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
					<c:when test="${empty requestScope.achatsVentes || (!empty requestScope.encheresOuvertes && requestScope.encheresOuvertes == true) }">
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
			<c:choose>
		    <c:when test="${!empty requestScope.achatsVentes && requestScope.achatsVentes == 'ventes' }">
				<input type="radio" name ="achatsVentes" id="ventes" value="ventes" checked>
			</c:when>
			<c:otherwise>
				<input type="radio" name ="achatsVentes" id="ventes" value="ventes">
			</c:otherwise>    
			</c:choose>    								
				<label for="ventes">Mes Ventes</label>
				<div>
					<c:choose>
					<c:when test="${!empty requestScope.achatsVentes
					                && (requestScope.achatsVentes == 'ventes' && requestScope.mesVentesEnCours == true
					                    || (requestScope.mesVentesEnCours == false && requestScope.mesVentesNonDebutees == false 
					                        &&requestScope.mesVentesTerminees == false ) ) }">
						<input type="checkbox" name ="mesVentesEnCours" id="ventesEnCours" value="mesVentesEnCours" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="mesVentesEnCours" id="ventesEnCours" value="mesVentesEnCours">
					</c:otherwise>
					</c:choose>								
						<label for="ventesEnCours">ventes en cours</label>
												
					<c:choose>
					<c:when test="${!empty requestScope.achatsVentes 
					                && requestScope.achatsVentes == 'ventes' && requestScope.mesVentesNonDebutees == true }">
						<input type="checkbox" name ="mesVentesNonDebutees" id="ventesNonDebutees" value="mesVentesNonDebutees" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="mesVentesNonDebutees" id="ventesNonDebutees" value="mesVentesNonDebutees">
					</c:otherwise>
					</c:choose>	
						<label for="ventesNonDebutees">ventes non débutées</label>
																
					<c:choose>
					<c:when test="${!empty requestScope.achatsVentes 
					              && requestScope.achatsVentes == 'ventes' && requestScope.mesVentesTerminees == true }">
						<input type="checkbox" name ="mesVentesTerminees" id="ventesTerminees" value="mesVentesTerminees" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name ="mesVentesTerminees" id="ventesTerminees" value="mesVentesTerminees">
					</c:otherwise>
					</c:choose>	
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
			<c:choose>
			<c:when test="${!empty requestScope.achatsVentes && requestScope.achatsVentes == 'ventes'
							&& !empty sessionScope.utilisateur 
							&& sessionScope.utilisateur.noUtilisateur == e.proprietaire.noUtilisateur
							&& e.etatVente == 0 }">
				<a href="${pageContext.request.contextPath }/NewSale?idArticle=${e.noArticle } ">
						<strong>${e.nomArticle }</strong></a>

			</c:when>
			<c:when test="${ ((!empty requestScope.achatsVentes && requestScope.achatsVentes == 'achats')
								|| empty requestScope.achatsVentes)
			                && !empty sessionScope.utilisateur 
			                && sessionScope.utilisateur.noUtilisateur != e.proprietaire.noUtilisateur
			                && e.etatVente == 1 }">
				<a href="${pageContext.request.contextPath }/Outbid?idArticle=${e.noArticle } ">
						<strong>${e.nomArticle }</strong></a>

			</c:when>
						
			<c:otherwise>
				<strong>${e.nomArticle }</strong>
			</c:otherwise>
			</c:choose>
			<br>
			Prix : ${e.prixVente } points
			<br>
			<em>( Début de l'enchère : ${e.dateDebEncheres } // Etat: ${e.etatVente } )</em>
			<br>
			Fin de l'enchère : ${e.dateFinEncheres }
			<br>
			Vendeur : 
			<c:choose>
			<c:when test="${!empty sessionScope.utilisateur }">
				<a href="${pageContext.request.contextPath }/ProfilOtherUser?idUser=${e.proprietaire.noUtilisateur }">
							${e.proprietaire.pseudo }</a>
			</c:when>
			<c:otherwise>
				${e.proprietaire.pseudo }
			</c:otherwise>
			</c:choose>
		</div>	
	</c:forEach>
	
	
	
</body>
</html>