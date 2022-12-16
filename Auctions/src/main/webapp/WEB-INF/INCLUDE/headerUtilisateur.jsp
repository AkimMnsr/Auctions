<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<title>Insert title here</title>
<style type="text/css">
header {
	display: flex;
	justify-content: space-between;
}

.navbar {
	align-items: flex-start;
}

#logoAccueil {
	padding: 8px 16px;
	text-decoration: none;
}
</style>
</head>
<body>
	<header>
		<div class="navbar">
			<a id="logoAccueil" method="get" href="/Auctions/WelcomePageUser"><strong>ENI
					- Auctions</strong></a>
		</div>
		
		<div>
		Bienvenue ${sessionScope.utilisateur.pseudo }
		</div>
		<div class="navbar">
			<nav class="nav">
				<a class="nav-link active" aria-current="page" href="/Auctions/WelcomePageUser">Troc-Enchères</a> 
				<a class="nav-link"	href="/Auctions/NewSale">Vendre un article</a> 
				<a class="nav-link"	href="/Auctions/ProfilUser">Mon profil</a>
					<form method="get" action="ServletDeconnexion">
					<a class="nav-link" method="get" href="/Auctions/ServletDeconnexion">Déconnexion</a>
					</form>
			</nav>
		</div>

	</header>

</body>
</html>