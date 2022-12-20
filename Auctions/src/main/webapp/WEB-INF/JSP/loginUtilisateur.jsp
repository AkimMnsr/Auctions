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
		<p>
			-
			<%=lecteurMessages.getMessageErreur(code)%></p>
		<%
		}
		}
		%>
	</div>


	<form id="formulaire" method="post" action="/Auctions/connexion">
		<label for="inputId">Identifiant</label> <input name="identifiant"
			type="text" id="inputId"> <label for="inputPassword3">Password</label>
		<input name="mot_de_passe" type="password" id="inputPassword3">

		<input type="checkbox">Se souvenir de moi <input type="submit"
			id="boutonSubmit" type="submit" value="Connexion">
	</form>
	<a href="">mot de passe oublié ? </a>
	<form action="/Auctions/ProfilCreation">
		<input type="submit" value="Créer un compte">
	</form>

</body>
</html>