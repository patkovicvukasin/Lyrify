package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.KomentarService;
import com.example.demo.services.OcenaService;
import com.example.demo.services.OmiljeniTekstService;
import com.example.demo.services.PesmaService;
import com.example.demo.services.TekstPesmeService;
import com.example.demo.services.ZanrService;

import jakarta.servlet.http.HttpSession;
import model.Komentar;
import model.Korisnik;
import model.Pesma;
import model.TekstPesme;
import model.Uloga;
import model.Zanr;

@Controller
public class TekstPesmeController {

	@Autowired
	TekstPesmeService tps;

	@Autowired
	OcenaService os;

	@Autowired
	KomentarService koms;

	@Autowired
	OmiljeniTekstService oms;

	@Autowired
	ZanrService zs;

	@Autowired
	PesmaService ps;

	// dodavanje teksta

	@PostMapping("/processDodajTekst")
	public String processDodajTekst(@RequestParam("pesmaId") int pesmaId, @RequestParam("tekst") String tekst,
			HttpSession session) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani == null) {
			return "redirect:/prijava";
		}

		// Pozivamo servis da sačuva tekst
		tps.dodajTekstPesme(pesmaId, tekst, ulogovani);

		// Preusmeravamo na stranicu "uspesno.jsp"
		return "uspesno";
	}

	@GetMapping("/dodajTekst")
	public String dodajTekst(@RequestParam(value = "zanrId", required = false) Integer zanrId, HttpSession session,
			Model model) {
		// Proverimo da li je korisnik ulogovan
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani == null) {
			return "redirect:/prijava";
		}

		// Uzimamo sve zanrove
		List<Zanr> zanrovi = zs.zanrovi();
		model.addAttribute("zanrovi", zanrovi);

		boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		model.addAttribute("isAdmin", isAdmin);

		// Ako je korisnik izabrao zanr, ucitamo pesme tog zanra
		if (zanrId != null) {
			List<Pesma> pesme = ps.pretraziPesmePoZanru(zanrId);
			model.addAttribute("pesme", pesme);
		}

		return "dodajTekst";
	}

	// prikazivanje teksta

	@GetMapping("/tekstovi/{pesmaId}")
	public String prikaziTekstoveZaPesmu(@PathVariable("pesmaId") int pesmaId,
			@RequestParam(value = "sortiraj", required = false, defaultValue = "false") boolean sortiraj, Model model) {
		List<TekstPesme> tekstovi;

		if (sortiraj) {
			tekstovi = tps.sortiraniTekstoviZaPesmu(pesmaId);
		} else {
			tekstovi = tps.nadjiTekstoveZaPesmu(pesmaId);
		}

		for (TekstPesme tekst : tekstovi) {
			Double prosek = os.nadjiProsecnuOcenu(tekst.getId());
			tekst.setProsecnaOcena(prosek);
		}

		model.addAttribute("tekstovi", tekstovi);
		model.addAttribute("pesmaId", pesmaId);
		model.addAttribute("trenutnoSortirano", sortiraj);
		return "tekstovi";
	}

	@GetMapping("/tekstKorisnika/{tekstId}")
	public String prikaziTekstKorisnika(@PathVariable("tekstId") int tekstId, HttpSession session, Model model) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani == null) {
			return "redirect:/prijava";
		}

		TekstPesme tekstPesme = tps.nadjiTekstPoId(tekstId); // Pronađi tekst po ID-u
		if (tekstPesme == null) {
			throw new RuntimeException("Tekst nije pronađen.");
		}
		model.addAttribute("tekstPesme", tekstPesme); // Prosleđujemo tekst na tekst.jsp

		List<Komentar> komentari = koms.nadjiKomentareZaTekst(tekstPesme.getId());
		System.out.println("Komentari za tekst ID " + tekstPesme.getId() + ": " + komentari);
		model.addAttribute("komentari", komentari);

		boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		model.addAttribute("isAdmin", isAdmin);
		System.out.println("Tip uloge: " + (ulogovani != null ? ulogovani.getUloga().getClass().getName() : "null"));

		System.out.println("Uloga korisnika: " + (ulogovani != null ? ulogovani.getUloga() : "null"));
		return "tekst";
	}

	@GetMapping("/tekstPesme/{pesmaId}")
	public String prikaziTekstPesme(@PathVariable("pesmaId") int pesmaId, Model model, HttpSession session) {
		List<TekstPesme> tekstovi = tps.nadjiTekstoveZaPesmu(pesmaId);

		if (tekstovi.size() == 1) {
			// Ako postoji samo jedan tekst, direktno prikaži tekst.jsp
			TekstPesme tekstPesme = tekstovi.get(0);
			model.addAttribute("tekstPesme", tekstPesme);

			List<Komentar> komentari = koms.nadjiKomentareZaTekst(tekstPesme.getId());
			System.out.println("Komentari za tekst ID " + tekstPesme.getId() + ": " + komentari);
			model.addAttribute("komentari", komentari);

			Double prosecnaOcena = os.nadjiProsecnuOcenu(tekstPesme.getId());
			model.addAttribute("prosecnaOcena", prosecnaOcena);

			Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
			model.addAttribute("ulogovaniKorisnik", ulogovani);

			boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
			model.addAttribute("isAdmin", isAdmin);
			System.out
					.println("Tip uloge: " + (ulogovani != null ? ulogovani.getUloga().getClass().getName() : "null"));

			System.out.println("Uloga korisnika: " + (ulogovani != null ? ulogovani.getUloga() : "null"));

			// Provera da li je tekst u omiljenim
			boolean jeOmiljen = false;
			boolean jeAutor = false;

			if (ulogovani != null) {
				jeOmiljen = oms.jeTekstUOmiljenim(ulogovani.getId(), tekstPesme.getId());
				jeAutor = tekstPesme.getKorisnik().getId() == ulogovani.getId();
			}
			System.out.println("jeOmiljen: " + jeOmiljen + ", jeAutor: " + jeAutor);

			model.addAttribute("jeOmiljen", jeOmiljen);
			model.addAttribute("jeAutor", jeAutor);

			return "tekst";

		} else {
			// Ako ih ima više, prikaži listu svih tekstova
			model.addAttribute("tekstovi", tekstovi);
			return "tekstovi";
		}
	}

	@GetMapping("/tekst/{tekstId}")
	public String prikaziPojedinacanTekst(@PathVariable int tekstId, Model model, HttpSession session) {
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);
		// prosecna ocena
		Double prosecnaOcena = os.nadjiProsecnuOcenu(tekstId);
		if (tekstPesme == null) {
			throw new RuntimeException("Tekst nije pronađen.");
		}

		List<Komentar> komentari = koms.nadjiKomentareZaTekst(tekstPesme.getId());
		System.out.println("Komentari za tekst ID " + tekstPesme.getId() + ": " + komentari);
		model.addAttribute("komentari", komentari);

		model.addAttribute("tekstPesme", tekstPesme);
		model.addAttribute("prosecnaOcena", prosecnaOcena);

		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		model.addAttribute("ulogovaniKorisnik", ulogovani);

		boolean jeOmiljen = false;
		boolean jeAutor = false;
		if (ulogovani != null) {
			jeOmiljen = oms.jeTekstUOmiljenim(ulogovani.getId(), tekstPesme.getId());
			jeAutor = (tekstPesme.getKorisnik().getId() == ulogovani.getId());
		}
		model.addAttribute("jeOmiljen", jeOmiljen);
		model.addAttribute("jeAutor", jeAutor);

		boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		model.addAttribute("isAdmin", isAdmin);
		// System.out.println("Uloga korisnika: " + (ulogovani != null ?
		// ulogovani.getUloga() : "null"));
		return "tekst"; // Ovo otvara postojeću `tekst.jsp` stranicu
	}

	// sortiranje tekstova

	@GetMapping("/tekstovi/sortiraj/{pesmaId}")
	public String sortiraniTekstoviZaPesmu(@PathVariable("pesmaId") int pesmaId, Model model) {
		List<TekstPesme> sortiraniTekstovi = tps.sortiraniTekstoviZaPesmu(pesmaId);
		model.addAttribute("tekstovi", sortiraniTekstovi);
		model.addAttribute("pesmaId", pesmaId);
		return "tekstovi";
	}

	// brisanje teksta

	@GetMapping("/brisanjeTeksta")
	public String prikaziStranicuZaBrisanje(@RequestParam("tekstId") int tekstId, Model model, HttpSession session) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);

		// Provera da li je korisnik autor teksta
		if (ulogovani == null || tekstPesme.getKorisnik().getId() != ulogovani.getId()) {
			return "redirect:/";
		}

		model.addAttribute("tekstPesme", tekstPesme);
		return "brisanjeTeksta";
	}

	@PostMapping("/processBrisanjeTeksta")
	public String obrisiTekst(@RequestParam("tekstId") int tekstId, HttpSession session) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);

		// Provera da li je korisnik autor teksta
		if (ulogovani == null || tekstPesme.getKorisnik().getId() != ulogovani.getId()) {
			return "redirect:/";
		}

		// Pozovi servis za brisanje teksta
		tps.obrisiTekst(tekstId);
		return "redirect:/";
	}

	// verifikacija

	@GetMapping("/verifikacija")
	public String prikaziVerifikaciju(@RequestParam("tekstId") int tekstId, Model model, HttpSession session) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		// Proveri da li je korisnik admin
		if (ulogovani == null || ulogovani.getUloga() != Uloga.ADMIN) {
			return "redirect:/";
		}

		// Dodaj tekst u model za prikaz na stranici
		TekstPesme tekstPesme = tps.nadjiTekstPesmePoId(tekstId);
		model.addAttribute("tekstPesme", tekstPesme);

		return "verifikacija";
	}

	@PostMapping("/processVerifikacija")
	public String procesuirajVerifikaciju(@RequestParam("tekstId") int tekstId, HttpSession session) {
		Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");

		// Proveri da li je korisnik admin
		if (ulogovani == null || ulogovani.getUloga() != Uloga.ADMIN) {
			return "redirect:/";
		}

		// Pozovi servis da verifikuje tekst
		try {
			tps.verifikujTekst(tekstId);
			System.out.println("Tekst sa ID " + tekstId + " uspešno verifikovan.");
		} catch (Exception e) {
			System.err.println("Greška prilikom verifikacije: " + e.getMessage());
			return "redirect:/error"; // Opcionalno, stranica za greške
		}

		return "redirect:/tekst/" + tekstId; // Vrati se na tekst nakon verifikacije
	}

}
