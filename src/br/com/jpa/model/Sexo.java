package br.com.jpa.model;

public enum Sexo {

	M("Masculino"),
	F("Feminino");
	
	public String sexo;
	
	Sexo(String sexo) {
		this.sexo = sexo;
	}
}
