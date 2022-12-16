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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
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
		<li><strong>Pseudo : </strong>${sessionScope.pseudo}</li>
		<li><strong>nom: </strong>${sessionScope.nom}</li>
		<li><strong>prenom:</strong> ${sessionScope.prenom}</li>
		<li><strong>email: </strong>${sessionScope.email}</li>
		<li><strong>Telephone: </strong>${sessionScope.telephone}<%if (session.getAttribute("telephone").equals("")) { %>
			<em>Aucun numéro de téléphone enregistré</em>	
		<% } %>
		
		</li>
		<li><strong>rue : </strong>${sessionScope.rue}</li>
		<li><strong>code postal : </strong>${sessionScope.codePostal}</li>
		<li><strong>Ville : </strong>${sessionScope.ville}</li>
	</ul>

	<form action="${pageContext.request.contextPath }/ProfilModification">
		<button>Modifier</button>
	</form>


</body>
</html>