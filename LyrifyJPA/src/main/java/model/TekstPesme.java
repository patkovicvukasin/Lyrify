//package model;
//
//import java.io.Serializable;
//import jakarta.persistence.*;
//
//
///**
// * The persistent class for the tekst_pesme database table.
// * 
// */
//@Entity
//@Table(name="tekst_pesme")
//@NamedQuery(name="TekstPesme.findAll", query="SELECT t FROM TekstPesme t")
//public class TekstPesme implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
//
//	@Column(columnDefinition = "TEXT")
//	private String tekst;
//	
//	private Boolean verifikovan = false;
//
//	//bi-directional many-to-one association to Pesma
//	@ManyToOne
//	@JoinColumn(name="pesma_id")
//	private Pesma pesma;
//
//	
//	@ManyToOne
//	@JoinColumn(name = "korisnik_id", nullable = false) // Referenca na korisnika koji je dodao tekst
//	private Korisnik korisnik;
//
//	public TekstPesme() {
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
//	public String getTekst() {
//		return this.tekst;
//	}
//
//	public void setTekst(String tekst) {
//		this.tekst = tekst;
//	}
//	
//	public Boolean getVerifikovan() {
//        return verifikovan;
//    }
//
//    public void setVerifikovan(Boolean verifikovan) {
//        this.verifikovan = verifikovan;
//    }
//
//	public Pesma getPesma() {
//		return this.pesma;
//	}
//
//	public void setPesma(Pesma pesma) {
//		this.pesma = pesma;
//	}
//	
//	public Korisnik getKorisnik() {
//	    return this.korisnik;
//	}
//
//	public void setKorisnik(Korisnik korisnik) {
//	    this.korisnik = korisnik;
//	}
//
//}
package model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "tekst_pesme")
@NamedQuery(name="TekstPesme.findAll", query="SELECT t FROM TekstPesme t")
public class TekstPesme implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String tekst;

    private Boolean verifikovan = false;

    // bi-directional many-to-one association to Pesma
    @ManyToOne
    @JoinColumn(name="pesma_id")
    private Pesma pesma;

    // bi-directional many-to-one association to Korisnik
    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    // bi-directional one-to-many association to OcenaTeksta
    @OneToMany(mappedBy = "tekstPesme", cascade = CascadeType.ALL)
    private List<OcenaTeksta> ocenaTekstas;

    @Transient
    private Double prosecnaOcena;
    
    public TekstPesme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Boolean getVerifikovan() {
        return verifikovan;
    }

    public void setVerifikovan(Boolean verifikovan) {
        this.verifikovan = verifikovan;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public Pesma getPesma() {
        return pesma;
    }

    public void setPesma(Pesma pesma) {
        this.pesma = pesma;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<OcenaTeksta> getOcenaTekstas() {
        return ocenaTekstas;
    }

    public void setOcenaTekstas(List<OcenaTeksta> ocenaTekstas) {
        this.ocenaTekstas = ocenaTekstas;
    }

    public OcenaTeksta addOcenaTeksta(OcenaTeksta ocenaTeksta) {
        getOcenaTekstas().add(ocenaTeksta);
        ocenaTeksta.setTekstPesme(this);
        return ocenaTeksta;
    }

    public OcenaTeksta removeOcenaTeksta(OcenaTeksta ocenaTeksta) {
        getOcenaTekstas().remove(ocenaTeksta);
        ocenaTeksta.setTekstPesme(null);
        return ocenaTeksta;
    }
}
