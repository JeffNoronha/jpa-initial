package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Carro extends Veiculo {

	@Column(name = "name")
	private String nome;
	
	@Column(name = "number_of_doors")
	private Integer qtdPortas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdPortas() {
		return qtdPortas;
	}

	public void setQtdPortas(Integer qtdPortas) {
		this.qtdPortas = qtdPortas;
	}
	
}
