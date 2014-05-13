package br.com.clogos.estagio.controller;

import java.io.Serializable;

import br.com.clogos.estagio.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;


public class SupervisorController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SupervisorDAO supervisorDAO;
	
	public void save(Supervisor supervisor) {
		supervisorDAO.save(supervisor);
	}

}
