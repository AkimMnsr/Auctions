<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

		<form id="formulaireVente" method="post" action="/Auctions/NewSale">

			<div class=AD>
				<div>
					<label for="Article">Article:</label> <input type="text"
						id="Article" name="Article">
				</div>
				<br>

				<div>
					<label for="Description">Description:</label> <input type="text"
						id="Description" name="Description">
				</div>
			</div>
			<br>

			<div>
				<label id="categorie"> Catégorie: </label> <select name="Categorie"
					id="categorie">
					<option value="">Choisir une catégorie</option>
					<option value="1">Informatique</option>
					<option value="2">Ameublement</option>
					<option value="3">Vêtement</option>
					<option value="4">Sport et Loisirs</option>
				</select>
			</div>
			<br>

			<div>
				<label for="photos">Photo de l'article:</label> <input type="file"></input>
			</div>
			<br>

			<div>
				<label for="prix">Mise à prix:</label> <input type="number" step="1"
					value="0" min="1" name="Prix">
			</div>

			<br>

			<div>
				<label for="DebutEnchere">Début de l'enchère:</label> <input
					type="date" id="DebutEnchere" name="DebutEnchere">
			</div>
			<br>

			<div>
				<label for="FinEnchere">Fin de l'enchère:</label> <input type="date"
					id="FinEnchere" name="FinEnchere">
			</div>
			<br>

			<div>
				<div>
					<label for="Rue">Rue:</label> <input type="text" id="Rue"
						name="Rue" required>
				</div>
				<br>

				<div>
					<label for="CodePostal">CodePostal:</label> <input type="number"
						id="CodePostal" name="CodePostal" required>
				</div>
				<br>

				<div>
					<label for="Ville">Ville:</label> <input type="text" id="Ville"
						name="Ville" required>
				</div>
			</div>
			<br>



			<div>
				<button id="boutonEnregistrer" type="submit">Enregistrer</button>


			</div>


		</form>
		<form action="/Auctions/WelcomePageUser">
			<input id="boutonAnnuler" type="submit" value="Annuler">
		</form>
	</div>

</body>

</html>

