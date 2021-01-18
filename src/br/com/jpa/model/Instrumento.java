package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "instrument")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Instrumento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "instrument_id")
	private Long id;
	
	@Column(name = "brand")
	private String marca;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
}
