package br.com.jpa.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@NamedQueries({
	@NamedQuery ( name ="Aluno.findAll", query =" SELECT a FROM Aluno a"),
	@NamedQuery ( name ="Aluno.count", query =" SELECT COUNT (a) FROM Aluno a")
})
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int matricula;
	
	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String nome;
	
	@OneToOne
	@JoinColumn(name = "course_id")
	private Curso curso;

	@ManyToMany //  um Aluno pode estar em muitas Disciplinas e uma Disciplina pode possuir muitos Alunos.
	private Set<Disciplina> disciplinas;

	@Embedded
	private Endereco endereco;
	
	public Aluno() {}

	public Aluno(int matricula, String nome, Curso curso, Set<Disciplina> disciplinas, Endereco endereco) {
		this.matricula = matricula;
		this.nome = nome;
		this.curso = curso;
		this.disciplinas = disciplinas;
		this.endereco = endereco;
	}
	
	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", nome=" + nome + ", curso=" + curso + ", disciplinas=" + disciplinas
				+ ", endereco=" + endereco + "]";
	}

}
