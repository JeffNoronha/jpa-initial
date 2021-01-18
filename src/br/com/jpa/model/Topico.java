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
@Table(name = "topic")
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topic_id")
	private Long id;
	
	@Column(name = "name")
	private String nome;
	
	@OneToMany(mappedBy = "topico")
	private List<PostagemAvaliacao> avaliacao;

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

	public List<PostagemAvaliacao> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<PostagemAvaliacao> avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	
}
