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
@Table(name = "post")
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id")
	private Long id;
	
	@Column(name = "text")
	private String texto;
	
	@OneToMany(mappedBy = "postagem")
	private List<PostagemAvaliacao> avaliacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<PostagemAvaliacao> getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(List<PostagemAvaliacao> avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
