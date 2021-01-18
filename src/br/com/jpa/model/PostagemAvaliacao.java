package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "post_raiting")
public class PostagemAvaliacao {

	@EmbeddedId
	private PostagemAvaliacaoId id;
	
	@ManyToOne
	@MapsId("postagemId")
	@JoinColumn(name = "post_id")
	private Postagem postagem;
	
	@ManyToOne
	@MapsId("topicoId")
	@JoinColumn(name = "topic_id")
	private Topico topico;
	
	@Column(name = "raiting")
	private Integer avaliacao;

	public PostagemAvaliacaoId getId() {
		return id;
	}

	public void setId(PostagemAvaliacaoId id) {
		this.id = id;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public Integer getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
