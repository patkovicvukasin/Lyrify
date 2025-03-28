package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.ZanrService;

import jakarta.servlet.http.HttpSession;
import model.Korisnik;
import model.Uloga;
import model.Zanr;

@Controller
public class ZanrController {

	@Autowired
	private ZanrService zs;

	@GetMapping("/")
	public String zanrovi(Model m) {
		List<Zanr> zanrovi = zs.zanrovi();
		// System.out.println("Zanrovi u kontroleru: " + zanrovi);
		m.addAttribute("zanrovi", zanrovi);
		return "index";
	}

	@GetMapping("/dodajPesmu")
	public String prikaziDodajPesmu(Model model) {
		model.addAttribute("zanrovi", zs.zanrovi()); // Dodaj zanrove u model
		return "dodajPesmu"; // Prikaz nove JSP stranice
	}

	@PostMapping("/processDodajZanr")
	public String processDodajZanr(@RequestParam("zanrNaziv") String zanrNaziv, HttpSession session, Model m) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		// Provera da li je korisnik admin
		if (ulogovani == null || ulogovani.getUloga() != Uloga.ADMIN) {
			return "redirect:/"; // Ako nije admin, vrati na početnu
		}
		
		boolean postoji = zs.proveriDaLiPostojiZanr(zanrNaziv);
		
		if(postoji) {
			return "redirect:/dodajTekst?error=ZanrExists";
		}

		boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		m.addAttribute("isAdmin", isAdmin);

		// Dodaj novi žanr
		zs.dodajZanr(zanrNaziv);

		return "redirect:/dodajTekst"; // Vrati na stranicu za dodavanje teksta
	}

}
