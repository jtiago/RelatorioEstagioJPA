package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "TURMA")
public class Turma implements ObjectModel {
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
	
	@OneToMany(mappedBy = "turmaLiberarRelatorio")
	private List<LiberarRelatorio> liberarRelatorios;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fksemestre", nullable=false)
	private Semestre semestre;
	
	@OneToMany(mappedBy = "turmaRelatorio")
	private List<Relatorio> Relatorios;
	
	@ManyToMany(mappedBy = "turmas")
	private List<Aluno> alunos;
	
	@OneToMany(mappedBy="turmaGrupo")
	private List<Grupo> listaGrupos;
	
	@Transient
	private LiberarRelatorio liberar;

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

	public LiberarRelatorio getLiberar() {
		return liberar == null ? liberar = new LiberarRelatorio() : liberar;
	}

	public void setLiberar(LiberarRelatorio liberar) {
		this.liberar = liberar;
	}

	public List<LiberarRelatorio> getLiberarRelatorios() {
		return liberarRelatorios;
	}

	public void setLiberarRelatorios(List<LiberarRelatorio> liberarRelatorios) {
		this.liberarRelatorios = liberarRelatorios;
	}

	public Semestre getSemestre() {
		return semestre == null ? semestre = new Semestre() : semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public List<Relatorio> getRelatorios() {
		return Relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios) {
		Relatorios = relatorios;
	}

	public List<Aluno> getO() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
}
