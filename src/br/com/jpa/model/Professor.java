package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "teacher_id")
	private int registro;
	
	@Column(name = "name")
	private String nome;
	
	@Column(name = "department")
	private String departamento;

	public Professor() {}
	
	public Professor(int registro, String nome, String departamento) {
		this.registro = registro;
		this.nome = nome;
		this.departamento = departamento;
	}

	public int getRegistro() {
		return registro;
	}

	public void setRegistro(int registro) {
		this.registro = registro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	@Override
	public String toString() {
		return "Professor [registro=" + registro + ", nome=" + nome + ", departamento=" + departamento + "]";
	}
	
}
