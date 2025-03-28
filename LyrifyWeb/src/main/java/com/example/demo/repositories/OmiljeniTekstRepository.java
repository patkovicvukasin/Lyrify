package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import model.OmiljeniTekst;
import model.OmiljeniTekstId;

public interface OmiljeniTekstRepository extends JpaRepository<OmiljeniTekst, OmiljeniTekstId> {

	List<OmiljeniTekst> findByKorisnikId(int korisnikId);

	OmiljeniTekst findByKorisnikIdAndTekstId(int korisnikId, int tekstId);
}
