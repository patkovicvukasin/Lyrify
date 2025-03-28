package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KomentarRepository;
import com.example.demo.repositories.TekstPesmeRepository;

import model.Komentar;
import model.Korisnik;
import model.TekstPesme;

@Service
public class KomentarService {

	@Autowired
	private KomentarRepository komr;

	@Autowired
	private TekstPesmeRepository tpr;

	public List<Komentar> nadjiKomentareZaTekst(int tekstId) {
		List<Komentar> komentari = komr.findByTekstPesmeId(tekstId);
		System.out.println("Dohvaćeni komentari za tekst ID " + tekstId + ": " + komentari);
		return komentari;
	}

	public Komentar nadjiKomentarPoId(int komentarId) {
		return komr.findById(komentarId).orElse(null);
	}

	public void obrisiKomentar(int komentarId) {
		komr.deleteById(komentarId);
	}

	public void dodajKomentar(int tekstPesmeId, Korisnik korisnik, String tekstKomentara) {
		Komentar komentar = new Komentar();
		komentar.setTekstKomentara(tekstKomentara);
		komentar.setKorisnik(korisnik);

		TekstPesme tekstPesme = tpr.findById(tekstPesmeId)
				.orElseThrow(() -> new RuntimeException("Tekst pesme nije pronađen."));
		komentar.setTekstPesme(tekstPesme);

		komentar.setDatumVreme(new Date()); // Obavezno postavljanje datuma
		komr.save(komentar);
	}

}
