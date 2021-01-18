package br.com.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PagamentoAvista extends TipoPagamento {

	@Column(name = "discount", precision = 3, scale = 3)
	private BigDecimal percentualDesconto;

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}
	
}
