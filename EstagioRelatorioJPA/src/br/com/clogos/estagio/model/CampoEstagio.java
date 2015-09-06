package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="CAMPOESTAGIO")
public class CampoEstagio implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcampoestagio")
	private Long id;
	
	@Column(name="nomecampoestagio", length=100, nullable=false)
	private String nome;
	
	@Column(name="bolSituacao", length=1, nullable=false)
	private Boolean bolSituacao;
	
	@OneToMany(mappedBy = "campoEstagio")
	private List<Relatorio> relatorios;
	
//	@ManyToMany(mappedBy="camposGrupo")
//	private List<Grupo> campogrupos;
	
	@OneToMany(mappedBy="campoEstagio")
	private List<Supervisor> supervisores;
	
	@OneToMany(mappedBy="campoEstagio")
	private List<GrupoCampoEstagio> listaGrupoCampoEstagio;

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

	public Boolean getBolSituacao() {
		return bolSituacao;
	}

	public void setBolSituacao(Boolean bolSituacao) {
		this.bolSituacao = bolSituacao;
	}
}
