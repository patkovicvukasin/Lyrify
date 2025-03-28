package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.PesmaRepository;
import com.example.demo.repositories.TekstPesmeRepository;
import com.example.demo.repositories.ZanrRepository;

import model.Korisnik;
import model.Pesma;
import model.TekstPesme;

@Service
public class PesmaService {

	@Autowired
	private PesmaRepository pr;

	@Autowired
	private ZanrRepository zr;

	@Autowired
	private TekstPesmeRepository tpr;

	public List<Pesma> pretraziPesmePoImenu(String naziv) {
		List<Pesma> pesme = pr.findByNazivContainingIgnoreCase(naziv);
		System.out.println("Pesme pronađene u bazi: " + pesme); // Log za bazu
		return pesme;
	}

	public List<Pesma> pretraziPesmePoZanru(Integer zanrId) {
		return pr.findByZanrId(zanrId);
	}

	public boolean pesmaVecPostoji(String naziv, String izvodjac) {
		List<Pesma> pesme = pr.findByNazivAndIzvodjac(naziv, izvodjac);
		return !pesme.isEmpty();
	}

	public void dodajPesmuINjenTekst(String naziv, String izvodjac, int zanrId, String tekst, Korisnik korisnik) {
		Pesma pesma = new Pesma();
		pesma.setNaziv(naziv);
		pesma.setIzvodjac(izvodjac);
		pesma.setZanr(zr.findById(zanrId).orElseThrow(() -> new RuntimeException("Zanr nije pronađen")));
		pesma.setKorisnik(korisnik);
		pr.save(pesma); // Sačuvaj pesmu

		TekstPesme tekstPesme = new TekstPesme();
		tekstPesme.setPesma(pesma); // Poveži tekst sa pesmom
		tekstPesme.setTekst(tekst);
		tekstPesme.setKorisnik(korisnik); // Poveži tekst sa korisnikom
		tekstPesme.setVerifikovan(false); // Podrazumevano nije verifikovan
		tpr.save(tekstPesme); // Sačuvaj tekst
	}

	public List<Pesma> pretraziPesmePoImenuIliTekstu(String tekst) {
		// Pretraga po imenu
		List<Pesma> pesmePoImenu = pr.findByNazivContainingIgnoreCase(tekst);

		// Pretraga po tekstu
		System.out.println("Pretraga po tekstu: " + tekst);
		List<TekstPesme> tekstovi = tpr.findByTekstContainingIgnoreCase(tekst);
		List<Pesma> pesmePoTekstu = tekstovi.stream().map(TekstPesme::getPesma).distinct().toList();

		// Kombinuj rezultate
		pesmePoImenu.addAll(pesmePoTekstu);
		return pesmePoImenu.stream().distinct().toList(); // Uklanjanje duplikata
	}

}
