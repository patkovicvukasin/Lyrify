<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Brisanje teksta</title>
</head>
<body>
	<h1>Brisanje teksta</h1>
	<p>
		Da li ste sigurni da želite da obrišete tekst za pesmu <strong>${tekstPesme.pesma.naziv}</strong>?
	</p>

	<form action="/processBrisanjeTeksta" method="POST">
		<input type="hidden" name="tekstId" value="${tekstPesme.id}">
		<button type="submit">Da</button>
		<a href="/tekst/${tekstPesme.id}"><button type="button">Odustani</button></a>
	</form>

</body>
</html>
