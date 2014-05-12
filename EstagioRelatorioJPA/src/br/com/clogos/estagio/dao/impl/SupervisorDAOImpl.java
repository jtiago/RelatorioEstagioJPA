package br.com.clogos.estagio.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.clogos.estagio.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;

@Repository
public class SupervisorDAOImpl implements SupervisorDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Supervisor supervisor) {
		entityManager.persist(supervisor);
	}
}
