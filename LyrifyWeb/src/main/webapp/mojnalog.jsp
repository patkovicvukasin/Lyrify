<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Moj nalog</title>
</head>
<body>
	<h1>Moj nalog</h1>
	<p>Korisničko ime: ${korisnik.korisnickoIme}</p>
	<p>Email: ${korisnik.email}</p>
	<p>Uloga: ${korisnik.uloga}</p>
	<p>Datum registracije: ${korisnik.datumRegistracije}</p>

	<p>
		<strong>Moji tekstovi:</strong>
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
		<p>
			Trenutno nemate objavljenih tekstova.<br>
			<br>
		</p>
	</c:if>

	<p>
		<strong>Omiljeni tekstovi:</strong>
	</p>
	<c:if test="${not empty omiljeniTekstovi}">
		<ul>
			<c:forEach items="${omiljeniTekstovi}" var="fav">
				<li><a href="/tekst/${fav.tekst.id}">
						${fav.tekst.pesma.naziv} - ${fav.tekst.pesma.izvodjac} </a></li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty omiljeniTekstovi}">
		<p>
			Trenutno nemate omiljenih tekstova.<br>
			<br>
		</p>
	</c:if>
	
	
	

	<a href="/potvrda"><button type="button">Izloguj se</button></a>
	<br>
	<br>
	<a href="/"><button type="button">Nazad</button></a>
	
	<c:if test="${isAdmin}">
	    <a href="/admin/report/izvestaji">
	        <button type="button">Izveštaji</button>
	    </a>
	</c:if>
	
	
</body>
</html>
