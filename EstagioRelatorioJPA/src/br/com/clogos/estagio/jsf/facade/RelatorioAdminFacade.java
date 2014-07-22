package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Relatorio;

public class RelatorioAdminFacade implements Serializable {
	private static final long serialVersionUID = 8659107450710545395L;
	private Relatorio relatorio;
	private Relatorio relatorioValidar;
	private List<Relatorio> listaRelatorios;
	private List<Relatorio> listaRelatoriosFilter;
	private RelatorioController relatorioController;
	
	public List<Relatorio> getListaRelatorios() {
		return listaRelatorios;
	}
	
	public void pesquisaRelatorio() {
		if(getRelatorio().getModulo() != null) {
			listaRelatorios = getRelatorioController().findRelatoriosAdmin(getRelatorio());
		}
	}
	
	public void validarRelatorio() {
		if(getRelatorioValidar() != null) {
			if(getRelatorioController().updateValidarRelatorio(getRelatorioValidar().getId(), getRelatorioValidar().getObservacao())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatório validado com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao validar relatório", ""));
			}
		}
		listaRelatorios=null;relatorioController=null;relatorioValidar=null;
	}
	
	public Relatorio getRelatorio() {
		return relatorio == null ? relatorio = new Relatorio() : relatorio;
	}
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	public Relatorio getRelatorioValidar() {
		return relatorioValidar == null ? relatorioValidar = new Relatorio() : relatorioValidar;
	}

	public void setRelatorioValidar(Relatorio relatorioValidar) {
		this.relatorioValidar = relatorioValidar;
	}

	public List<Relatorio> getListaRelatoriosFilter() {
		return listaRelatoriosFilter;
	}

	public void setListaRelatoriosFilter(List<Relatorio> listaRelatoriosFilter) {
		this.listaRelatoriosFilter = listaRelatoriosFilter;
	}

	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}
}
