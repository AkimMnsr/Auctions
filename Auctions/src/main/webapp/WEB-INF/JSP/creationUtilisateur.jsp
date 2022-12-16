<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.javaee.auctions.messages.lecteurMessages"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Création de profil</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<style type="text/css">
#spanH1 {
	display: flex;
	justify-content: center;
	margin: 15px 0px 15px;
}
</style>
</head>
<body>

	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<span id="spanH1">
		<h1>Mon profil</h1>
	</span>
	<div id="listeErreur">
		<%
		List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodeErreur");
		if (listeCodesErreur != null) {
		%>
		<p style="color: red">Erreur, La création de profil est impossible
			pour l'une de ces raisons :</p>
		<%
		for (int code : listeCodesErreur) {
		%>
		<p><%=lecteurMessages.getMessageErreur(code)%></p>
		<%
		}
		}
		%>
	</div>
	<div id="generalDiv">
		<form method="post" action="/Auctions/ProfilCreation">
			<input name="pseudo" type="text" placeholder="Votre pseudo">
			<input name="nom" type="text" placeholder="Nom"> 
			<input name="prenom" type="text" placeholder="Prenom"> 
			<input name="email" type="email" placeholder="Email">
			<input name="telephone" type="number" placeholder="Télèphone"> 
			<input name="rue" type="text" placeholder="Rue">			
			<input name="codePostal" type="number" placeholder="Code Postal"> 
			<input name="ville" type="text" placeholder="Ville"> 
			<input name="mdp" type="password" placeholder="Password"> 
			<input name="mdpConfirmation" type="password" placeholder="Password">
			<button type="submit">Créer</button>
		</form>

		<form method="get" action="/Auctions/WelcomePageUser">
			<input type="submit" value="annuler">
		</form>
	</div>




</body>
</html>