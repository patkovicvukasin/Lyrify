<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Izveštaji - Admin</title>
</head>
<body>
    <h1>Izveštaji</h1>

    <section style="border:1px solid #ccc; padding:10px; margin-bottom:20px;">
        <h3>Spisak korisnika</h3>
        
        <a href="/admin/report/korisnici" target="_blank">
            <button type="button">Otvori PDF korisnika</button>
        </a>
    </section>

    <section style="border:1px solid #ccc; padding:10px; margin-bottom:20px;">
        <h3>Spisak tekstova</h3>

        <form action="/admin/report/tekstovi" method="GET" target="_blank">
            <label for="zanrId">Filtriraj po žanru (opcionalno):</label>
            <select name="zanrId" id="zanrId">
                <option value="">Svi žanrovi</option>
                <c:forEach var="z" items="${zanrovi}">
                    <option value="${z.id}">
                        ${z.naziv}
                    </option>
                </c:forEach>
            </select>
            
            <button type="submit">Prikaži PDF</button>
        </form>
    </section>

    <p>
        <a href="/mojNalog"><button type="button">Nazad na moj nalog</button></a>
        <a href="/"><button type="button">Početna</button></a>
    </p>
</body>
</html>
