package br.com.clogos.estagio.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.clogos.estagio.enums.ModuloEnum;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(length=4000, nullable=false)
	private String texto;
	
	private Boolean validado;
	
	private Boolean confirmado;
	
	private Boolean revisao;
	
	@Enumerated(EnumType.STRING)
	private ModuloEnum modulo;
	
	private String observacao;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "fkcampoestagio")
	private CampoEstagio campoEstagio;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "fkaluno")
	private Aluno aluno;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name = "fksupervisor", referencedColumnName="idsupervisor" )
	private Supervisor supervisor;
	
	@ManyToOne(cascade=CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name="fksemestre")
	private Semestre semestre;

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
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public ModuloEnum getModulo() {
		return modulo;
	}

	public void setModulo(ModuloEnum modulo) {
		this.modulo = modulo;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public Supervisor getSupervisor() {
		return supervisor == null ? supervisor = new Supervisor() : supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public Semestre getSemestre() {
		return semestre == null ? semestre = new Semestre() : semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
}
