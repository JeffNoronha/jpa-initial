package br.com.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Projeto {

	@EmbeddedId
	private ProjetoId id;
	
	@Column(name = "name", nullable = false, unique = true, length = 120)
	private String nome;
	
	@MapsId("departmentId")
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Departamento departamento;
}
