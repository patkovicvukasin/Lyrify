package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.KomentarService;
import com.example.demo.services.KorisnikService;
import com.example.demo.services.OcenaService;
import com.example.demo.services.OmiljeniTekstService;
import com.example.demo.services.TekstPesmeService;

import jakarta.servlet.http.HttpSession;
import model.Korisnik;
import model.OmiljeniTekst;
import model.TekstPesme;
import model.Uloga;

@Controller
public class KorisnikController {

	@Autowired
	KorisnikService ks;

	@Autowired
	TekstPesmeService tps;

	@Autowired
	OcenaService os;

	@Autowired
	OmiljeniTekstService oms;

	@GetMapping("/registracija")
	public String prikaziRegistraciju() {
		return "registracija";
	}

	@GetMapping("/prijava")
	public String prikaziPrijavu() {
		return "prijava";
	}

	@PostMapping("/processRegistracija")
	public String obradiRegistraciju(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("lozinka") String lozinka) {
		boolean postoji = ks.proveriDaLiPostojiKorisnik(username, email);
		if(postoji) {
			return "redirect:/registracija?error=UserExists";
		}
		// Pozovi servis za registraciju korisnika
		ks.registrujKorisnika(username, email, lozinka);
		return "redirect:/";
	}

//	@PostMapping("/processPrijava")
//	public String obradiPrijavu(@RequestParam("email") String email, @RequestParam("lozinka") String lozinka,
//			HttpSession session) {
//		boolean uspesno = ks.prijaviKorisnika(email, lozinka);
//		if (uspesno) {
//			Korisnik k = ks.nadjiKorisnikaPoEmailu(email);
//			session.setAttribute("ulogovaniKorisnik", k);
//
//			System.out.println("Prijava USPELA za korisnika: " + k.getKorisnickoIme());
//			return "redirect:/";
//		} else {
//			System.out.println("Neuspesna prijava");
//			return "redirect:/prijava?error=NeuspesnaPrijava";
//		}
//	}

	@GetMapping("/potvrda")
	public String potvrda() {
		return "potvrda";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("ulogovaniKorisnik");
		return "redirect:/";
	}

	@GetMapping("/mojNalog")
	public String prikaziMojNalog(HttpSession session, Model model) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani == null) {
			return "redirect:/prijava";
		}
		// Samo prikaz mojnalog.jsp, bez dohvaćanja pesama
		model.addAttribute("korisnik", ulogovani);

		List<TekstPesme> tekstovi = tps.nadjiTekstoveZaKorisnika(ulogovani.getId());
		model.addAttribute("tekstovi", tekstovi);

		List<OmiljeniTekst> omiljeniTekstovi = oms.omiljeniTekstoviKorisnika(ulogovani.getId());
		model.addAttribute("omiljeniTekstovi", omiljeniTekstovi);
		
		boolean isAdmin = (ulogovani.getUloga() == Uloga.ADMIN);
	    model.addAttribute("isAdmin", isAdmin);

		// Ovde dodaj logiku za dobijanje prosečne ocene svakog teksta
		Map<Integer, Double> mapeProsecneOcene = new HashMap<>();
		for (TekstPesme tp : tekstovi) {
			Double prosek = os.nadjiProsecnuOcenu(tp.getId());
			mapeProsecneOcene.put(tp.getId(), prosek);
		}
		model.addAttribute("mapaOcena", mapeProsecneOcene);

		return "mojnalog";
	}

	@GetMapping("/profil/{korisnikId}")
	public String prikaziProfilKorisnika(@PathVariable("korisnikId") int korisnikId, Model model) {
		// Pronađi korisnika po ID-u
		Korisnik korisnik = ks.nadjiKorisnikaPoId(korisnikId);
		if (korisnik == null) {
			throw new RuntimeException("Korisnik nije pronađen.");
		}

		// Pronađi tekstove koje je korisnik dodao
		List<TekstPesme> tekstovi = tps.nadjiTekstoveZaDrugogKorisnika(korisnikId);

		model.addAttribute("korisnikProfil", korisnik);
		model.addAttribute("tekstovi", tekstovi);

		return "nalog"; // Otvori `nalog.jsp`
	}

}
