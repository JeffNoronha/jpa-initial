package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "guitar")
public class Guitarra extends Instrumento {

	@Column(name = "name")
	private String nome;
	
	@Column(name = "chords")
	private Integer qtdCordas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdCordas() {
		return qtdCordas;
	}

	public void setQtdCordas(Integer qtdCordas) {
		this.qtdCordas = qtdCordas;
	}
	
}
