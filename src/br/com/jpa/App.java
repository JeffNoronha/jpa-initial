package br.com.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.jpa.model.Aluno;
import br.com.jpa.model.Autor;
import br.com.jpa.model.Bateria;
import br.com.jpa.model.Carro;
import br.com.jpa.model.Curso;
import br.com.jpa.model.Departamento;
import br.com.jpa.model.Dependente;
import br.com.jpa.model.DependenteId;
import br.com.jpa.model.Disciplina;
import br.com.jpa.model.Disciplina.DiaDaSemana;
import br.com.jpa.model.Endereco;
import br.com.jpa.model.Estado;
import br.com.jpa.model.Funcionario;
import br.com.jpa.model.FuncionarioDepartamento;
import br.com.jpa.model.Governador;
import br.com.jpa.model.Guitarra;
import br.com.jpa.model.Instituicao;
import br.com.jpa.model.Livro;
import br.com.jpa.model.Moto;
import br.com.jpa.model.PagamentoAprazo;
import br.com.jpa.model.PagamentoAvista;
import br.com.jpa.model.Pessoa;
import br.com.jpa.model.Professor;
import br.com.jpa.model.Sexo;

public class App {

	static EntityManager entityManager;
	
	public static void main(String[] args) {

		initSecondEntities();
		createEmployeeWithDependentMapsId();
	}

	@SuppressWarnings("unchecked")
	public static void findAllTeachers() {
		
		buildEntityManager();
		
		System.out.println("\nLista de Professores:\n");
		
		Query q = entityManager.createQuery("SELECT p FROM Professor p ORDER BY p.nome");
		List<Professor> list = q.getResultList();
		
		for (Professor professor : list) {
			System.out.println(professor);
		}
		
		System.out.println("\nTotal: " + list.size());
		System.out.println("-------------------------------------\n");
		
		closeEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public static void findAllStudentsAndSubjects() {
		
		buildEntityManager();
		
		System.out.println("Lista de Alunos e Disciplinas:");
		
		Query q = entityManager.createQuery("SELECT a FROM Aluno a");
		List<Aluno> alunos = q.getResultList();
		
		System.out.println("RA\t\tAluno\t\tDisciplinas");
		
		for (Aluno aluno : alunos) {
			System.out.print(aluno.getMatricula());
			System.out.print("\t" + aluno.getNome() + "\t\t");
			for (Disciplina materia : aluno.getDisciplinas()) {
				System.out.print(materia.getSigla() + " ");
			}
			System.out.println();
		}
		
		System.out.println("Total de alunos: " + alunos.size());
		System.out.println("-------------------------------------\n");
		
		closeEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static void findAllStudentsSpecificTeacher() {
		
		buildEntityManager();
		
		System.out.println("Lista de Alunos do Prof. Cesar Lattes:");
		
		Query q = entityManager.createQuery("SELECT DISTINCT a " + " FROM Aluno a JOIN a.disciplinas d " + " WHERE d.professor.nome = :prof");
		q.setParameter("prof", "Cesar Lattes");
		List<Aluno> lista1 = q.getResultList();
		
		for (Aluno aluno : lista1) {
			System.out.println(aluno);
		}
		
		System.out.println("Total de alunos: " + lista1.size());
		System.out.println("-------------------------------------\n");
	
		closeEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public static void findTeachersDoClassWednesdayMorning() {
		
		buildEntityManager();
		
		System.out.println("Lista de Professores que dão aula quarta-feira pela manhã:");
		Query q = entityManager.createQuery("SELECT d FROM Disciplina d " + " WHERE d.diaDaSemana = ?1 AND d.horaInicio >= ?2 AND d.horaFim <= ?3");
		q.setParameter(1, Disciplina.DiaDaSemana.QUARTA);
		q.setParameter(2, 8);
		q.setParameter(3, 12);

		List<Disciplina> lista2 = q.getResultList();
		
		System.out.println("Professor\t\tDia\tHorario");
		for (Disciplina d : lista2) {
			String linha = String.format("%s\t\t%s\t%02dh-%02dh", d.getProfessor().getNome(), d.getDiaDaSemana(),
					d.getHoraInicio(), d.getHoraFim());
			System.out.println(linha);
		}
		
		closeEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public static void findAllStudentsUsingNamedQueries() {
		
		buildEntityManager();
		
		Query query = entityManager.createNamedQuery("Aluno.findAll");
		List<Aluno> alunos = query.getResultList();
		
		System.out.println(alunos);
		
		closeEntityManager();
	}
	
	public static void initDB() {
		
		buildEntityManager();
		
		entityManager.getTransaction().begin();

		Professor professor1 = new Professor(1, "Bill Gates", "Computação");
		Professor professor2 = new Professor(2, "Oswald de Souza", "Matemática");
		Professor professor3 = new Professor(3, "Cesar Lattes", "Física");
		
		entityManager.persist(professor1);
		entityManager.persist(professor2);
		entityManager.persist(professor3);

		Instituicao instituicao = new Instituicao(1, "MySchool", Arrays.asList(professor1, professor2, professor3));
		entityManager.persist(instituicao);
		
		Disciplina disciplina1 = new Disciplina("MA-100", "Cálculo 1", DiaDaSemana.SEGUNDA, 8, 10, professor2);
		Disciplina disciplina2 = new Disciplina("MA-100", "Cálculo 1", DiaDaSemana.QUARTA, 8, 10, professor2);
		Disciplina disciplina3 = new Disciplina("CC-100", "Algoritmos", DiaDaSemana.QUARTA, 10, 12, professor1);
		Disciplina disciplina4 = new Disciplina("F-100", "Física 1", DiaDaSemana.QUINTA, 14, 16, professor3);
		
		entityManager.persist(disciplina1);
		entityManager.persist(disciplina2);
		entityManager.persist(disciplina3);
		entityManager.persist(disciplina4);

		HashSet<Disciplina> grade1 = new HashSet<Disciplina>();
		grade1.add(disciplina2);
		grade1.add(disciplina3);
		grade1.add(disciplina4);
		
		HashSet<Disciplina> grade2 = new HashSet<Disciplina>();
		grade2.add(disciplina1);
		grade2.add(disciplina2);
		grade2.add(disciplina3);
		
		Curso curso = new Curso(1, "Computação");
		entityManager.persist(curso);
		
		Endereco endereco1 = new Endereco("Rua Qualquer 558", "São Paulo", "SP", "01897000");
		Endereco endereco2 = new Endereco("Rua XYZ 999", "São Paulo", "SP", "08565840");
		Endereco endereco3 = new Endereco("Rua de Cima 1897", "São Paulo", "SP", "84564130");
		Endereco endereco4 = new Endereco("Av Principal 4852", "São Paulo", "SP", "654843220");
		
		Aluno aluno1 = new Aluno(20130001, "Adriano", curso, grade1, endereco1);
		Aluno aluno2 = new Aluno(20130002, "Felipe", curso, grade1, endereco2);
		Aluno aluno3 = new Aluno(20130003, "Mariana", curso, grade2, endereco3);
		Aluno aluno4 = new Aluno(20130004, "Rodrigo", curso, grade2, endereco4);
		
		entityManager.persist(aluno1);
		entityManager.persist(aluno2);
		entityManager.persist(aluno3);
		entityManager.persist(aluno4);

		entityManager.getTransaction().commit();
		closeEntityManager();
	}
	
	public static void initSecondEntities() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("First Person");
		pessoa.setDataNascimento(new Date());
		pessoa.setAltura(new BigDecimal(1.78));
		pessoa.setRegistroPessoal("5584951463");
		pessoa.setSexo(Sexo.M);
		
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setNome("Second Person");
		pessoa2.setDataNascimento(new Date());
		pessoa2.setAltura(new BigDecimal(1.62));
		pessoa2.setRegistroPessoal("3324951463");
		pessoa2.setSexo(Sexo.F);
		
		entityManager.persist(pessoa);
		entityManager.persist(pessoa2);

		Departamento dept1 = new Departamento();
		dept1.setNome("RH");
		
		Departamento dept2 = new Departamento();
		dept2.setNome("Contabil");
		
		Funcionario func1 = new Funcionario();
		func1.setNome("Paul Coby");
		func1.setDepartamento(dept1);
		func1.setDataNascimento(LocalDate.of(1998, 11, 5));
		
		Funcionario func2 = new Funcionario();
		func2.setNome("Mary Marie");
		func2.setDepartamento(dept1);
		func2.setDataNascimento(LocalDate.of(1998, 11, 5));
		
		Funcionario func3 = new Funcionario();
		func3.setNome("Clarie D.");
		func3.setDepartamento(dept2);
		func3.setDataNascimento(LocalDate.of(1998, 11, 5));
		
		Funcionario func4 = new Funcionario();
		func4.setNome("John Zanzy");
		func4.setDepartamento(dept2);
		func4.setDataNascimento(LocalDate.of(1998, 11, 5));
		
		dept1.setFuncionarios(List.of(func1,func2));
		dept2.setFuncionarios(List.of(func3,func4));
		
		entityManager.persist(dept1);
		entityManager.persist(dept2);
		
		//entityManager.persist(func1);
		//entityManager.persist(func2);
		//entityManager.persist(func3);
		//entityManager.persist(func4);
		
		Governador governador = new Governador();
		governador.setNome("Governador");
		entityManager.persist(governador);
		
		/*
		for (int i = 1; i <= 100; i++) {
			Estado estado = new Estado();
			estado.setNome("Estado" + i);
			entityManager.persist(estado);
		}*/
		
		Estado estado = new Estado();
		estado.setNome("Estado Governado");
		estado.setGovernador(governador);
		entityManager.persist(estado);
		
		String texto = "Resumo resumido";
		String resumo = texto.repeat(10);
		
		Livro livro1 = new Livro();
		livro1.setNome("Livro 0001");
		livro1.setResumo(resumo);
		
		Livro livro2 = new Livro();
		livro2.setNome("Livro 0002");
		livro2.setResumo(resumo);
		
		Livro livro3 = new Livro();
		livro3.setNome("Livro 0003");
		livro3.setResumo(resumo);
		
		Autor autor1 = new Autor();
		autor1.setNome("Autor 0001");
		autor1.setLivros(List.of(livro1, livro2));
		
		Autor autor2 = new Autor();
		autor2.setNome("Autor 0002");
		autor2.setLivros(List.of(livro2, livro3));
		
		Autor autor3 = new Autor();
		autor3.setNome("Autor 0003");
		autor3.setLivros(List.of(livro3));
		
		Autor autor4 = new Autor();
		autor4.setNome("Autor 0004");
		autor4.setLivros(List.of(livro1, livro2, livro3));
		
		Autor autor5 = new Autor();
		autor5.setNome("Autor 0005");
		
		livro1.setAutores(List.of(autor1,autor4));
		livro2.setAutores(List.of(autor1,autor2,autor4));
		livro3.setAutores(List.of(autor2,autor3,autor4));
		
		entityManager.persist(livro1);
		entityManager.persist(livro2);
		entityManager.persist(livro3);
		
		entityManager.persist(autor1);
		entityManager.persist(autor2);
		entityManager.persist(autor3);
		entityManager.persist(autor4);
		entityManager.persist(autor5);
		
		entityManager.getTransaction().commit();
		closeEntityManager();
	}
	
	public static void findFirstPersonAndTryUpdatePersonalRecord() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();
		
		Pessoa firstPerson = entityManager.find(Pessoa.class, 1L);
		firstPerson.setNome("First Person UPDT");
		firstPerson.setRegistroPessoal("54831232184");
		
		entityManager.persist(firstPerson);
		
		entityManager.getTransaction().commit();
		closeEntityManager();
	}
	
	public static void tryPersistNewPersonWithSpecificCreateAt() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();

		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Second Person");
		pessoa.setDataNascimento(new Date());
		pessoa.setAltura(new BigDecimal(1.55));
		pessoa.setRegistroPessoal("99985512177");
		pessoa.setDataCriacao(new Date());
		
		entityManager.persist(pessoa);
		
		entityManager.getTransaction().commit();
		closeEntityManager();
	}
	
	public static void savePaymentTypes() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();
		
		PagamentoAvista avista = new PagamentoAvista();
		avista.setNome("Pagamento com desconto de 5%");
		avista.setPercentualDesconto(new BigDecimal(0.05));
		
		PagamentoAprazo aprazo = new PagamentoAprazo();
		aprazo.setNome("Pagamento em 3 vezes");
		aprazo.setQtdParcelas(3);
		
		entityManager.persist(avista);
		entityManager.persist(aprazo);
		
		entityManager.getTransaction().commit();
		closeEntityManager();

	}
	
	public static void saveVehicles() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();
		
		Carro carro = new Carro();
		carro.setMarca("FIAT");
		carro.setNome("Uno");
		carro.setQtdPortas(2);
		
		Moto moto = new Moto();
		moto.setMarca("Yamaha");
		moto.setNome("XPTO");
		moto.setQtdCilindradas(250);
		
		entityManager.persist(carro);
		entityManager.persist(moto);
		
		entityManager.getTransaction().commit();
		closeEntityManager();

	}
	
	public static void saveInstruments() {
		
		buildEntityManager();
		entityManager.getTransaction().begin();
		
		Guitarra guitarra = new Guitarra();
		guitarra.setMarca("Gibson");
		guitarra.setNome("Les Paul");
		guitarra.setQtdCordas(6);
		
		Bateria bateria = new Bateria();
		bateria.setMarca("PEARL");
		bateria.setNome("HYZ 552");
		bateria.setQtdPratos(3);
		bateria.setQtdTons(4);
		
		entityManager.persist(guitarra);
		entityManager.persist(bateria);
		
		entityManager.getTransaction().commit();
		closeEntityManager();

	}
	
	@SuppressWarnings("unchecked")
	public static void findPersonByHeight() {
		
		buildEntityManager();
		
		Query q = entityManager.createNamedQuery("Pessoa.findByHeight");
		q.setParameter("altura", 1.70);
		
		List<Pessoa> pessoas = q.getResultList();
		
		System.out.println(pessoas);
	}
	
	@SuppressWarnings("unchecked")
	public static void findPersonByGender() {
		buildEntityManager();
		
		Query q = entityManager.createNamedQuery("Pessoa.findByGender");
		q.setParameter(1, Sexo.F);
		
		List<Pessoa> pessoas = q.getResultList();
		
		System.out.println(pessoas);
	}
	
	public static void findAllPersonTypedQuery() {
		
		buildEntityManager();
		
		TypedQuery<Pessoa> query = entityManager.createQuery(
				"SELECT p FROM Pessoa p", Pessoa.class);
		
		List<Pessoa> pessoas = query.getResultList();
		
		System.out.println(pessoas);
		
	}
	
	@SuppressWarnings("unchecked")
	public static void findEmployeeNameAndDepartmentNameByNewResultFactory() {
		
		buildEntityManager();
		
		String query = "SELECT NEW br.com.jpa.model.FuncionarioDepartamento ( "
				+ "f.nome , f.departamento.nome ) FROM Funcionario f";
		
		Query q = entityManager.createQuery(query);
		
		List <FuncionarioDepartamento> resultado = q.getResultList();
		
		resultado.forEach(System.out::println);

	}
	
	public static void findAllStatesWithPagination() {
		
		buildEntityManager();
		
		TypedQuery<Estado> query = entityManager.createQuery(
				"SELECT e FROM Estado e", Estado.class);
		
		query.setFirstResult(0);
		query.setMaxResults(20);
		
		List<Estado> estados = query.getResultList();
		
		estados.forEach(System.out::println);
		System.out.println("TOTAL: " + estados.size());
	}
	
	@SuppressWarnings("unchecked")
	public static void findAllBooks() {
		
		buildEntityManager();
		
		Query q = entityManager.createQuery("SELECT l FROM Livro l");
		List<Livro> livros =  q.getResultList();
		
		System.out.println(livros);
	}
	
	@SuppressWarnings("unchecked")
	public static void findAllDepartments() {
		
		buildEntityManager();
		
		Query q = entityManager.createQuery("SELECT d FROM Departamento d");
		List<Departamento> departamentos =  q.getResultList();
		
		System.out.println(departamentos);
	}
	
	@SuppressWarnings("unchecked")
	public static void findStateWithGovernator() {
		
		buildEntityManager();
		
		Query q = entityManager.createQuery("SELECT e FROM Estado e");
		List<Estado> estados = q.getResultList();
		
		System.out.println(estados);
	}
	
	public static void criteriaFindAllPerson() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(criteriaRoot);
		
		TypedQuery<Pessoa> query = entityManager.createQuery(criteriaQuery);
		List<Pessoa> pessoas = query.getResultList();
		
		System.out.println(pessoas);
		
		closeEntityManager();
	}
	
	public static void criteriaFindPersonNames() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(criteriaRoot.get("nome"));
		
		TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		List<String> nomes = query.getResultList();
		System.out.println(nomes);
		
		closeEntityManager();
		
	}
	
	public static void criteriaFindAverageHeightPersons() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.select(criteriaBuilder.avg(criteriaRoot.get("altura")));
		
		TypedQuery<Double> query = entityManager.createQuery(criteriaQuery);
		Double averageHeights = query.getSingleResult();
		System.out.println(averageHeights);
		
		closeEntityManager();
	}
	
	public static void criteriaFindNamePersonalRecordPerson() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.multiselect(criteriaRoot.get("nome"), criteriaRoot.get("registroPessoal"));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> resultado = query.getResultList();
		
		for (Object[] registro : resultado) {
			System.out.println(registro[0]);
			System.out.println(registro[1]);
		}
		
		closeEntityManager();
	}
	
	public static void criteriaFindNamePersonalRecordPersonTuple() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
			
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		
		criteriaQuery.multiselect(criteriaRoot.get("nome").alias("pessoa.nome"),
				criteriaRoot.get("registroPessoal").alias("pessoa.registroPessoal"));
		
		TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
		List<Tuple> resultado = query.getResultList();
		
		for (Tuple registro : resultado) {
			System.out.println(registro.get("pessoa.nome"));
			System.out.println(registro.get("pessoa.registroPessoal"));
		}
		
		closeEntityManager();		
	}
	
	public static void criteriaFindPersonByPersonalRegister() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
		
		Root<Pessoa> criteriaRoot = criteriaQuery.from(Pessoa.class);
		criteriaQuery.select(criteriaRoot);
		
		Predicate predicate = criteriaBuilder.equal(criteriaRoot.get("registroPessoal"), "5584951463");
		criteriaQuery.where(predicate);
		
		TypedQuery<Pessoa> query = entityManager.createQuery(criteriaQuery);
		List<Pessoa> pessoas = query.getResultList();
		
		System.out.println(pessoas);
		
		closeEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public static void callProcedureNamed() {
		
		buildEntityManager();
		
		StoredProcedureQuery procedure = entityManager.createNamedStoredProcedureQuery("SpFindPerson");
		procedure.setParameter("height", 1.78);
		
		List<Pessoa> pessoas = procedure.getResultList();
		
		System.out.println(pessoas);
		
		closeEntityManager();
		
	}
	
	@SuppressWarnings("unchecked")
	public static void callProcedure() {
		
		buildEntityManager();
		
		StoredProcedureQuery procedure = entityManager.createStoredProcedureQuery("sp_find_person");
		procedure.registerStoredProcedureParameter("height", BigDecimal.class, ParameterMode.IN);
		
		procedure.setParameter("height", 1.78);
		
		boolean hasResult = procedure.execute();
		
		if(hasResult) {
			List<Object[]> resultado = procedure.getResultList();
			
			for (Object[] registro : resultado) {
				System.out.println(registro[0]);
				System.out.println(registro[1]);
			}
		}
		
		closeEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public static void joinFetchSchool() {
		
		buildEntityManager();
		
		Query query = entityManager.createQuery("SELECT DISTINCT (i) FROM Instituicao i LEFT JOIN FETCH i.professores");
		List<Instituicao> instituicoes = query.getResultList();
		
		System.out.println(instituicoes);
		
		instituicoes.forEach(inst -> 
			inst.getProfessores()
				.forEach(System.out::println));
		
		closeEntityManager();
	}
	
	public static void criteriaJoinSchoolDepartments() {
		
		buildEntityManager();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Instituicao> criteriaQuery = criteriaBuilder.createQuery(Instituicao.class);
		Root<Instituicao> criteriaRoot = criteriaQuery.from(Instituicao.class);
		
		criteriaRoot.fetch("professores", JoinType.LEFT);
		criteriaQuery.select(criteriaRoot).distinct(true);
		
		TypedQuery<Instituicao> query = entityManager.createQuery(criteriaQuery);
		List<Instituicao> instituicoes = query.getResultList();
		
		System.out.println(instituicoes);
		
		closeEntityManager();
	}
	
	public static void createEmployeeWithDependentMapsId() {
		
		buildEntityManager();
		
		entityManager.getTransaction().begin();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Joseph Albert");
		funcionario.setDataNascimento(LocalDate.of(1962, 5, 11));
		
		DependenteId id = new DependenteId();
		id.setNome("Susan Albert");
		
		Dependente dependente = new Dependente();
		dependente.setDependenteId(id);
		dependente.setFuncionario(funcionario);

		entityManager.persist(funcionario);
		entityManager.persist(dependente);
		
		entityManager.getTransaction().commit();
		
		closeEntityManager();
	}
	
	public static void buildEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
		entityManager = emf.createEntityManager();
	}
	
	public static void closeEntityManager() {
		entityManager.close();
	}
}
