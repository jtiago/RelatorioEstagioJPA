package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.Turma;

public class TurmaFacade implements Serializable {
	private static final long serialVersionUID = -6917985296563545333L;
	private Turma turma;
	private Turma turmaAltera;
	private List<Turma> listaTurma;
	private List<Turma> listaTurmaFilter;
	private GenericController genericController;
	private TurmaController turmaController;
	
	public List<Turma> getListaTurma() {
		if(listaTurma == null){ 
			listaTurma = getTurmaController().findAll();
		}
		return listaTurma;
	}
	
	public void save() {
		if(getTurmaController().verificaDuplicidade(getTurma().getNome())) {
			try {
				getGenericController().save(getTurma());
				turma=null; genericController = null; listaTurma = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Turma salvo com suceso.", ""));
			} catch (PersistenceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Turma.", ""));
			}
		} else {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
//					FacesMessage.SEVERITY_WARN, "Turma j� existente.", ""));
		}
	}
	
	public void remover() {
		try {
			if(turma != null) {
				getGenericController().remove(getTurma());
				turma = null; genericController = null; listaTurma = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Turma removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Turma.", ""));
		}
	}
	
	public void update() {
		try {
			if(turmaAltera != null) {
				getGenericController().update(getTurmaAltera());
				turmaAltera = null; genericController = null; listaTurma = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Turma alterado com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Turma.", ""));
		}
	}
	
	public String getCurso(String nomeTurma) {
		return getTurmaController().obterCurso(nomeTurma).getNomeCurso();
	}
	
	public Turma getTurma() {
		return turma == null ? turma = new Turma() : turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public Turma getTurmaAltera() {
		return turmaAltera == null ? turmaAltera = new Turma() : turmaAltera;
	}
	public void setTurmaAltera(Turma turmaAltera) {
		this.turmaAltera = turmaAltera;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	public TurmaController getTurmaController() {
		return turmaController == null ? turmaController = new TurmaController() : turmaController;
	}

	public List<Turma> getListaTurmaFilter() {
		return listaTurmaFilter;
	}

	public void setListaTurmaFilter(List<Turma> listaTurmaFilter) {
		this.listaTurmaFilter = listaTurmaFilter;
	}
}
