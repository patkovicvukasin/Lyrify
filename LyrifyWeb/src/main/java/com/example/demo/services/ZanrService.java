package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.ZanrRepository;

import model.Korisnik;
import model.Zanr;

@Service
public class ZanrService {

	@Autowired
	private ZanrRepository zr;

	public List<Zanr> zanrovi() {
		List<Zanr> zanrovi = zr.findAll();
		// System.out.println("Zanrovi iz baze: " + zanrovi);
		return zanrovi;
	}

	public Zanr pronadjiZanrPoId(Integer zanrId) {
		return zr.findById(zanrId).get();
	}

	public Zanr dodajZanr(Zanr zanr) {
		return zr.save(zanr);
	}

	public void dodajZanr(String naziv) {
		Zanr noviZanr = new Zanr();
		noviZanr.setNaziv(naziv);
		zr.save(noviZanr); // zr je ZanrRepository
	}
	
	public boolean proveriDaLiPostojiZanr(String naziv) {
	    // Proveri da li postoji email
	    Zanr pronadji = zr.findByNaziv(naziv);
	    if (pronadji != null) {
	        return true;
	    }
	    
	    return false;   
	}

}
