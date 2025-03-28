package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.OcenaTekstaRepository;
import com.example.demo.repositories.TekstPesmeRepository;

import model.Korisnik;
import model.OcenaTeksta;
import model.TekstPesme;

@Service
public class OcenaService {

	@Autowired
	private OcenaTekstaRepository otr;

	@Autowired
	private TekstPesmeRepository tpr;

	@Autowired
	private KorisnikRepository kr;

	public void dodajOcenuTeksta(int tekstPesmeId, int korisnikId, int ocena) {
		TekstPesme tekstPesme = tpr.findById(tekstPesmeId)
				.orElseThrow(() -> new RuntimeException("Tekst nije pronađen."));
		Korisnik korisnik = kr.findById(korisnikId).orElseThrow(() -> new RuntimeException("Korisnik nije pronađen."));

		OcenaTeksta novaOcena = new OcenaTeksta();
		novaOcena.setTekstPesme(tekstPesme);
		novaOcena.setKorisnik(korisnik);
		novaOcena.setOcena(ocena);
		otr.save(novaOcena);
	}

	public Double nadjiProsecnuOcenu(int tekstPesmeId) {
		return otr.findProsecnaOcena(tekstPesmeId);
	}

	public boolean vecOcenioTekst(int tekstPesmeId, int korisnikId) {
		List<OcenaTeksta> postojeceOcene = otr.findByTekstPesmeIdAndKorisnikId(tekstPesmeId, korisnikId);
		return !postojeceOcene.isEmpty();
	}

}
