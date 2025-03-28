package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.KorisnikRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Korisnik;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private final KorisnikRepository korisnikRepository;
    
    public CustomLoginSuccessHandler(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException {

        String email = authentication.getName();
        Korisnik k = korisnikRepository.findByEmail(email);

        // stavi u sesiju
        request.getSession().setAttribute("ulogovaniKorisnik", k);

        // redirect na "/"
        response.sendRedirect(request.getContextPath() + "/");

    }
}
