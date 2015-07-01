package br.com.clogos.estagio.model;

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

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name = "LIBERARRELATORIO")
public class LiberarRelatorio implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idliberar")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ModuloEnum modulo;
	
	@Column(name="aberto", length=1, nullable=false)
	private Boolean aberto = true;
	
	@Column(name="qtdRelatorio")
	private Integer qtdRelatorio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fkturma")
	private Turma turmaLiberarRelatorio;
	
	public Long getId() {	
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModuloEnum getModulo() {
		return modulo;
	}

	public void setModulo(ModuloEnum modulo) {
		this.modulo = modulo;
	}

	public Turma getTurmaLiberarRelatorio() {
		return turmaLiberarRelatorio == null ? turmaLiberarRelatorio = new Turma() : turmaLiberarRelatorio;
	}

	public void setTurmaLiberarRelatorio(Turma turmaLiberarRelatorio) {
		this.turmaLiberarRelatorio = turmaLiberarRelatorio;
	}

	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	public Integer getQtdRelatorio() {
		return qtdRelatorio;
	}

	public void setQtdRelatorio(Integer qtdRelatorio) {
		this.qtdRelatorio = qtdRelatorio;
	}
}
