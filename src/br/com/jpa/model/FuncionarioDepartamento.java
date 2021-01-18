package br.com.jpa.model;

public class FuncionarioDepartamento {

	private String funcionarioNome;
	private String departamentoNome;
	
	public FuncionarioDepartamento(String funcionarioNome, String departamentoNome) {
		this.funcionarioNome = funcionarioNome;
		this.departamentoNome = departamentoNome;
	}

	public String getFuncionarioNome() {
		return funcionarioNome;
	}

	public void setFuncionarioNome(String funcionarioNome) {
		this.funcionarioNome = funcionarioNome;
	}

	public String getDepartamentoNome() {
		return departamentoNome;
	}

	public void setDepartamentoNome(String departamentoNome) {
		this.departamentoNome = departamentoNome;
	}

	@Override
	public String toString() {
		return "FuncionarioDepartamento [funcionarioNome=" + funcionarioNome + ", departamentoNome=" + departamentoNome
				+ "]";
	}

}
