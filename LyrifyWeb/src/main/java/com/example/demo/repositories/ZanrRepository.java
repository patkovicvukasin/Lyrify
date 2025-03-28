package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Zanr;

public interface ZanrRepository extends JpaRepository<Zanr, Integer> {

	Zanr findByNaziv(String naziv);

}
