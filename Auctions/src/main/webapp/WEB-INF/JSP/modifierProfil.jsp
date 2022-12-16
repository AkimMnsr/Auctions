<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Paramètre de profil</title>
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
	
	<div id="generalDiv">
		<form method="post" action="/Auctions/ProfilModification"> 
			<input name="pseudo" type="text" placeholder="Votre pseudo" value="${sessionScope.pseudo }"> 
			<input name="nom" type="text" placeholder="Nom" value="${sessionScope.nom }"> 
			<input name="prenom" type="text" placeholder="Prenom" value="${sessionScope.prenom }">  
			<input name="email" type="email" placeholder="Email"  value="${sessionScope.email }"> 
			<input name="telephone" type="number" placeholder="Télèphone"  value="${sessionScope.telephone }"> 
			<input name="rue" type="text" placeholder="Rue"  value="${sessionScope.rue }">	
			<input name="codePostal" type="number" placeholder="Code Postal"  value="${sessionScope.codePostal }">  
			<input name="ville" type="text" placeholder="Ville"  value="${sessionScope.ville }"> 
			<input name="mdp" type="password" placeholder="Ancien mot de passe"> 			
			<input name="nouveauMdp" type="password" placeholder="nouveau mot de passe" >
			<input name="confirmationMDP" type="paswword" placeholder="confirmer le nouveau mot de passe">
			<button type="submit">Enregistrer</button> 
		</form> 

		<form method="get" action="/Auctions/WelcomePageUser">
			<input type="submit" value="Supression du compte">
		</form>
	</div>

</body>
</html>