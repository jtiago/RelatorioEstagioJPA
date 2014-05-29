package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.AlunoDAO;
import br.com.clogos.estagio.jpa.dao.impl.AlunoDAOImpl;
import br.com.clogos.estagio.model.Aluno;


public class AlunoController implements Serializable {
	private static final long serialVersionUID = 1L;
	private AlunoDAO alunoDAO;
	
	public List<Aluno> findAll() {
		return getSupervisorDAO().findAll();
	}
	
	public AlunoDAO getSupervisorDAO() {
		if(alunoDAO == null) {
			alunoDAO = new AlunoDAOImpl();
		}
		return alunoDAO;
	}

}
