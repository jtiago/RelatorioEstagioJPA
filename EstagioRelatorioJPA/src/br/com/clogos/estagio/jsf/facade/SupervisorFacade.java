package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.SupervisorController;
import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.jpa.dao.impl.SupervisorDAOImpl;
import br.com.clogos.estagio.model.ImagemAssinatura;
import br.com.clogos.estagio.model.Supervisor;

public class SupervisorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Supervisor supervisor;
	private SupervisorController supervisorController;
	@SuppressWarnings("rawtypes")
	private GenericController genericControl;
	private SupervisorDAO supervisorDAO;
	private List<Supervisor> listaSupervisores;
	
	public List<Supervisor> getListaSupervisores() {
		if(listaSupervisores == null) {
			listaSupervisores = getSupervisorDAO().findAll();
		}
		return listaSupervisores;
	}
	
	public void save(ImagemAssinatura assinatura, String nome) {
		getSupervisor().setNome(nome);
		getSupervisor().setImagem(assinatura);
		try {
			getGenericControl().save(getSupervisor());
			supervisor=null; genericControl = null; listaSupervisores = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor salvo com suceso.", ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Supervisor.", ""));
		}
	}
	
	public void remover() {
		try {
			if(supervisor != null) {
				getGenericControl().remove(getSupervisor());
				supervisor = null; genericControl = null; listaSupervisores = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao remover Supervisor.", ""));
		}
	}
	
	public Supervisor getSupervisor() {
		return supervisor == null ? supervisor = new Supervisor() : supervisor;
	}
	
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public SupervisorController getSupervisorController() {
		return supervisorController == null ? supervisorController = new SupervisorController() : supervisorController;
	}
	
	@SuppressWarnings("rawtypes")
	public GenericController getGenericControl() {
		return genericControl == null ? genericControl = new GenericController() : genericControl;
	}
	
	public SupervisorDAO getSupervisorDAO() {
		return supervisorDAO == null ? supervisorDAO = new SupervisorDAOImpl() : supervisorDAO;
	}
}
