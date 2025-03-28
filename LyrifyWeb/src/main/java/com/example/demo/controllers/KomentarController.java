package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.KomentarService;

import jakarta.servlet.http.HttpSession;
import model.Komentar;
import model.Korisnik;

@Controller
public class KomentarController {

	@Autowired
	KomentarService koms;

	@PostMapping("/dodajKomentar")
	public String dodajKomentar(@RequestParam("tekstId") int tekstId,
			@RequestParam("tekstKomentara") String tekstKomentara, HttpSession session) {
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		if (ulogovaniKorisnik == null) {
			return "redirect:/prijava";
		}

		try {
			koms.dodajKomentar(tekstId, ulogovaniKorisnik, tekstKomentara);
		} catch (Exception e) {
			System.err.println("Greška pri dodavanju komentara: " + e.getMessage());
			return "redirect:/error"; // Opcionalno, stranica za greške
		}
		return "redirect:/tekst/" + tekstId;
	}

	@PostMapping("/obrisiKomentar")
	public String obrisiKomentar(@RequestParam("komentarId") int komentarId, HttpSession session) {
		Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovaniKorisnik == null) {
			return "redirect:/prijava";
		}

		Komentar komentar = koms.nadjiKomentarPoId(komentarId);
		if (komentar != null && komentar.getKorisnik().getId() == ulogovaniKorisnik.getId()) {
			koms.obrisiKomentar(komentarId);
		}

		return "redirect:/tekst/" + komentar.getTekstPesme().getId(); // Vrati korisnika na stranicu teksta
	}

}
