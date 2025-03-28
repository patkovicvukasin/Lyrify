package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.KorisnikRepository;

import model.Korisnik;

/**
 * Obrada Spring Security zahteva da pronadjemo korisnika
 * po "username" (kod nas je to "email") i da vratimo
 * u standardni format "UserDetails".
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1) pronadji korisnika u bazi
        Korisnik k = korisnikRepository.findByEmail(email);
        if (k == null) {
            throw new UsernameNotFoundException("Nepoznat korisnik: " + email);
        }

        // 2) Spring Security ‘User’ => username, password, role(s) ...
        //   Uloga je npr. "USER" ili "ADMIN". Pretpostavimo da "k.getUloga()" vraca npr. Uloga.USER / Uloga.ADMIN
        String role = (k.getUloga() != null) ? k.getUloga().name() : "USER"; 
        // .name() vraca npr. "USER" ili "ADMIN" ako je enum

        // 3) Vratimo "UserDetails" objekat
        return User.builder()
            .username(k.getEmail())
            .password(k.getLozinka())   // ovde treba da bude hesirano ako koristimo BCrypt
            .roles(role)               // Spring ce sam prefix "ROLE_"
            .build();
    }
}
