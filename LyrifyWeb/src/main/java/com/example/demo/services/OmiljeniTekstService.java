package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.OmiljeniTekstRepository;
import com.example.demo.repositories.TekstPesmeRepository;

import model.Korisnik;
import model.OmiljeniTekst;
import model.TekstPesme;

@Service
public class OmiljeniTekstService {

	@Autowired
	private OmiljeniTekstRepository omr;

	@Autowired
	private TekstPesmeRepository tpr;

	// Dohvatanje omiljenih tekstova korisnika
	public List<OmiljeniTekst> omiljeniTekstoviKorisnika(int korisnikId) {
		return omr.findByKorisnikId(korisnikId);
	}

	public boolean jeTekstUOmiljenim(int korisnikId, int tekstId) {
		System.out.println("Provera da li je tekst ID: " + tekstId + " u omiljenim za korisnika ID: " + korisnikId);
		OmiljeniTekst omiljeniTekst = omr.findByKorisnikIdAndTekstId(korisnikId, tekstId);
		boolean rezultat = omiljeniTekst != null;
		System.out.println("Rezultat provere: " + rezultat);
		return rezultat;
	}

	public void ukloniIzOmiljenih(int tekstId, Korisnik korisnik) {
		System.out.println(
				"Provera postojanja teksta ID: " + tekstId + " u omiljenim za korisnika ID: " + korisnik.getId());
		OmiljeniTekst omiljeniTekst = omr.findByKorisnikIdAndTekstId(korisnik.getId(), tekstId);
		if (omiljeniTekst != null) {
			System.out.println("Tekst ID: " + tekstId + " pronađen u omiljenim. Brišemo...");
			omr.delete(omiljeniTekst);
			System.out.println("Tekst ID: " + tekstId + " uspešno obrisan iz omiljenih.");
		} else {
			System.out.println(
					"Tekst ID: " + tekstId + " nije pronađen u omiljenim za korisnika ID: " + korisnik.getId());
		}
	}

	public void dodajUOmiljene(int tekstId, Korisnik korisnik) {
		OmiljeniTekst postojeci = omr.findByKorisnikIdAndTekstId(korisnik.getId(), tekstId);
		if (postojeci != null) {
			return;
		}

		TekstPesme tp = tpr.findById(tekstId).orElseThrow(() -> {
			return new RuntimeException("Tekst nije pronađen");
		});

		OmiljeniTekst om = new OmiljeniTekst();
		om.setKorisnikId(korisnik.getId());
		om.setTekstId(tp.getId());

		System.out.println("Dodavanje u omiljene - Korisnik ID: " + korisnik.getId() + ", Tekst ID: " + tekstId);
		omr.save(om);
		System.out.println("Tekst ID: " + tekstId + " dodat u omiljene za korisnika ID: " + korisnik.getId());

	}

}
