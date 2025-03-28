package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Pesma;

public interface PesmaRepository extends JpaRepository<Pesma, Integer> {
	List<Pesma> findByNazivContainingIgnoreCase(String naziv);

	List<Pesma> findByZanrId(Integer zanrId);

	@Query("SELECT p FROM Pesma p WHERE p.naziv = :naziv AND p.izvodjac = :izvodjac")
	List<Pesma> findByNazivAndIzvodjac(@Param("naziv") String naziv, @Param("izvodjac") String izvodjac);

}
