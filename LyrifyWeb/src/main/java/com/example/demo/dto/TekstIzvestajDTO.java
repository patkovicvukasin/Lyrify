package com.example.demo.dto;

public class TekstIzvestajDTO {

    private Integer tekstPesmeId;
    private String nazivPesme;
    private String izvodjac;
    private String korisnickoIme; // Korisnik koji je objavio tekst
    private Double prosecnaOcena;

    public TekstIzvestajDTO(Integer tekstPesmeId,
                               String nazivPesme,
                               String izvodjac,
                               String korisnickoIme,
                               Double prosecnaOcena) {
        this.tekstPesmeId = tekstPesmeId;
        this.nazivPesme = nazivPesme;
        this.izvodjac = izvodjac;
        this.korisnickoIme = korisnickoIme;
        this.prosecnaOcena = prosecnaOcena;
    }

    // getteri, setteri po potrebi
    public Integer getTekstPesmeId() {
        return tekstPesmeId;
    }

    public String getNazivPesme() {
        return nazivPesme;
    }

    public String getIzvodjac() {
        return izvodjac;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }
}
