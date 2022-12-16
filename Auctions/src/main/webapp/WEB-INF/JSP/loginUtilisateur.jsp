<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="fr.eni.javaee.auctions.messages.lecteurMessages"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<title>ENI-Enchères : connection</title>
<style type="text/css">
#formuDiv {
	display: flex;
	justify-content: center;
	align-items: center;
}

#formulaire {
	margin: 20px 40px;
}
</style>
</head>
<body>

	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<div id="listeErreur">
		<%
		List<Integer> listeCodesErreur = (List<Integer>) request.getAttribute("listeCodeErreur");
		if (listeCodesErreur != null) {
		%>
		<p style="color: red">Erreur, connexion à votre compte
			imposssible:</p>
		<%
		for (int code : listeCodesErreur) {
		%>
		<p> - <%=lecteurMessages.getMessageErreur(code)%></p>
		<%
		}
		}
		%>
	</div>


	<form id="formulaire" method="post" action="/Auctions/connexion">

		<label for="inputId">Identifiant</label> <input name="identifiant"
			type="text" id="inputId"> <label for="inputPassword3">Password</label>
		<input name="mot_de_passe" type="password" id="inputPassword3">

		<input type="checkbox">Se souvenir de moi


		<button id="boutonSubmit" type="submit">Connexion</button>

	</form>



	<form action="/Auctions/ProfilCreation">
		<button type="submit">Créer un compte</button>
	</form>

</body>
</html>