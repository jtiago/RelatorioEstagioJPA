package br.com.clogos.estagio.vo;

import java.io.Serializable;

import br.com.clogos.estagio.enums.ModuloEnum;

public class AlunoFichaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idAluno;
	private String cpf;
	private String nomeAluno;
	private String nomeTurma;
	private String nomeCurso;
	private ModuloEnum modulo;
	private String nomeGrupo;
	private String semestre;
	private Integer qtdRelatorio;
	private Integer qtdRelatorioEnviado;
	private String cargaHoraria;
	
	public AlunoFichaVO() {}
	
	public AlunoFichaVO(Long idAluno, String cpf, String nomeAluno, String nomeTurma, String nomeGrupo, String semestre,
			Integer qtdRelatorio, Integer qtdRelatorioEnviado) {
		super();
		this.idAluno = idAluno;
		this.cpf = cpf;
		this.nomeAluno = nomeAluno;
		this.nomeTurma = nomeTurma;
		this.nomeGrupo = nomeGrupo;
		this.semestre = semestre;
		this.qtdRelatorio = qtdRelatorio;
		this.qtdRelatorioEnviado = qtdRelatorioEnviado;
	}

	public Long getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}
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
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public Integer getQtdRelatorio() {
		return qtdRelatorio;
	}
	public void setQtdRelatorio(Integer qtdRelatorio) {
		this.qtdRelatorio = qtdRelatorio;
	}
	public Integer getQtdRelatorioEnviado() {
		return qtdRelatorioEnviado;
	}
	public void setQtdRelatorioEnviado(Integer qtdRelatorioEnviado) {
		this.qtdRelatorioEnviado = qtdRelatorioEnviado;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public ModuloEnum getModulo() {
		return modulo;
	}

	public void setModulo(ModuloEnum modulo) {
		this.modulo = modulo;
	}
}
