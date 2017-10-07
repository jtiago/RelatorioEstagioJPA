package br.com.clogos.estagio.vo;

import java.io.Serializable;
/**
 * 
 * @author Tiago
 *
 */
public class CampoEstagioFichaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245056796406185594L;
	private Long idCampoEstagio;
	private String nomeCampoEstagio;
	
	
	public Long getIdCampoEstagio() {
		return idCampoEstagio;
	}
	public void setIdCampoEstagio(Long idCampoEstagio) {
		this.idCampoEstagio = idCampoEstagio;
	}
	public String getNomeCampoEstagio() {
		return nomeCampoEstagio;
	}
	public void setNomeCampoEstagio(String nomeCampoEstagio) {
		this.nomeCampoEstagio = nomeCampoEstagio;
	}
}
