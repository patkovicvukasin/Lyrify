package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.PesmaService;
import com.example.demo.services.ZanrService;

import jakarta.servlet.http.HttpSession;
import model.Korisnik;
import model.Pesma;
import model.Zanr;

@Controller
public class PesmaController {

	@Autowired
	private PesmaService ps;

	@Autowired
	private ZanrService zs;

	@GetMapping("/pretraga")
	public String pretragaPesme(@RequestParam("pesma") String unos, HttpSession session, Model m) {
		System.out.println("Unos za pretragu: " + unos);
		List<Pesma> pesme;

		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani != null) {
			// Registrovan korisnik: pretraga po imenu ili tekstu
			pesme = ps.pretraziPesmePoImenuIliTekstu(unos);
		} else {
			// Neregistrovan korisnik: samo pretraga po imenu
			pesme = ps.pretraziPesmePoImenu(unos);
		}

		System.out.println("Pronađene pesme: " + pesme);
		m.addAttribute("pesme", pesme);
		m.addAttribute("pretraga", unos);
		return "rezultatPretrage";
	}

	@GetMapping("/pretragaPoZanru")
	public String pretragaPoZanru(@RequestParam("zanrId") Integer zanrId, Model m) {
		System.out.println("ID izabranog žanra: " + zanrId); // Debug log
		List<Pesma> pesme = ps.pretraziPesmePoZanru(zanrId);
		// System.out.println("Pronađene pesme za žanr: " + pesme); // Debug log
		Zanr z = zs.pronadjiZanrPoId(zanrId);
		m.addAttribute("pesme", pesme);
		m.addAttribute("nazivZanra", z.getNaziv());
		return "rezultatPretrageZanr";
	}

	@PostMapping("/processDodajPesmu")
	public String processDodajPesmu(@RequestParam("naziv") String naziv, @RequestParam("izvodjac") String izvodjac,
			@RequestParam("zanrId") int zanrId, @RequestParam("tekst") String tekst, HttpSession session, Model m) {

		boolean pesmaVecPostoji = ps.pesmaVecPostoji(naziv, izvodjac);
		if (pesmaVecPostoji) {
			m.addAttribute("naziv", naziv);
			m.addAttribute("izvodjac", izvodjac);
			return "postojiTekst";
		}

		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovanKorisnik == null) {
			return "redirect:/prijava";
		}

		// Pozivamo servis da doda pesmu i njen tekst
		ps.dodajPesmuINjenTekst(naziv, izvodjac, zanrId, tekst, ulogovanKorisnik);

		return "redirect:/"; // Povratak na početnu stranicu
	}

}
