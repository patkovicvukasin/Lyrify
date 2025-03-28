<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Profil korisnika</title>
</head>
<body>
	<h1>Profil korisnika: ${korisnikProfil.korisnickoIme}</h1>
	<p>Email: ${korisnikProfil.email}</p>
	<p>Uloga: ${korisnikProfil.uloga}</p>

	<p>
		<strong>Tekstovi korisnika:</strong>
	</p>
	<c:if test="${not empty tekstovi}">
		<ul>
			<c:forEach items="${tekstovi}" var="tekst">
				<li><a href="/tekst/${tekst.id}">${tekst.pesma.naziv} -
						${tekst.pesma.izvodjac}</a></li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty tekstovi}">
		<p>Korisnik još uvek nije dodao tekstove.</p>
	</c:if>

	<a href="/"><button type="button">Nazad</button></a>
</body>
</html>
