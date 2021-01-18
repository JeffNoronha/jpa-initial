package br.com.jpa.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class Instituicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id")
	private Integer id;
	
	@Column(name = "name", updatable = false)
	private String nome;
	
	@OneToMany 
	private List<Professor> professores;
	// uma Instituicao possui muitos professores e um Professor possui apenas uma Instituicao.
	
	public Instituicao() {}

	public Instituicao(Integer id, String nome, List<Professor> professores) {
		this.id = id;
		this.nome = nome;
		this.professores = professores;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	@Override
	public String toString() {
		return "Instituicao [id=" + id + ", nome=" + nome + ", professores=" + professores + "]";
	}
	
}
