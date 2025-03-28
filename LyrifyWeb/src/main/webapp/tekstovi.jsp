<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Tekstovi za pesmu</title>
</head>
<body>
	<h3>Dostupni tekstovi:</h3>
	<br>
	<c:choose>
		<c:when test="${not empty tekstovi}">
			<ul>
				<c:forEach items="${tekstovi}" var="tekst">
					<li><a href="/tekst/${tekst.id}">${tekst.pesma.naziv} -
							${tekst.pesma.izvodjac} (${tekst.korisnik.korisnickoIme})</a> <c:choose>
							<c:when test="${not empty tekst.prosecnaOcena}">
			                    ${tekst.prosecnaOcena}
			                </c:when>
							<c:otherwise>
			                   -
			                </c:otherwise>
						</c:choose> <c:if test="${tekst.verifikovan}">
			                ✔
			            </c:if><br>
					<br></li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p>Nema dostupnih tekstova za ovu pesmu.</p>
		</c:otherwise>
	</c:choose>

	<a href="/tekstovi/${pesmaId}?sortiraj=${!trenutnoSortirano}">
		<button>
			<c:choose>
				<c:when test="${trenutnoSortirano}">
					&#9660;
                </c:when>
				<c:otherwise>
                    &#9660;                                
                </c:otherwise>
			</c:choose>
		</button>
	</a>
	<br>
	<br>
	<a href="/"><button type="button">Nazad</button></a>
</body>
</html>
