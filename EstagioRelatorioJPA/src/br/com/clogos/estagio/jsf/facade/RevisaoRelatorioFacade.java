package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;

public class RevisaoRelatorioFacade implements Serializable {
	private static final long serialVersionUID = -1643920823073800053L;
	private Relatorio relatorioRevisao;
	private List<Relatorio> listaRevisao;
	private List<Relatorio> listaRevisaoFilter;
	private RelatorioController relatorioController;
	
	public void populaListaRevisao(Aluno aluno) {
		if(aluno != null) {
			listaRevisao = getRelatorioController().findRelatoriosRevisao(aluno);
		}
	}
	
	public List<Relatorio> getListaRevisao() {
		return listaRevisao;
	}

	public List<Relatorio> getListaRevisaoFilter() {
		return listaRevisaoFilter;
	}

	public void setListaRevisaoFilter(List<Relatorio> listaRevisaoFilter) {
		this.listaRevisaoFilter = listaRevisaoFilter;
	}
	
	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}

	public Relatorio getRelatorioRevisao() {
		return relatorioRevisao == null ? relatorioRevisao = new Relatorio() : relatorioRevisao;
	}

	public void setRelatorioRevisao(Relatorio relatorioRevisao) {
		this.relatorioRevisao = relatorioRevisao;
	}
}
