package br.com.clogos.estagio.vo;

import java.io.Serializable;
import java.util.Date;

public class GrupoFichaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idGrupo;
	private String siglaCampoEstagio;
	private Date dataInicial;
	private Date dataFinal;
	private String relEnviado;
	
	public Long getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getSiglaCampoEstagio() {
		return siglaCampoEstagio;
	}
	public void setSiglaCampoEstagio(String siglaCampoEstagio) {
		this.siglaCampoEstagio = siglaCampoEstagio;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getRelEnviado() {
		return relEnviado;
	}
	public void setRelEnviado(String relEnviado) {
		this.relEnviado = relEnviado;
	}
	
}
