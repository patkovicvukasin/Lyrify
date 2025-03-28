package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.services.IzvestajService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Korisnik;
import model.Uloga;
import model.Zanr;

@Controller
@RequestMapping("/admin/report")
public class IzvestajController {

    @Autowired
    private IzvestajService is;

    @GetMapping("/korisnici")
    @ResponseBody
    public void getKorisniciReport(HttpServletResponse response) throws Exception {
        // Pozivamo servis, koji upisuje PDF u response
        is.generateKorisniciPdf(response);
    }
    
    @GetMapping("/tekstovi")
    public void getTekstoviReport(
            @RequestParam(value="zanrId", required=false) Integer zanrId,
            HttpServletResponse response) throws Exception {
        
        // Ako zanrId nije poslat, bice null => report za sve
        // Ako jeste, bice npr. 1 => report samo za taj zanr
        is.generateTekstoviPdf(zanrId, response);
    }
    
    @GetMapping("/izvestaji")
    public String prikaziIzvestaje(HttpSession session, Model model) {
        // Uzimamo žanrove iz servisa i prosleđujemo JSP-u
        List<Zanr> zanrovi = is.vratiSveZanrove();
        model.addAttribute("zanrovi", zanrovi);
        
        Korisnik ulogovani = (Korisnik) session.getAttribute("ulogovaniKorisnik");
		if (ulogovani == null) {
			return "redirect:/prijava";
		}
        
        boolean isAdmin = (ulogovani != null && ulogovani.getUloga() == Uloga.ADMIN);
		model.addAttribute("isAdmin", isAdmin);

        // Vraćamo ime JSP fajla (npr. /WEB-INF/jsp/izvestaji.jsp)
        return "izvestaji";
    }
}
