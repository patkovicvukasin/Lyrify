package model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "pesma")
@NamedQuery(name = "Pesma.findAll", query = "SELECT p FROM Pesma p")
public class Pesma implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String izvodjac;

    @Column(name = "link")
    private String link;

    private String naziv;
    private String slika;

    // bi-directional many-to-one association to Zanr
    @ManyToOne
    @JoinColumn(name = "zanr_id")
    private Zanr zanr;

    // bi-directional many-to-one association to Korisnik
    @ManyToOne
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;

    // bi-directional one-to-many association to TekstPesme
    @OneToMany(mappedBy = "pesma", cascade = CascadeType.ALL)
    private List<TekstPesme> tekstPesmes;

    public Pesma() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIzvodjac() {
        return izvodjac;
    }

    public void setIzvodjac(String izvodjac) {
        this.izvodjac = izvodjac;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<TekstPesme> getTekstPesmes() {
        return tekstPesmes;
    }

    public void setTekstPesmes(List<TekstPesme> tekstPesmes) {
        this.tekstPesmes = tekstPesmes;
    }

    // Pomoćne metode za dodavanje i uklanjanje TekstPesme iz liste
    public TekstPesme addTekstPesme(TekstPesme tekstPesme) {
        getTekstPesmes().add(tekstPesme);
        tekstPesme.setPesma(this);
        return tekstPesme;
    }

    public TekstPesme removeTekstPesme(TekstPesme tekstPesme) {
        getTekstPesmes().remove(tekstPesme);
        tekstPesme.setPesma(null);
        return tekstPesme;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
