package br.com.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostagemAvaliacaoId implements Serializable{

	private static final long serialVersionUID = 8560274242504982799L;

	@Column(name = "post_id")
	private Long postagemId;
	
	@Column(name = "topic_id")
	private Long topicoId;

	public Long getPostagemId() {
		return postagemId;
	}

	public void setPostagemId(Long postagemId) {
		this.postagemId = postagemId;
	}

	public Long getTopicoId() {
		return topicoId;
	}

	public void setTopicoId(Long topicoId) {
		this.topicoId = topicoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((postagemId == null) ? 0 : postagemId.hashCode());
		result = prime * result + ((topicoId == null) ? 0 : topicoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostagemAvaliacaoId other = (PostagemAvaliacaoId) obj;
		if (postagemId == null) {
			if (other.postagemId != null)
				return false;
		} else if (!postagemId.equals(other.postagemId))
			return false;
		if (topicoId == null) {
			if (other.topicoId != null)
				return false;
		} else if (!topicoId.equals(other.topicoId))
			return false;
		return true;
	}
	
	
}
