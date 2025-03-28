package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Komentar;

public interface KomentarRepository extends JpaRepository<Komentar, Integer> {
	List<Komentar> findByTekstPesmeId(int tekstPesmeId);
}
