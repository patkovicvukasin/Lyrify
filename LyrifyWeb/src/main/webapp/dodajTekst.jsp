<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dodavanje teksta pesme</title>
    <script>
        // Ova funkcija na promenu žanra automatski šalje formu
        function onGenreChange() {
            document.getElementById("genreForm").submit();
        }
    </script>
</head>
<body>
    <h1>Dodavanje teksta pesme</h1>

    <!-- (1) Ako je admin, mogućnost da doda novi žanr -->
    <c:if test="${isAdmin}">
        <section style="border: 1px solid #ccc; padding:10px; margin-bottom:20px;">
            <h3>Dodaj novi žanr (samo za admina)</h3>
            <form action="/processDodajZanr" method="POST">
                <label for="zanrNaziv">Naziv žanra:</label><br>
                <input type="text" id="zanrNaziv" name="zanrNaziv" required>
                <button type="submit">Dodaj žanr</button>
                
                <c:if test="${param.error eq 'ZanrExists'}">
					<p style="color:red;">
						Zanr koji pokusavate da unesete vec postoji.
					</p>
				</c:if>
				
            </form>
        </section>
    </c:if>

    <!-- (2) Forma za izbor žanra -->
    <section style="border: 1px solid #ccc; padding:10px; margin-bottom:20px;">
        <h3>Izaberi žanr </h3>
        
        <form id="genreForm" action="/dodajTekst" method="GET">
            <label for="zanrId">Žanr:</label>
            <select name="zanrId" id="zanrId" onchange="onGenreChange()">
                <option value="">-- Izaberi žanr --</option>
                <c:forEach var="zanr" items="${zanrovi}">
                    <option value="${zanr.id}"
                        <c:if test="${zanr.id == param.zanrId}">selected</c:if>>
                        ${zanr.naziv}
                    </option>
                </c:forEach>
            </select>
        </form>
    </section>

    <!-- (3) Dodaj tekst za postojeću pesmu: prikazuje se tek kad je izabran žanr -->
    <c:if test="${not empty param.zanrId}">
        <section style="border: 1px solid #ccc; padding:10px; margin-bottom:20px;">
            <h3>Dodaj tekst za pesmu iz izabranog žanra</h3>

            <!-- Ako postoji lista pesama u ovom žanru -->
            <c:choose>
                <c:when test="${not empty pesme}">
                    <!-- Forma za dodavanje teksta -->
                    <form action="/processDodajTekst" method="POST">
                        <!-- Možeš proslediti i zanrId, ako ti treba u daljem procesu -->
                        <input type="hidden" name="zanrId" value="${param.zanrId}" />

                        <label for="pesmaId">Postojeće pesme:</label>
                        <select name="pesmaId" id="pesmaId" required>
                            
                            <c:forEach var="p" items="${pesme}">
                                <option value="${p.id}">${p.naziv} - ${p.izvodjac}</option>
                            </c:forEach>
                        </select>
                        
                        <br><br>
                        <label for="tekst">Unesi tekst:</label><br>
                        <textarea id="tekst" name="tekst" rows="5" cols="50" required></textarea>
                        
                        <br><br>
                        <button type="submit">Sačuvaj tekst</button>
                    </form>
                </c:when>
                
                <c:otherwise>
                    <!-- Nema pesama u izabranom žanru -->
                    <p>U ovom žanru trenutno nema nijedne pesme.</p>
                </c:otherwise>
            </c:choose>
        </section>
    </c:if>

    <!-- (4) Veza za dodavanje nove pesme (posebna stranica) -->
    <section style="border: 1px solid #ccc; padding:10px; margin-bottom:20px;">
        <h3>Nema pesme za koju želiš da dodaš tekst?</h3>
        <p>
            Za dodavanje nove pesme, klikni na dugme ispod:
        </p>
        <a href="/dodajPesmu">
            <button type="button">Dodaj novu pesmu</button>
        </a>
    </section>

    <!-- (5) Nazad na pocetnu -->
    <p>
        <a href="/">
            <button type="button">Nazad na početnu</button>
        </a>
    </p>

</body>
</html>
