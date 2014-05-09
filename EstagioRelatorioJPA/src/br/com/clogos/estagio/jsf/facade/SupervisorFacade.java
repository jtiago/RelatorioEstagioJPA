package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import br.com.clogos.estagio.model.ImagemAssinatura;
import br.com.clogos.estagio.model.Supervisor;

public class SupervisorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Supervisor supervisor;
	
	public void save(ImagemAssinatura assinatura) {
		System.out.println(getSupervisor().getNome()+" <<<<<");
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
