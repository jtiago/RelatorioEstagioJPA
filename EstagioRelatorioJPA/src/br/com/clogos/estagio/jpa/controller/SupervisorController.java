package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.jpa.dao.impl.SupervisorDAOImpl;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.vo.SupervisorVO;


public class SupervisorController implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorDAO supervisorDAO;
	
	public List<Supervisor> findAll() {
		return getSupervisorDAO().findAll();
	}
	
	public List<Supervisor> findPorCampo(Long idCampo) {
		return getSupervisorDAO().findPorCampo(idCampo);
	}
	
	public Supervisor validarAutenticacao(Supervisor param) {
		return getSupervisorDAO().validarAutenticacao(param);
	}
	
	public List<SupervisorVO> findSupervisorAnalitico(Long idSemestre) {
		return getSupervisorDAO().findSupervisorAnalitico(idSemestre);
	}
	
	public SupervisorDAO getSupervisorDAO() {
		if(supervisorDAO == null) {
			supervisorDAO = new SupervisorDAOImpl();
		}
		return supervisorDAO;
	}

}
