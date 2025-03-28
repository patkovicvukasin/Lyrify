<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Verifikacija teksta</title>
</head>
<body>
	<h1>Verifikacija teksta</h1>
	<p>
		Da li ste sigurni da želite da verifikujete tekst za pesmu: <strong>"${tekstPesme.pesma.naziv}"
		</strong>od korisnika <strong>${tekstPesme.korisnik.korisnickoIme}</strong>?
	</p>
	<form action="/processVerifikacija" method="POST">
		<input type="hidden" name="tekstId" value="${tekstPesme.id}">
		<button type="submit">Da</button>
		<a href="/tekst/${tekstPesme.id}"><button type="button">Poništi</button></a>
	</form>
</body>
</html>
