# Lyrify - Web aplikacija za tekstove pesama

Lyrify je full-stack web aplikacija implementirana koristeći **Spring Boot**, **JSP**, **MySQL** i **Spring Security**. Aplikacija predstavlja platformu za deljenje i ocenjivanje tekstova pesama, sa podrškom za uloge korisnika, pretragu, komentare, statistiku i izveštaje.

## Ključne funkcionalnosti

- **Registrovani korisnici** mogu:
  - dodavati tekstove pesama
  - ocenjivati i komentarisati tuđe tekstove
  - dodavati tekstove u omiljene
  - pregledati sopstveni profil

- **Neregistrovani korisnici** mogu:
  - pretraživati tekstove pesama po nazivu ili žanru
  - pregledati dostupne tekstove

- **Administrator**:
  - ima dodatne privilegije za verifikaciju i brisanje tekstova
  - ima pristup statističkim izveštajima (broj pesama po žanru i po danima)

## Sigurnost

Aplikacija koristi **Spring Security (form login)**:
- Pristup određenim rutama je dozvoljen samo registrovanim korisnicima ili adminima
- Korisničke lozinke se čuvaju **BCrypt** hesirane
- Prijavljeni korisnik se čuva u **HTTP sesiji** kao `ulogovaniKorisnik`

## Slanje emailova

- Podržano slanje mejlova pomoću `spring-boot-starter-mail`
- Konfigurisano u `application.properties`
- Planirana primena: slanje PDF izveštaja na email korisnika (npr. o broju pesama po žanru ili novim tekstovima)

## JasperReports

- Izveštaji se generišu pomoću **JasperReports**
- Podržano generisanje **PDF izveštaja** iz baze podataka
- Primer izveštaja: broj pesama po žanru, broj tekstova po danima

## Tehnologije

- **Backend:** Spring Boot, Spring MVC, Spring Security, JasperReports
- **Frontend:** JSP + JSTL
- **Baza:** MySQL
- **Build alat:** Maven

## Struktura projekta

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.demo
│   │   │       ├── controllers
│   │   │       ├── services
│   │   │       ├── repositories
│   │   │       ├── security
│   │   │       └── config
│   │   ├── resources
│   │   │   ├── static
│   │   │   ├── templates
│   │   │   └── jasperreports
│   │   └── webapp
│   │       └── WEB-INF
│   │           └── jsp
├── pom.xml
```

## Pokretanje projekta

1. Kreirati bazu `pesme_db` u MySQL
2. Podesiti `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pesme_db
spring.datasource.username=root
spring.datasource.password=lozinka
```
3. Pokrenuti aplikaciju iz IDE-a ili terminala:
```bash
./mvnw spring-boot:run
```
4. Otvoriti u browseru: `http://localhost:8080`

## Autor

- Ime: [Ovde uneti ime i prezime]
- GitHub: [tvoj GitHub profil]
- Email: [tvoj kontakt mejl]

---
Za više informacija i demonstraciju rada, pogledajte JSP stranice i kontrolere unutar projekta.

---

> Ovaj projekat je urađen kao deo obaveza na fakultetu i demonstrira rad sa Spring Boot-om, bezbednošću, e-mail servisima i generisanjem izveštaja.

