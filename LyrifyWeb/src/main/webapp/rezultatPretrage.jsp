<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Rezultati pretrage</title>
</head>
<body>
	<h1>Rezultati pretrage za: "${pretraga}"</h1>

	<c:choose>
		<c:when test="${not empty pesme}">
			<ul>
				<c:forEach items="${pesme}" var="pesma">
					<li><a href="/tekstPesme/${pesma.id}">${pesma.naziv} -
							${pesma.izvodjac}</a></li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p>Nijedna pesma nije pronađena.</p>
		</c:otherwise>
	</c:choose>
	<a href="/"><button type="button">Nazad</button></a>
</body>
</html>
