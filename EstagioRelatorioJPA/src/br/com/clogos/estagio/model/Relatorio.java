package br.com.clogos.estagio.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.enums.StatusEnum;
import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "RELATORIO")
public class Relatorio implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrelatorio")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(length=8000, nullable=false)
	private String texto;
	
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
	@JoinColumn(name = "fksupervisor")
	private Supervisor supervisor;
	
	@ManyToOne
	@JoinColumn(name = "fkturma")
	private Turma turmaRelatorio;
	
	@ManyToOne
	@JoinColumn(name = "fkgrupocampoestagio")
	private GrupoCampoEstagio grupoCampoEstagio;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status;
	
	@Transient
	private Long idSemestre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

	public Turma getTurmaRelatorio() {
		return turmaRelatorio == null ? turmaRelatorio = new Turma() : turmaRelatorio;
	}

	public void setTurmaRelatorio(Turma turmaRelatorio) {
		this.turmaRelatorio = turmaRelatorio;
	}

	public Long getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(Long idSemestre) {
		this.idSemestre = idSemestre;
	}

	public GrupoCampoEstagio getGrupoCampoEstagio() {
		return grupoCampoEstagio == null ? grupoCampoEstagio = new GrupoCampoEstagio() : grupoCampoEstagio;
	}

	public void setGrupoCampoEstagio(GrupoCampoEstagio grupoCampoEstagio) {
		this.grupoCampoEstagio = grupoCampoEstagio;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
}
