package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.util.Util;

public class RevisaoRelatorioFacade implements Serializable {
	private static final long serialVersionUID = -1643920823073800053L;
	private Relatorio relatorioRevisao;
	private List<Relatorio> listaRevisao;
	private List<Relatorio> listaRevisaoFilter;
	private RelatorioController relatorioController;
	private GenericController genericController;
	
	public void populaListaRevisao() {
		listaRevisao = getRelatorioController().findRelatoriosRevisao(Util.getAlunoSessao());
	}
	
	public void saveRevisao() {
		if(relatorioRevisao != null) {
			getRelatorioRevisao().setRevisao(false);
			if(getGenericController().update(getRelatorioRevisao())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatório salvo e enviando para validação com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao salvar e enviar relatório para validação.", ""));
			}
			relatorioRevisao=null; listaRevisao=null; relatorioController=null;
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
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}
