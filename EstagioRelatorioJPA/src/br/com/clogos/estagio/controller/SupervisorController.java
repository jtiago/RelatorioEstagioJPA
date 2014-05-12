package br.com.clogos.estagio.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.clogos.estagio.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;

@Transactional
@Controller
public class SupervisorController implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SupervisorDAO supervisorDAO;
	
	public void save(Supervisor supervisor) {
		supervisorDAO.save(supervisor);
	}

}
