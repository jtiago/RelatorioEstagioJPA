package br.com.clogos.estagio.vo;

import java.io.Serializable;
import java.util.List;

public class FichaAvaliacaoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AlunoFichaVO alunoFichaVO;
	private List<GrupoFichaVO> listaGrupoCampo;
	private List<CampoEstagioFichaVO> listaCampoEstagio;
	
	public AlunoFichaVO getAlunoFichaVO() {
		return alunoFichaVO;
	}
	public void setAlunoFichaVO(AlunoFichaVO alunoFichaVO) {
		this.alunoFichaVO = alunoFichaVO;
	}
	public List<GrupoFichaVO> getListaGrupoCampo() {
		return listaGrupoCampo;
	}
	public void setListaGrupoCampo(List<GrupoFichaVO> listaGrupoCampo) {
		this.listaGrupoCampo = listaGrupoCampo;
	}
	public List<CampoEstagioFichaVO> getListaCampoEstagio() {
		return listaCampoEstagio;
	}
	public void setListaCampoEstagio(List<CampoEstagioFichaVO> listaCampoEstagio) {
		this.listaCampoEstagio = listaCampoEstagio;
	}
}