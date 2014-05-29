package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.jpa.dao.impl.SupervisorDAOImpl;
import br.com.clogos.estagio.model.Supervisor;


public class SupervisorController implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorDAO supervisorDAO;
	
	public List<Supervisor> findAll() {
		return getSupervisorDAO().findAll();
	}
	
	public SupervisorDAO getSupervisorDAO() {
		if(supervisorDAO == null) {
			supervisorDAO = new SupervisorDAOImpl();
		}
		return supervisorDAO;
	}

}
