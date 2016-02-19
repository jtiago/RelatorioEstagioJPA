package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="ALUNO", schema="uniweb")
public class Aluno implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idaluno")
	private Long id;
	
	@Column(name="nomealuno", length=100, nullable=false)
	private String nome;
	
	@Column(length=14, nullable=false, unique=true)
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
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="fkperfil", referencedColumnName="idperfil")
	private Perfil perfil;
	
	@OneToMany(mappedBy = "aluno")
	private List<Relatorio> relatorios;
	
	@ManyToMany
	@JoinTable(name = "turma_aluno")
	private List<Turma> turmas; 
	
	@ManyToMany(mappedBy="alunosGrupo")
	private List<Grupo> grupos;
	
	@Transient
	private Turma turmaT;
	@Transient
	private Semestre semestre;
	
	private String status1;
	private String status2;
	private String status3;
	private String status4;
	private String status5;
	private String status6;
	
//	@Transient
//	private String modulo;
//	@Transient
//	private Boolean moduloLiberado;
//	@Transient
//	private String fichaRelatorio;
//	@Transient
//	private Relatorio relatorioR;
//	@Transient
//	private Boolean limiteRelatorio;
	
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

	public Semestre getSemestre() {
		return semestre == null ? semestre = new Semestre() : semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}

	public String getStatus4() {
		return status4;
	}

	public void setStatus4(String status4) {
		this.status4 = status4;
	}

	public String getStatus5() {
		return status5;
	}

	public void setStatus5(String status5) {
		this.status5 = status5;
	}

	public String getStatus6() {
		return status6;
	}

	public void setStatus6(String status6) {
		this.status6 = status6;
	}
}
