<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="logo/ENILogo.png" type="image/x-icon">
<title>ENI-Enchères : Profil d'utilisateur</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<style type="text/css">

h1 {
	text-align : center;
}
</style>
</head>
<body>
	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<h1>Profil du vendeur</h1>

	<ul>
		<li><strong>Pseudo :</strong> ${pseudo}</li>
		<li><strong>Nom :</strong> ${nom }</li>
		<li><strong>Prénom :</strong> ${prenom }</li>
		<li><strong>Email :</strong> ${email }</li>
		<li><strong>Telephone :</strong> ${telephone }</li>
		<li><strong>Rue :</strong> ${rue }</li>
		<li><strong>Code Postal :</strong> ${codePostal }</li>
		<li><strong>Ville :</strong> ${ville }</li>
	</ul>

	<form method="get"
		action="${pageContext.request.contextPath }/WelcomePageUser">
		<input type="submit" value="retour">
	</form>



</body>
</html>