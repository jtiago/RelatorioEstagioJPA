package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="ALUNO")
public class Aluno implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idaluno")
	private Long id;
	
	@Column(name="nomealuno", length=100, nullable=false)
	private String nome;
	
	@Column(length=14, nullable=false)
	private String cpf;
	
	@Column(length=100, nullable=false)
	private String senha;
	
	@Column(length=20, nullable=false)
	private String matricula;
	
	@Column(length=1, nullable=true)
	private String sexo;
	
	@Column(length=15, nullable=true)
	private String status;
	
	@Column(length=50, nullable=true)
	private String email;
	
	@Column(name="nometurma", length=15, nullable=true)
	private String nomeTurma;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="fkperfil")
	private Perfil perfil;
	
	@OneToMany(mappedBy = "aluno")
	private List<Relatorio> relatorios;
	
	@Transient
	private Turma turmaT;
	@Transient
	private String modulo;
	@Transient
	private Boolean moduloLiberado;
	@Transient
	private String fichaRelatorio;
	@Transient
	private Relatorio relatorioR;
	@Transient
	private Semestre semestre;

	
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public Turma getTurmaT() {
		return turmaT == null ? turmaT = new Turma() : turmaT;
	}

	public void setTurmaT(Turma turmaT) {
		this.turmaT = turmaT;
	}
	
	public Perfil getPerfil() {
		return perfil == null ? perfil = new Perfil() : perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Boolean getModuloLiberado() {
		return moduloLiberado;
	}

	public void setModuloLiberado(Boolean moduloLiberado) {
		this.moduloLiberado = moduloLiberado;
	}

	public String getFichaRelatorio() {
		return fichaRelatorio;
	}

	public void setFichaRelatorio(String fichaRelatorio) {
		this.fichaRelatorio = fichaRelatorio;
	}

	public Relatorio getRelatorioR() {
		return relatorioR == null ? relatorioR = new Relatorio() : relatorioR;
	}

	public void setRelatorioR(Relatorio relatorioR) {
		this.relatorioR = relatorioR;
	}

	public Semestre getSemestre() {
		return semestre == null ? semestre = new Semestre() : semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
}
