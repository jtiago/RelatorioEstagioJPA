package br.com.clogos.estagio.dao.impl;

import javax.persistence.EntityManager;

import br.com.clogos.estagio.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;


public class SupervisorDAOImpl implements SupervisorDAO {
	
	private EntityManager entityManager;
	
	@Override
	public void save(Supervisor supervisor) {
		entityManager.persist(supervisor);
	}
}
