package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "GRUPO")
public class Grupo implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgrupo")
	private Long id;
	
	@Column(length=100, nullable=false)
	private String nomeGrupo;
	
	@ManyToMany
	@JoinTable(name="grupo_aluno")
	private List<Aluno> alunosGrupo;
	
	@ManyToMany
	@JoinTable(name="grupo_campo")
	private List<CampoEstagio> camposGrupo;
	
	@ManyToOne
	@JoinColumn(name = "fkturma")
	private Turma turmaGrupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public List<Aluno> getAlunosGrupo() {
		return alunosGrupo;
	}

	public void setAlunosGrupo(List<Aluno> alunosGrupo) {
		this.alunosGrupo = alunosGrupo;
	}
}