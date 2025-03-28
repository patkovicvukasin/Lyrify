package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.OcenaTeksta;

public interface OcenaTekstaRepository extends JpaRepository<OcenaTeksta, Integer> {
	@Query("SELECT AVG(o.ocena) FROM OcenaTeksta o WHERE o.tekstPesme.id = :tekstPesmeId")
	Double findProsecnaOcena(@Param("tekstPesmeId") int tekstPesmeId);

	List<OcenaTeksta> findByTekstPesmeIdAndKorisnikId(int tekstPesmeId, int korisnikId);

}
