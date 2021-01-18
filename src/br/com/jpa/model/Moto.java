package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Moto extends Veiculo {

	@Column(name = "name")
	private String nome;
	
	@Column(name = "cylinder_capacity")
	private Integer qtdCilindradas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdCilindradas() {
		return qtdCilindradas;
	}

	public void setQtdCilindradas(Integer qtdCilindradas) {
		this.qtdCilindradas = qtdCilindradas;
	}
	
}
