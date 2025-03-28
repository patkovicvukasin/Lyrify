<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Tekst pesme</title>
</head>
<body>
	<h1>Tekst pesme</h1>

	<c:if test="${not empty tekstPesme}">
		<p>
			<strong>Pesma:</strong> ${tekstPesme.pesma.naziv} -
			${tekstPesme.pesma.izvodjac}
		</p>
		<br>
		<p>
			<strong>Tekst:</strong>
		</p>
		<pre style="font-size: 16px; line-height: 1.5;">${tekstPesme.tekst}</pre>
		<p style="font-size: 18px">
			<strong>Dodao korisnik:</strong>
			<c:choose>
				<c:when
					test="${ulogovaniKorisnik != null && ulogovaniKorisnik.id != tekstPesme.korisnik.id}">
					<a href="/profil/${tekstPesme.korisnik.id}">${tekstPesme.korisnik.korisnickoIme}</a>
				</c:when>
				<c:otherwise>
		            ${tekstPesme.korisnik.korisnickoIme}
		        </c:otherwise>
			</c:choose>
		<p style="font-size: 18px">
			<strong><br>
			<br>Prosečna ocena:</strong>
			<c:choose>
				<c:when test="${prosecnaOcena != null}">
                    ${prosecnaOcena}
                </c:when>
				<c:otherwise>
                    Još uvek nema ocena.
                </c:otherwise>
			</c:choose>
		</p>
		</p>


		<!-- Forma za ocenjivanje -->
		<c:if
			test="${ulogovaniKorisnik != null && ulogovaniKorisnik.id != tekstPesme.korisnik.id}">
			<form action="/oceniTekst" method="POST">
				<input type="hidden" name="tekstId" value="${tekstPesme.id}">
				<label for="ocena">Oceni tekst:</label> <select name="ocena"
					id="ocena">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
				<button type="submit">Oceni</button>
				<br>
				<br>
			</form>
		</c:if>

		<c:if test="${ulogovaniKorisnik != null && !jeAutor}">
			<form
				action="${jeOmiljen ? '/ukloniIzOmiljenih' : '/dodajUOmiljene'}"
				method="POST" style="display: inline;">
				<input type="hidden" name="tekstId" value="${tekstPesme.id}">
				<button type="submit"
					title="${jeOmiljen ? 'Ukloni iz omiljenih' : 'Dodaj u omiljene'}"
					style="font-size:20px; cursor:pointer; background-color: ${jeOmiljen ? 'black' : 'white'}; color: ${jeOmiljen ? 'white' : 'black'};">
					👍</button>
			</form>
			<p style="display: inline; color: green; font-weight: bold;">
				${poruka != null ? poruka : ''}</p>
		</c:if>



		<c:if test="${not empty komentari}">
			<h3>
				<br>
				<br>Komentari:
			</h3>
			<ul>
				<c:forEach items="${komentari}" var="komentar">
					<li><strong>${komentar.korisnik.korisnickoIme}:</strong>
						${komentar.tekstKomentara} <br> <small>${komentar.datumVreme}</small>
						<c:if
							test="${ulogovaniKorisnik != null && ulogovaniKorisnik.id == komentar.korisnik.id}">
							<form action="/obrisiKomentar" method="POST"
								style="display: inline;">
								<input type="hidden" name="komentarId" value="${komentar.id}">
								<button type="submit">Obriši</button>
							</form>
						</c:if></li>
				</c:forEach>
			</ul>
		</c:if>

		<!-- Forma za dodavanje komentara -->
		<c:if
			test="${ulogovaniKorisnik != null && ulogovaniKorisnik.id != tekstPesme.korisnik.id}">
			<h3>
				<br>Dodajte komentar:
			</h3>
			<form action="/dodajKomentar" method="POST">
				<input type="hidden" name="tekstId" value="${tekstPesme.id}">
				<textarea name="tekstKomentara" rows="3" cols="50" required></textarea>
				<br>
				<button type="submit">Postavi komentar</button>
			</form>
		</c:if>

		<c:if test="${isAdmin}">
			<c:choose>
				<c:when test="${tekstPesme.verifikovan}">
					<p>
						<strong><br>
						<br>Tekst je verifikovan</strong> ✔️
					</p>
				</c:when>
				<c:otherwise>
					<br>
					<br>
					<form action="/verifikacija" method="GET">
						<input type="hidden" name="tekstId" value="${tekstPesme.id}">
						<button type="submit">Verifikuj tekst</button>
					</form>
				</c:otherwise>
			</c:choose>
		</c:if>
		<br>

		<c:if
			test="${ulogovaniKorisnik != null && ulogovaniKorisnik.id == tekstPesme.korisnik.id}">
			<form action="/brisanjeTeksta" method="GET">
				<input type="hidden" name="tekstId" value="${tekstPesme.id}">
				<button type="submit">Obriši tekst</button>
			</form>
		</c:if>
		<br>
	</c:if>

	<c:if test="${empty tekstPesme}">
		<p>Tekst pesme nije pronađen.</p>
	</c:if>

	<a href="/"><button type="button">Početna</button></a>
	<br>
	<br>
	<br>
</body>
</html>

