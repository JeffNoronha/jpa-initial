package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PagamentoAprazo extends TipoPagamento {

	@Column(name = "division")
	private Integer qtdParcelas;

	public Integer getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(Integer qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}
	
}
