package br.com.jpa.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "person")
@NamedQueries({
		@NamedQuery(name = "Pessoa.findByHeight", query = "SELECT p FROM Pessoa p WHERE p.altura >= :altura"),
		@NamedQuery(name = "Pessoa.findByGender", query = "SELECT p FROM Pessoa p WHERE p.sexo = ?1")
})
@NamedStoredProcedureQuery(name = "SpFindPerson", 
	resultClasses = Pessoa.class,
	procedureName = "sp_find_person",
	parameters = {
			@StoredProcedureParameter(
				mode = ParameterMode.IN,
				name = "height",
				type = BigDecimal.class)
	})
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long id;
	
	@Column(name = "name", length = 30, nullable = false, unique = true)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", nullable = false)
	private Date dataNascimento;
	
	@Column(name = "height", precision = 3, scale = 2)
	private BigDecimal altura;
	
	@Column(name = "personal_record", nullable = false, unique = true, insertable = true, updatable = false)
	private String registroPessoal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Date dataCriacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false, length = 1)
	private Sexo sexo;
	
	@Embedded
	private Endereco endereco;
	
	@Transient
	private String naoQueroSalvarEsseCampoNoBanco; 

	public Pessoa() {}

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public String getRegistroPessoal() {
		return registroPessoal;
	}

	public void setRegistroPessoal(String registroPessoal) {
		this.registroPessoal = registroPessoal;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getNaoQueroSalvarEsseCampoNoBanco() {
		return naoQueroSalvarEsseCampoNoBanco;
	}

	public void setNaoQueroSalvarEsseCampoNoBanco(String naoQueroSalvarEsseCampoNoBanco) {
		this.naoQueroSalvarEsseCampoNoBanco = naoQueroSalvarEsseCampoNoBanco;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", altura=" + altura
				+ ", registroPessoal=" + registroPessoal + ", dataCriacao=" + dataCriacao + ", sexo=" + sexo
				+ ", endereco=" + endereco + ", naoQueroSalvarEsseCampoNoBanco=" + naoQueroSalvarEsseCampoNoBanco + "]";
	}

}
