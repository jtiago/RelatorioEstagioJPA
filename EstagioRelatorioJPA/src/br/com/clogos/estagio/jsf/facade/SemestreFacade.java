package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.Semestre;

public class SemestreFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Semestre semestre;
	private Semestre semestreAltera;
	private Semestre semestreBusca;
	private List<Semestre> listaSemestre;
	private GenericController genericController;
	
	@SuppressWarnings("unchecked")
	public List<Semestre> getListaSemestre() {
		if(listaSemestre == null) {
			listaSemestre = (List<Semestre>) getGenericController().findAll(Semestre.class, "nomeSemestre", "desc", "");
		}
		return listaSemestre;
	}
	
	public Semestre getSemestreBusca(Long id) {
		if(semestreBusca != null) {
			semestreBusca = (Semestre) getGenericController().findID(Semestre.class, "id", id);
		}
		
		return semestreBusca;
	}
	
	public void save() {
		try {
			getGenericController().save(getSemestre());
			semestre=null; genericController = null; listaSemestre = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Semestre salvo com suceso.", ""));
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao semestre Turma.", ""));
		}
	}
	
	public void remover() {
		try {
			if(semestre != null) {
				getGenericController().remove(getSemestre());
				semestre = null; genericController = null; listaSemestre = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Semestre removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Semestre.", ""));
		}
	}
	
	public void update() {
		try {
			if(semestreAltera != null) {
				getGenericController().update(getsemestreAltera());
				semestreAltera = null; genericController = null; listaSemestre = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Semestre alterado com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Semestre.", ""));
		}
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}

	public Semestre getSemestre() {
		return semestre == null ? semestre = new Semestre() : semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public Semestre getsemestreAltera() {
		return semestreAltera == null ? semestreAltera = new Semestre() : semestreAltera;
	}

	public void setsemestreAltera(Semestre semestreAltera) {
		this.semestreAltera = semestreAltera;
	}
}
