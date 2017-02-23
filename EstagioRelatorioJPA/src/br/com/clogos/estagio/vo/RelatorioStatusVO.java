package br.com.clogos.estagio.vo;

import java.io.Serializable;

public class RelatorioStatusVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nomeAluno;
	private String nomeTurma;
	private String nomeSemestre;
	private Integer qtdRelatorio;
	private String modulo;
	private Integer relEnviados;
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNomeAluno() {
		return nomeAluno;
	}
	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}
	public String getNomeTurma() {
		return nomeTurma;
	}
	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}
	public String getNomeSemestre() {
		return nomeSemestre;
	}
	public void setNomeSemestre(String nomeSemestre) {
		this.nomeSemestre = nomeSemestre;
	}
	public Integer getQtdRelatorio() {
		return qtdRelatorio;
	}
	public void setQtdRelatorio(Integer qtdRelatorio) {
		this.qtdRelatorio = qtdRelatorio;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public Integer getRelEnviados() {
		return relEnviados;
	}
	public void setRelEnviados(Integer relEnviados) {
		this.relEnviados = relEnviados;
	}
}
