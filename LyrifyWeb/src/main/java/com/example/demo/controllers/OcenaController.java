package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.OcenaService;
import com.example.demo.services.TekstPesmeService;

import jakarta.servlet.http.HttpSession;
import model.Korisnik;
import model.TekstPesme;

@Controller
public class OcenaController {

	@Autowired
	OcenaService os;

	@Autowired
	TekstPesmeService tps;

	// ocena teksta

	@PostMapping("/oceniTekst")
	public String oceniTekst(@RequestParam("tekstId") int tekstId, @RequestParam("ocena") int ocena,
			HttpSession session, Model m) {
		Korisnik korisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (korisnik == null)
			return "redirect:/prijava";

		// Ako pokušava da oceni svoj tekst
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);
		if (tekstPesme.getKorisnik().getId() == korisnik.getId()) {
			// možeš baciti grešku ili ignorisati unos
			return "redirect:/tekst/" + tekstId;
		}

		// Proveri da li je korisnik već ocenio tekst
		boolean ocenio = os.vecOcenioTekst(tekstId, korisnik.getId());
		if (ocenio) {
			m.addAttribute("poruka", "Već ste ocenili ovaj tekst.");
			return "vecOcenjeno";
		}

		os.dodajOcenuTeksta(tekstId, korisnik.getId(), ocena);
		return "redirect:/tekst/" + tekstId;
	}

}
