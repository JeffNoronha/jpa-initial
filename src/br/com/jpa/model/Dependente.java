package br.com.jpa.model;

import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
@Table(name = "dependent")
public class Dependente {

	@EmbeddedId
	private DependenteId dependenteId;
	
	@ManyToOne
	@MapsId("funcionarioId")
	@JoinColumn(name = "employee_id")
	private Funcionario funcionario;

	public DependenteId getDependenteId() {
		return dependenteId;
	}

	public void setDependenteId(DependenteId dependenteId) {
		this.dependenteId = dependenteId;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}
