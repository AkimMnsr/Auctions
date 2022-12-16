<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="fr.eni.javaee.auctions.messages.lecteurMessages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Votre profil</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
body {
	text-align: center;
}

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




	<ul>
		<li><strong>Pseudo : </strong>${sessionScope.utilisateur.pseudo}</li>
		<li><strong>nom: </strong>${sessionScope.utilisateur.nom}</li>
		<li><strong>prenom:</strong> ${sessionScope.utilisateur.prenom}</li>
		<li><strong>email: </strong>${sessionScope.utilisateur.email}</li>
		<li><strong>Telephone: </strong>${sessionScope.utilisateur.telephone}
		</li>
		<li><strong>rue : </strong>${sessionScope.utilisateur.rue}</li>
		<li><strong>code postal : </strong>${sessionScope.utilisateur.codePostal}</li>
		<li><strong>Ville : </strong>${sessionScope.utilisateur.ville}</li>
	</ul>

	<form action="${pageContext.request.contextPath }/ProfilModification">
		<button>Modifier</button>
	</form>


</body>
</html>