package model;

import java.io.Serializable;
import java.util.Objects;

public class OmiljeniTekstId implements Serializable {
    private int korisnikId;
    private int tekstId;

    // Default konstruktor
    public OmiljeniTekstId() {}

    // Parametrizovani konstruktor
    public OmiljeniTekstId(int korisnikId, int tekstId) {
        this.korisnikId = korisnikId;
        this.tekstId = tekstId;
    }

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

    // equals i hashCode metode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmiljeniTekstId that = (OmiljeniTekstId) o;
        return korisnikId == that.korisnikId && tekstId == that.tekstId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnikId, tekstId);
    }
}
