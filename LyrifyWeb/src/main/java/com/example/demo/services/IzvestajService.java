package com.example.demo.services;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TekstIzvestajDTO;
import com.example.demo.repositories.KorisnikRepository;
import com.example.demo.repositories.OcenaTekstaRepository;
import com.example.demo.repositories.PesmaRepository;
import com.example.demo.repositories.TekstPesmeRepository;
import com.example.demo.repositories.ZanrRepository;

import jakarta.servlet.http.HttpServletResponse;
import model.Korisnik;
import model.Pesma;
import model.TekstPesme;
import model.Zanr;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class IzvestajService {

    @Autowired
    private KorisnikRepository kr;
    
    @Autowired
    private PesmaRepository pr;
    
    @Autowired
    private TekstPesmeRepository tpr;
    
    @Autowired
    private OcenaTekstaRepository otr;
    
    @Autowired
    private ZanrRepository zr;

    public void generateKorisniciPdf(HttpServletResponse response) throws Exception {
        // 1) Dobavimo podatke iz baze
        List<Korisnik> lista = kr.findAll();
        System.out.println("Velicina liste: " + lista.size());

        // 2) Napravimo data source od te liste
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);

        // 3) Učitamo i kompajlujemo .jrxml
        InputStream is = getClass().getResourceAsStream("/jasperReports/korisnici.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(is);
        is.close();

        // 4) Map parametara (možeš i "imeKladionice" ili drugo ako želiš)
        Map<String, Object> params = new HashMap<>();
        params.put("nazivApp", "Lyrify Aplikacija");

        // 5) Popunimo izveštaj - glavni dataset
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);

        // 6) Podesimo HTTP response da vraća PDF
        response.setContentType("application/pdf");
        // inline (prikaz u browseru) ili attachment (skidanje)
        response.setHeader("Content-Disposition", "inline; filename=\"Korisnici.pdf\"");

        // 7) Izvezemo PDF direktno u OutputStream
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        out.flush();
        out.close();
    }
    
    public void generateTekstoviPdf(Integer zanrId, HttpServletResponse response) throws Exception {
        
        // 1) Dohvati sve TekstPesme koje pripadaju pesmama željenog žanra, ili sve
        List<TekstPesme> tekstovi;
        
        if (zanrId != null) {
            // Prvo dohvatimo sve Pesme koje imaju zanrId
            List<Pesma> pesme = pr.findByZanrId(zanrId);
            
            // Skupljamo ID-ove tih pesama
            List<Integer> pesmaIds = pesme.stream()
                                          .map(p -> p.getId())
                                          .toList();
            
            // Zatim dohvatimo sve tekstove koji su vezani za te pesme
            tekstovi = tpr.findByPesmaIdIn(pesmaIds);
        } else {
            // Bez filtera, svi tekstovi
            tekstovi = tpr.findAll();
        }

        // 2) Napravi listu DTO-ova
        List<TekstIzvestajDTO> dtoList = new ArrayList<>();
        for (TekstPesme tp : tekstovi) {
            Pesma p = tp.getPesma(); // Asocijacija u TekstPesme
            
            String nazivPesme = p.getNaziv();
            String izvodjac = p.getIzvodjac();
            String korisnickoIme = tp.getKorisnik().getKorisnickoIme();
            
            // Nadji prosečnu ocenu
            Double prosek = otr.findProsecnaOcena(tp.getId());
            if (prosek == null) {
                prosek = 0.0; // ili ostavi null
            }
            
            TekstIzvestajDTO dto = new TekstIzvestajDTO(
                tp.getId(),
                nazivPesme,
                izvodjac,
                korisnickoIme,
                prosek
            );
            dtoList.add(dto);
        }

        // 3) Napravimo data source za Jasper
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dtoList);

        // 4) Učitamo i kompajlujemo .jrxml
        InputStream is = getClass().getResourceAsStream("/jasperReports/tekstovi.jrxml");
        JasperReport report = JasperCompileManager.compileReport(is);
        is.close();

        // 5) Parametar za zanr (da prikažemo u Page Header)
        Map<String, Object> params = new HashMap<>();
        if (zanrId != null) {
        	Zanr z = zr.findById(zanrId).orElseThrow(() -> new RuntimeException("Zanr ne postoji"));
            params.put("odabraniZanr", z.getNaziv());
        } else {
            params.put("odabraniZanr", "Svi");
        }

        // 6) Popunimo izveštaj
        JasperPrint jp = JasperFillManager.fillReport(report, params, ds);

        // 7) Vratimo PDF u response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"Tekstovi.pdf\"");
        JasperExportManager.exportReportToPdfStream(jp, response.getOutputStream());
    }
    
    public List<Zanr> vratiSveZanrove() {
        return zr.findAll();
    }
    
}
