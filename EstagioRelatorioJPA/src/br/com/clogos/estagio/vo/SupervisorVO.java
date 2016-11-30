package br.com.clogos.estagio.vo;

import java.io.Serializable;

public class SupervisorVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idSupervisor; 
	private String cpfSupervisor;
	private String nomeSupervisor; 
	private String nomeCampoEstagio; 
	private String siglaCampoEstagio; 
	private Integer qtdValidado;
	private Integer qtdAberto;
	private Integer qtdRevisao;
	private String situacao;
	public Long getIdSupervisor() {
		return idSupervisor;
	}
	public void setIdSupervisor(Long idSupervisor) {
		this.idSupervisor = idSupervisor;
	}
	public String getCpfSupervisor() {
		return cpfSupervisor;
	}
	public void setCpfSupervisor(String cpfSupervisor) {
		this.cpfSupervisor = cpfSupervisor;
	}
	public String getNomeSupervisor() {
		return nomeSupervisor;
	}
	public void setNomeSupervisor(String nomeSupervisor) {
		this.nomeSupervisor = nomeSupervisor;
	}
	public String getNomeCampoEstagio() {
		return nomeCampoEstagio;
	}
	public void setNomeCampoEstagio(String nomeCampoEstagio) {
		this.nomeCampoEstagio = nomeCampoEstagio;
	}
	public String getSiglaCampoEstagio() {
		return siglaCampoEstagio;
	}
	public void setSiglaCampoEstagio(String siglaCampoEstagio) {
		this.siglaCampoEstagio = siglaCampoEstagio;
	}
	public Integer getQtdValidado() {
		return qtdValidado;
	}
	public void setQtdValidado(Integer qtdValidado) {
		this.qtdValidado = qtdValidado;
	}
	public Integer getQtdAberto() {
		return qtdAberto;
	}
	public void setQtdAberto(Integer qtdAberto) {
		this.qtdAberto = qtdAberto;
	}
	public Integer getQtdRevisao() {
		return qtdRevisao;
	}
	public void setQtdRevisao(Integer qtdRevisao) {
		this.qtdRevisao = qtdRevisao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
