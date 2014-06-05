package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.LiberarRelatorioController;
import br.com.clogos.estagio.model.LiberarRelatorio;

public class LiberarRelatorioFacade implements Serializable {
	private static final long serialVersionUID = -1656621873140148824L;
	private LiberarRelatorio liberarRelatorio;
	private GenericController genericController;
	private LiberarRelatorioController liberarRelatorioController;
	
	public void save() {
		try {
			if(!getLiberarRelatorioController().existeModuloLiberado(getLiberarRelatorio())) {
				getGenericController().save(getLiberarRelatorio());
				liberarRelatorio=null; genericController = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatorio Liberado para este Modulo com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, " Este Modulo já foi liberado para esta turma", ""));
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Liberação de Relatório.", ""));
		}
	}
	
	public ModuloEnum[] getModulo() {
		return ModuloEnum.values();
	}
	
	public LiberarRelatorio getLiberarRelatorio() {
		return liberarRelatorio == null ? liberarRelatorio = new LiberarRelatorio() : liberarRelatorio;
	}
	public void setLiberarRelatorio(LiberarRelatorio liberarRelatorio) {
		this.liberarRelatorio = liberarRelatorio;
	}
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	public LiberarRelatorioController getLiberarRelatorioController() {
		return liberarRelatorioController == null ? liberarRelatorioController = new LiberarRelatorioController() : liberarRelatorioController;
	}
}
