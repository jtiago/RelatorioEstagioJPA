package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.SupervisorController;
import br.com.clogos.estagio.model.ImagemAssinatura;
import br.com.clogos.estagio.model.Supervisor;

public class SupervisorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Supervisor supervisor;
	private SupervisorController supervisorController;
	private GenericController genericControl;
	
	public void save(ImagemAssinatura assinatura, String nome) {
		getSupervisor().setNome(nome);
		getSupervisor().setImagem(assinatura);
		try {
			getGenericControl().save(getSupervisor());
			supervisor=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Supervisor getSupervisor() {
		if(supervisor == null) {
			supervisor = new Supervisor();
		}
		return supervisor;
	}
	
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public SupervisorController getSupervisorController() {
		return supervisorController == null ? new SupervisorController() : supervisorController;
	}
	
	public GenericController getGenericControl() {
		if(genericControl == null) {
			genericControl = new GenericController();
		}
		return genericControl;
	}
}
