<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Pesma već postoji</title>
</head>
<body>
	<h1>Pesma već postoji</h1>
	<p>
		Pesma <strong>${naziv}</strong> izvođača <strong>${izvodjac}</strong>
		već postoji u bazi podataka.
	</p>
	<a href="/"><button type="button">Početna</button></a>
</body>
</html>
