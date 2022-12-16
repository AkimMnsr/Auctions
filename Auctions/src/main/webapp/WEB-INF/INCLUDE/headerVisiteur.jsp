<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>headeur visiteur</title>
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
			<a id="logoAccueil" href="/Auctions/WelcomePageUser"><strong>ENI
					- Auctions</strong></a>
		</div>
		Bienvenue ${sessionScope.pseudo }
		<div class="navbar">
			<nav class="nav">
				<a class="nav-link active" aria-current="page"
					href="/Auctions/connexion">S'identifier / s'enregistrer</a>

			</nav>
		</div>

	</header>

</body>
</html>