package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="datum_registracije")
	private LocalDate datumRegistracije;
	
	@Column(name = "uloga", nullable = false)
    @Enumerated(EnumType.STRING)
    private Uloga uloga;

	private String email;

	@Column(name="korisnicko_ime")
	private String korisnickoIme;

	private String lozinka;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="korisnik")
	private List<Komentar> komentars;

	//bi-directional many-to-one association to OcenaTeksta
	@OneToMany(mappedBy="korisnik")
	private List<OcenaTeksta> ocenaTekstas;

	//bi-directional many-to-one association to OmiljenaLista
	@OneToMany(mappedBy="korisnik")
	private List<OmiljeniTekst> omiljenaListas;

	//bi-directional many-to-one association to Pesma
	@OneToMany(mappedBy="korisnik")
	private List<Pesma> pesmas;

	public Korisnik() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

	public LocalDate getDatumRegistracije() {
		return this.datumRegistracije;
	}

	public void setDatumRegistracije(LocalDate datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return this.lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public List<Komentar> getKomentars() {
		return this.komentars;
	}

	public void setKomentars(List<Komentar> komentars) {
		this.komentars = komentars;
	}

	public Komentar addKomentar(Komentar komentar) {
		getKomentars().add(komentar);
		komentar.setKorisnik(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setKorisnik(null);

		return komentar;
	}

	public List<OcenaTeksta> getOcenaTekstas() {
		return this.ocenaTekstas;
	}

	public void setOcenaTekstas(List<OcenaTeksta> ocenaTekstas) {
		this.ocenaTekstas = ocenaTekstas;
	}

	public OcenaTeksta addOcenaTeksta(OcenaTeksta ocenaTeksta) {
		getOcenaTekstas().add(ocenaTeksta);
		ocenaTeksta.setKorisnik(this);

		return ocenaTeksta;
	}

	public OcenaTeksta removeOcenaTeksta(OcenaTeksta ocenaTeksta) {
		getOcenaTekstas().remove(ocenaTeksta);
		ocenaTeksta.setKorisnik(null);

		return ocenaTeksta;
	}

	public List<OmiljeniTekst> getOmiljenaListas() {
		return this.omiljenaListas;
	}

	public void setOmiljenaListas(List<OmiljeniTekst> omiljenaListas) {
		this.omiljenaListas = omiljenaListas;
	}

	public OmiljeniTekst addOmiljenaLista(OmiljeniTekst omiljenaLista) {
		getOmiljenaListas().add(omiljenaLista);
		omiljenaLista.setKorisnik(this);

		return omiljenaLista;
	}

	public OmiljeniTekst removeOmiljenaLista(OmiljeniTekst omiljenaLista) {
		getOmiljenaListas().remove(omiljenaLista);
		omiljenaLista.setKorisnik(null);

		return omiljenaLista;
	}

	public List<Pesma> getPesmas() {
		return this.pesmas;
	}

	public void setPesmas(List<Pesma> pesmas) {
		this.pesmas = pesmas;
	}

	public Pesma addPesma(Pesma pesma) {
		getPesmas().add(pesma);
		pesma.setKorisnik(this);

		return pesma;
	}

	public Pesma removePesma(Pesma pesma) {
		getPesmas().remove(pesma);
		pesma.setKorisnik(null);

		return pesma;
	}

}