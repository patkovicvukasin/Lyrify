<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Dodaj novu pesmu</title>
</head>
<body>
	<h1>Dodaj novu pesmu</h1>
	<form action="/processDodajPesmu" method="POST">
		<label for="naziv">Naziv pesme:</label> <input type="text" id="naziv"
			name="naziv" required><br>
		<br> <label for="izvodjac">Izvođač:</label> <input type="text"
			id="izvodjac" name="izvodjac" required><br>
		<br> <label for="zanrId">Zanr:</label> <select name="zanrId"
			id="zanrId" required>
			<c:forEach items="${zanrovi}" var="zanr">
				<option value="${zanr.id}">${zanr.naziv}</option>
			</c:forEach>
		</select><br>
		<br> <label for="tekst">Tekst pesme:</label><br>
		<textarea id="tekst" name="tekst" rows="5" cols="40" required></textarea>
		<br>
		<br>

		<button type="submit">Dodaj pesmu</button>
	</form>
	<br>
	<br>
	<a href="/"><button type="button">Nazad</button></a>
</body>
</html>
