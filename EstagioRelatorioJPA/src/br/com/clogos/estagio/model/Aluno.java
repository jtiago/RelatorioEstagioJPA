package br.com.clogos.estagio.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ALUNO")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idaluno")
	private Long id;
	
	@Column(name="nomealuno", length=100, nullable=false)
	private String nome;
	
	@Column(name="cpfaluno", length=11, nullable=false)
	private String cpf;
	
	@Column(name="matriculaaluno", length=20, nullable=false)
	private String matricula;
	
	@Column(name="sexoaluno", length=1, nullable=true)
	private String sexo;
	
	@Column(name="turmaaluno", length=15, nullable=false, unique = false)
	private String turma;
	
	@Column(name="cursoaluno", length=30, nullable=true)
	private String nomeCurso;
	
	@Column(name="turnoaluno", length=15, nullable=true)
	private String turno;
	
	@Column(name="statusaluno", length=15, nullable=true)
	private String status;
	
	@Column(name="emailaluno", length=50, nullable=false)
	private String email;
	
	@OneToMany(mappedBy = "aluno")
	private List<Relatorio> relatorios;
	
	@OneToMany(mappedBy = "turma")
	private List<LiberarRelatorio> liberarRelatorios;

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

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
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
}
