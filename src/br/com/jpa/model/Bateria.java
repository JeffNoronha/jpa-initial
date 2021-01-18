package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drum")
public class Bateria extends Instrumento {

	@Column(name = "name")
	private String nome;
	
	@Column(name = "cymbals")
	private Integer qtdPratos;
	
	@Column(name = "tones")
	private Integer qtdTons;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdPratos() {
		return qtdPratos;
	}

	public void setQtdPratos(Integer qtdPratos) {
		this.qtdPratos = qtdPratos;
	}

	public Integer getQtdTons() {
		return qtdTons;
	}

	public void setQtdTons(Integer qtdTons) {
		this.qtdTons = qtdTons;
	}
	
}
