package br.com.clogos.estagio.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "RELATORIO")
public class Relatorio implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrelatorio")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataTerminio;
	
	@Column(length=4000, nullable=false)
	private String texto;
	
	private Boolean validado;
	
	private Boolean confirmado;
	
	private Boolean revisao;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "fkcampoestagio")
	private CampoEstagio campoEstagio;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "fkaluno")
	private Aluno aluno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTerminio() {
		return dataTerminio;
	}

	public void setDataTerminio(Date dataTerminio) {
		this.dataTerminio = dataTerminio;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getValidado() {
		return validado;
	}

	public void setValidado(Boolean validado) {
		this.validado = validado;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Boolean getRevisao() {
		return revisao;
	}

	public void setRevisao(Boolean revisao) {
		this.revisao = revisao;
	}

	public CampoEstagio getCampoEstagio() {
		return campoEstagio == null ? campoEstagio = new CampoEstagio() : campoEstagio;
	}

	public void setCampoEstagio(CampoEstagio campoEstagio) {
		this.campoEstagio = campoEstagio;
	}

	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
