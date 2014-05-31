package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.controller.CampoEstagioController;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.CampoEstagio; 

public class CampoEstagioFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private CampoEstagio campoEstagio;
	private CampoEstagio campoEstagioAltera;
	private List<CampoEstagio> listaCampoEstagio;
	private GenericController<CampoEstagio> genericController;
	private CampoEstagioController campoEstagioController;
	
	public List<CampoEstagio> getListaCampoEstagio() {
		if(listaCampoEstagio == null) {
			listaCampoEstagio = getCampoEstagioController().findAll();
		}
		return listaCampoEstagio;
	}
	
	public void save() {
		try {
			getGenericController().save(getCampoEstagio());
			campoEstagio=null; genericController = null; listaCampoEstagio = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Campo de Estagio salvo com suceso.", ""));
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Campo de Estagio.", ""));
		}
	}
	
	public void remover() {
		try {
			if(campoEstagio != null) {
				getGenericController().remove(getCampoEstagio());
				campoEstagio = null; genericController = null; listaCampoEstagio = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Campo de Estagio removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Campo de Estagio.", ""));
		}
	}
	
	public void update() {
		try {
			if(campoEstagioAltera != null) {
				getGenericController().update(getCampoEstagioAltera());
				campoEstagioAltera = null; genericController = null; listaCampoEstagio = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Campo de Estagio alterado com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Campo de Estagio.", ""));
		}
	}
	
	public CampoEstagio getCampoEstagio() {
		return campoEstagio == null ? campoEstagio = new CampoEstagio() : campoEstagio;
	}
	public void setCampoEstagio(CampoEstagio campoEstagio) {
		this.campoEstagio = campoEstagio;
	}
	public CampoEstagio getCampoEstagioAltera() {
		return campoEstagioAltera == null ? campoEstagioAltera = new CampoEstagio() : campoEstagioAltera;
	}
	public void setCampoEstagioAltera(CampoEstagio campoEstagioAltera) {
		this.campoEstagioAltera = campoEstagioAltera;
	}
	public GenericController<CampoEstagio> getGenericController() {
		return genericController == null ? genericController = new GenericController<CampoEstagio>() : genericController;
	}
	public CampoEstagioController getCampoEstagioController() {
		return campoEstagioController == null ? campoEstagioController = new CampoEstagioController() : campoEstagioController;
	}
}
