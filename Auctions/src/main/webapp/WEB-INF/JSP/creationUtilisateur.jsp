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

#generalDiv {
	display: flex;
	justify-content: center;
}

#mulptipleInput {
	padding: 15px 15px 15px15px;
	margin: 10px 10px 10px 10opx;
	display: flex;
	flex-wrap: wrap;
	display: flex;
	display: flex
}

.form-floating {
	margin: 10px 100px 10px 100px;
	width: 35%;
}

#boutonCDiv {
	display: flex;
	justify-content: center;
	margin: 35px 35px;
}

#boutonCreate {
	box-shadow: 2px 2px 2px black;
	margin: 10px 10px 10px 10px;
	padding: 20px 50px 20px 50px;
}

#boutonAnnule {
	box-shadow: 2px 2px 2px black;
	margin: 10px 50px 10px 50px;
	padding: 20px 50px 20px 50px;
}

#listeErreur{
display: flex;
justify-content: flex-start;
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
		List<Integer> listeCodesErreur = (List<Integer>) request.getAttribute("listeCodeErreur");
		if (listeCodesErreur != null) {
		%>
		<p style="color: red">Erreur, La création de profil est impossible
			pour l'une de ces raisons :</p>
		<%
		for (int code : listeCodesErreur) {
		%>
		<p><%=code%>
			:
			<%=lecteurMessages.getMessageErreur(code)%></p>
		<%
		}
		}
		%>
	</div>
	<div id="generalDiv">
		<form method="post" action="/Auctions/ProfilCreation">
			<div id="mulptipleInput">

				<div class="form-floating">
					<input name="pseudo" type="text" class="form-control"
						id="floatingPseudo" placeholder="Votre pseudo"> <label
						for="floatingPseudo">Pseudo</label>
				</div>
				<div class="form-floating">
					<input name="nom" type="text" class="form-control" id="floatingNom"
						placeholder="Nom"> <label for="floatingNom">Nom</label>
				</div>
				<div class="form-floating">
					<input name="prenom" type="text" class="form-control"
						id="floatingPrenom" placeholder="Prenom"> <label
						for="floatingPrenom">Prenom</label>
				</div>
				<div class="form-floating">
					<input name="email" type="email" class="form-control"
						id="floatingEmail" placeholder="Email"> <label
						for="floatingEmail">Email</label>
				</div>
				<div class="form-floating">
					<input name="telephone" type="number" class="form-control"
						id="floatingTelephone" placeholder="Télèphone"> <label
						for="floatingTelephone">Telephone</label>
				</div>
				<div class="form-floating">
					<input name="rue" type="text" class="form-control" id="floatingRue"
						placeholder="Rue"> <label for="floatingRue">Rue</label>
				</div>
				<div class="form-floating">
					<input name="codePostal" type="number" class="form-control"
						id="floatingCodePostal" placeholder="Code Postal"> <label
						for="floatingCodePostal">Code Postal</label>
				</div>
				<div class="form-floating">
					<input name="ville" type="text" class="form-control"
						id="floatingVille" placeholder="Ville"> <label
						for="floatingVille">Ville</label>
				</div>
				<div class="form-floating">
					<input name="mdp" type="password" class="form-control"
						id="floatingPassword" placeholder="Password"> <label
						for="floatingPassword">Mot de Passe</label>
				</div>
				<div class="form-floating">
					<input name="mdpConfirmation" type="password" class="form-control"
						id="floatingPassword" placeholder="Password"> <label
						for="floatingPassword">Confirmer le mot de passe</label>
				</div>
			</div>
			<div id="boutonCDiv">
				<button id="boutonCreate" type="submit" class="btn btn-primary">Créer</button>
				<input id="boutonAnnule" type="submit"
					href="/Auctions/WelcomePageVisitor" class="btn btn-primary"
					value="annuler">
			</div>
		</form>


	</div>




</body>
</html>