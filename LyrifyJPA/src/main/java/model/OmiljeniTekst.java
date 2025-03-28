package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "omiljeni_tekst")
@IdClass(OmiljeniTekstId.class)
public class OmiljeniTekst {

    @Id
    @Column(name = "korisnik_id")
    private int korisnikId;

    @Id
    @Column(name = "tekst_id")
    private int tekstId;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", insertable = false, updatable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "tekst_id", insertable = false, updatable = false)
    private TekstPesme tekst;

    // Getteri i setteri
    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getTekstId() {
        return tekstId;
    }

    public void setTekstId(int tekstId) {
        this.tekstId = tekstId;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public TekstPesme getTekst() {
        return tekst;
    }

    public void setTekst(TekstPesme tekst) {
        this.tekst = tekst;
    }
}
