package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.TekstPesme;

public interface TekstPesmeRepository extends JpaRepository<TekstPesme, Integer> {
	List<TekstPesme> findByPesmaId(Integer pesmaId);

	List<TekstPesme> findByKorisnikId(int korisnikId);

	@Query("SELECT t FROM TekstPesme t WHERE LOWER(t.tekst) LIKE LOWER(CONCAT('%', :tekst, '%'))")
	List<TekstPesme> findByTekstContainingIgnoreCase(@Param("tekst") String tekst);
	
	List<TekstPesme> findByPesmaIdIn(List<Integer> ids);

}
