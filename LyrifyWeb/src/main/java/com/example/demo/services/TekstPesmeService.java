package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.OcenaTekstaRepository;
import com.example.demo.repositories.PesmaRepository;
import com.example.demo.repositories.TekstPesmeRepository;

import jakarta.transaction.Transactional;
import model.Korisnik;
import model.Pesma;
import model.TekstPesme;

@Service
public class TekstPesmeService {

	@Autowired
	TekstPesmeRepository tpr;

	@Autowired
	OcenaTekstaRepository otr;

	@Autowired
	PesmaRepository pr;

	public TekstPesme pronadjiTekstPesmePoPesmaId(Integer pesmaId) {
		return (TekstPesme) tpr.findByPesmaId(pesmaId);
	}

	public TekstPesme nadjiTekstPesmePoId(int tekstId) {
		return tpr.findById(tekstId).orElseThrow(() -> new RuntimeException("Tekst nije pronađen."));
	}

	public List<TekstPesme> nadjiTekstoveZaKorisnika(int korisnikId) {
		return tpr.findByKorisnikId(korisnikId);
	}

	public TekstPesme nadjiTekstPoId(int tekstId) {
		return tpr.findById(tekstId).orElseThrow(() -> new RuntimeException("Tekst nije pronađen."));
	}

	@Transactional
	public void verifikujTekst(int tekstId) {
		TekstPesme tekstPesme = tpr.findById(tekstId).orElseThrow(() -> new RuntimeException("Tekst nije pronađen."));

		tekstPesme.setVerifikovan(true);
		tpr.save(tekstPesme);
	}

	public void obrisiTekst(int tekstId) {
		tpr.deleteById(tekstId);
	}

	public List<TekstPesme> nadjiTekstoveZaDrugogKorisnika(int korisnikId) {
		return tpr.findByKorisnikId(korisnikId); // `tpr` je repozitorijum za tekstove pesama
	}

	public void dodajTekstPesme(int pesmaId, String tekst, Korisnik korisnik) {
		TekstPesme tekstPesme = new TekstPesme();
		Pesma pesma = pr.findById(pesmaId).orElseThrow(() -> new RuntimeException("Pesma nije pronađena."));
		tekstPesme.setPesma(pesma);
		tekstPesme.setKorisnik(korisnik);
		tekstPesme.setTekst(tekst);
		tekstPesme.setVerifikovan(false);
		tpr.save(tekstPesme);
	}

	public List<TekstPesme> nadjiTekstoveZaPesmu(int pesmaId) {
		List<TekstPesme> tekstovi = tpr.findByPesmaId(pesmaId);

		for (TekstPesme tekst : tekstovi) {
			// Računanje prosečne ocene za svaki tekst
			Double prosecnaOcena = otr.findProsecnaOcena(tekst.getId());
			tekst.setProsecnaOcena(prosecnaOcena);
		}

		return tpr.findByPesmaId(pesmaId);
	}

	public List<TekstPesme> sortiraniTekstoviZaPesmu(int pesmaId) {
		List<TekstPesme> tekstovi = tpr.findByPesmaId(pesmaId);

		for (TekstPesme tekst : tekstovi) {
			// Računanje prosečne ocene za svaki tekst
			Double prosecnaOcena = otr.findProsecnaOcena(tekst.getId());
			tekst.setProsecnaOcena(prosecnaOcena);
		}

		tekstovi.sort((t1, t2) -> {
			// Prioritet verifikovanih tekstova
			if (t1.getVerifikovan() && !t2.getVerifikovan()) {
				return -1;
			} else if (!t1.getVerifikovan() && t2.getVerifikovan()) {
				return 1;
			}

			// Ako oba imaju isti verifikovan status, poredi ocene
			if (t1.getProsecnaOcena() != null && t2.getProsecnaOcena() != null) {
				return t2.getProsecnaOcena().compareTo(t1.getProsecnaOcena());
			} else if (t1.getProsecnaOcena() != null) {
				return -1;
			} else if (t2.getProsecnaOcena() != null) {
				return 1;
			}

			return 0; // Ako su ocene jednake ili obe null
		});

		return tekstovi;
	}

}
