<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

#BtnCreate {
	margin: 50px 10px;
	display: flex;
	justify-content: center;
}

#boutonSubmit {
	box-shadow: 2px 2px 2px black;
	height: 60px;
	margin: 10px;
}

#gridCheck1 {
	margin: 10px;
}

#boutonCreate {
	box-shadow: 10px 5px 5px black;
	height: 70px;
	width: 250px;
	height: 70px;
}

#cnxMdp {
	display: flex;
	flex-direction: row-reverse;
	align-items: center;
}
</style>
</head>
<body>

	<%--Include de la balise Header--%>
	<%@include file="/WEB-INF/INCLUDE/header.jsp"%>

	<div id="formuDiv">
		<form id="formulaire" method="post" action="/Auctions/connexion">
			<div class="rowmb-3">
				<label for="inputId" class="col-sm-2 col-form-label">Identifiant</label>
				<div class="col-sm-10">
					<input name="identifiant" type="text" class="form-control"
						id="inputId">
				</div>
			</div>
			<div class="rowmb-3">
				<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
				<div class="col-sm-10">
					<input name="mot_de_passe" type="password" class="form-control"
						id="inputPassword3">
				</div>
			</div>
			<div id="cnxMdp">
				<div>
					<div class="col-sm-10 offset-sm-2">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="gridCheck">Se
							souvenir de moi</input>

						</div>
					</div>
				</div>
				<button id="boutonSubmit" type="submit" class="btn btn-primary">Connexion</button>
			</div>
		</form>
	</div>


	<div id="BtnCreate">
		<form action="/Auctions/ProfilCreation">
			<button id="boutonCreate" type="submit" class="btn btn-primary">Créer
				un compte</button>
		</form>
	</div>
</body>
</html>