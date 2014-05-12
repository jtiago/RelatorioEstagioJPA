package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.clogos.estagio.controller.SupervisorController;
import br.com.clogos.estagio.model.ImagemAssinatura;
import br.com.clogos.estagio.model.Supervisor;

public class SupervisorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Supervisor supervisor;
	@Autowired
	private SupervisorController supervisorController;
	
	public void save(ImagemAssinatura assinatura, String nome) {
		getSupervisor().setNome(nome);
		getSupervisor().setImagem(assinatura);
		try {
			supervisorController.save(getSupervisor());
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
}
