package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "TURMA")
public class Turma implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	/*@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idturma")
	private Long id;*/
	@EmbeddedId
	private TurmaPK pk;
	
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

	/*public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/
	
	public String getNome() {
		return nome;
	}

	public TurmaPK getPk() {
		return pk;
	}

	public void setPk(TurmaPK pk) {
		this.pk = pk;
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
