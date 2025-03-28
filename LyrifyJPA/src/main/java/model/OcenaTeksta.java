//package model;
//
//import java.io.Serializable;
//import jakarta.persistence.*;
//
//
///**
// * The persistent class for the ocena_teksta database table.
// * 
// */
//@Entity
//@Table(name="ocena_teksta")
//@NamedQuery(name="OcenaTeksta.findAll", query="SELECT o FROM OcenaTeksta o")
//public class OcenaTeksta implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
//
//	private int ocena;
//
//	 // Bi-directional many-to-one association to Korisnik
//    @ManyToOne
//    @JoinColumn(name = "korisnik_id", nullable = false)
//    private Korisnik korisnik;
//
//    // Bi-directional many-to-one association to TekstPesme
//    @ManyToOne
//    @JoinColumn(name = "tekst_pesme_id")
//    private TekstPesme tekstPesme;
//
//	public OcenaTeksta() {
//	}
//
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public int getOcena() {
//		return this.ocena;
//	}
//
//	public void setOcena(int ocena) {
//		this.ocena = ocena;
//	}
//
//	public Korisnik getKorisnik() {
//		return this.korisnik;
//	}
//
//	public void setKorisnik(Korisnik korisnik) {
//		this.korisnik = korisnik;
//	}
//
//	public TekstPesme getTekstPesme() {
//		return this.tekstPesme;
//	}
//
//	public void setTekstPesme(TekstPesme tekstPesme) {
//		this.tekstPesme = tekstPesme;
//	}
//
//}
package model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="ocena_teksta")
@NamedQuery(name="OcenaTeksta.findAll", query="SELECT o FROM OcenaTeksta o")
public class OcenaTeksta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private int ocena;

    // bi-directional many-to-one association to Korisnik
    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    // bi-directional many-to-one association to TekstPesme
    @ManyToOne
    @JoinColumn(name = "tekst_pesme_id", nullable = false)
    private TekstPesme tekstPesme;

    public OcenaTeksta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public TekstPesme getTekstPesme() {
        return tekstPesme;
    }

    public void setTekstPesme(TekstPesme tekstPesme) {
        this.tekstPesme = tekstPesme;
    }
}
