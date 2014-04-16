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
@Table(name = "TURMA")
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idturma")
	private Long id;
	
	@Column(name="nometurma", length=15, nullable=false)
	private String nome;
	
	@Column(length=30, nullable=true)
	private String nomeCurso;
	
	@Column(length=15, nullable=true)
	private String turno;
	
	@OneToMany(mappedBy = "turma")
	private List<Aluno> alunos;
	
	@OneToMany(mappedBy = "turmaLiberarRelatorio")
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

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}
