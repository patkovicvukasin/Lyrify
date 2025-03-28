package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.KomentarService;
import com.example.demo.services.OcenaService;
import com.example.demo.services.OmiljeniTekstService;
import com.example.demo.services.TekstPesmeService;

import jakarta.servlet.http.HttpSession;
import model.Komentar;
import model.Korisnik;
import model.TekstPesme;
import model.Uloga;

@Controller
public class OmiljeniTekstController {

	@Autowired
	OmiljeniTekstService oms;

	@Autowired
	TekstPesmeService tps;

	@Autowired
	KomentarService koms;

	@Autowired
	OcenaService os;

	@PostMapping("/dodajUOmiljene")
	public String dodajUOmiljene(@RequestParam("tekstId") int tekstId, HttpSession session, Model model) {

		Korisnik korisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (korisnik == null) {
			model.addAttribute("poruka", "Morate biti prijavljeni da biste dodali tekst u omiljene.");
			return ponovoUkljuciAtribute(tekstId, model, session);
		}

		oms.dodajUOmiljene(tekstId, korisnik);
		model.addAttribute("poruka", "Dodato u omiljene.");

		return ponovoUkljuciAtribute(tekstId, model, session);
	}

	@PostMapping("/ukloniIzOmiljenih")
	public String ukloniIzOmiljenih(@RequestParam("tekstId") int tekstId, HttpSession session, Model model) {

		Korisnik korisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (korisnik == null) {
			model.addAttribute("poruka", "Morate biti prijavljeni da biste uklonili tekst iz omiljenih.");
			return ponovoUkljuciAtribute(tekstId, model, session);
		}

		oms.ukloniIzOmiljenih(tekstId, korisnik);
		model.addAttribute("poruka", "Uklonjeno iz omiljenih.");

		return ponovoUkljuciAtribute(tekstId, model, session);
	}

	/** Metoda koja "simulira" GET logiku i vraća direktno tekst.jsp */
	private String ponovoUkljuciAtribute(int tekstId, Model model, HttpSession session) {
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);
		model.addAttribute("tekstPesme", tekstPesme);

		List<Komentar> komentari = koms.nadjiKomentareZaTekst(tekstId);
		model.addAttribute("komentari", komentari);

		Double prosecnaOcena = os.nadjiProsecnuOcenu(tekstId);
		model.addAttribute("prosecnaOcena", prosecnaOcena);

		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		model.addAttribute("ulogovaniKorisnik", ulogovani);

		boolean jeOmiljen = false;
		boolean jeAutor = false;
		if (ulogovani != null) {
			jeOmiljen = oms.jeTekstUOmiljenim(ulogovani.getId(), tekstId);
			jeAutor = (tekstPesme.getKorisnik().getId() == ulogovani.getId());
		}
		model.addAttribute("jeOmiljen", jeOmiljen);
		model.addAttribute("jeAutor", jeAutor);

		boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		model.addAttribute("isAdmin", isAdmin);

		return "tekst"; // Vrati JSP stranicu
	}

}
