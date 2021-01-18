package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "governor")
public class Governador {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "governor_id")
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String nome;

	@OneToOne(mappedBy = "governador")
	private Estado estado;
	
	public Governador() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
