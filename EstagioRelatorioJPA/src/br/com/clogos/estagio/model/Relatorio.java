package br.com.clogos.estagio.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RELATORIO")
public class Relatorio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrelatorio")
	private Long id;
	
	private Date dataInicio;
	
	private Date dataTerminio;
	
	private Boolean validado;
	
	private Boolean confirmado;
	
	private Boolean revisao;

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
}
