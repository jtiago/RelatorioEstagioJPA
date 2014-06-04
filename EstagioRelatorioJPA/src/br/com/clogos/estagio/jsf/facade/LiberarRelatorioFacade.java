package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.LiberarRelatorio;
import br.com.clogos.estagio.model.Turma;

public class LiberarRelatorioFacade implements Serializable {
	private static final long serialVersionUID = -1656621873140148824L;
	private LiberarRelatorio liberarRelatorio;
	private Turma turma;
	private GenericController genericController;
	
	public void save() {
		try {
			getLiberarRelatorio().setTurmaLiberarRelatorio(getTurma());
			getGenericController().save(getLiberarRelatorio());
			liberarRelatorio=null; genericController = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Liberação de Relatório salvo com suceso.", ""));
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
	public Turma getTurma() {
		return turma == null ? turma = new Turma() : turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}
