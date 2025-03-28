package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the zanr database table.
 * 
 */
@Entity
@NamedQuery(name="Zanr.findAll", query="SELECT z FROM Zanr z")
public class Zanr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String naziv;

	//bi-directional many-to-one association to Pesma
	@OneToMany(mappedBy="zanr")
	private List<Pesma> pesmas;

	public Zanr() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Pesma> getPesmas() {
		return this.pesmas;
	}

	public void setPesmas(List<Pesma> pesmas) {
		this.pesmas = pesmas;
	}

	public Pesma addPesma(Pesma pesma) {
		getPesmas().add(pesma);
		pesma.setZanr(this);

		return pesma;
	}

	public Pesma removePesma(Pesma pesma) {
		getPesmas().remove(pesma);
		pesma.setZanr(null);

		return pesma;
	}

	@Override
	public String toString() {
		return naziv;
	}

	
	
}