package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	Korisnik findByEmail(String email);

	Optional<Korisnik> findById(int id);

	Korisnik findByKorisnickoIme(String korisnickoIme);


}
