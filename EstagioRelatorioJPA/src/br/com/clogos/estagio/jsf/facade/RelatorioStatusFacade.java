package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.util.Util;
import br.com.clogos.estagio.vo.RelatorioStatusVO;

public class RelatorioStatusFacade implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<RelatorioStatusVO> listaRelatorioStatus;
	private RelatorioController relatorioController;
	private Long idTurma;
	
	
	public List<RelatorioStatusVO> getListaRelatorioStatus() {
		return listaRelatorioStatus;
	}
	
	public void pesquisaRelatorio() {
		listaRelatorioStatus = getRelatorioController().findRelatorioStatus(getIdTurma(), Util.getIdSemestre());
	}
	
	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}

	public Long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}
}
