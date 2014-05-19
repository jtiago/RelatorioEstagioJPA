package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.jpa.dao.impl.SupervisorDAOImpl;


public class SupervisorController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SupervisorDAO supervisorDAO;
	
	public SupervisorDAO getSupervisorDAO() {
		if(supervisorDAO == null) {
			supervisorDAO = new SupervisorDAOImpl();
		}
		return supervisorDAO;
	}

}
