package br.com.clogos.estagio.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;


public class SupervisorDAOImpl implements SupervisorDAO {
	
	private EntityManager entityManager;

	@Override
	public List<Supervisor> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Supervisor> lista = new ArrayList<Supervisor>();
		String hql = "SELECT s FROM Supervisor s INNER JOIN s.imagem i ORDER BY s.nome";
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql, Supervisor.class);
			lista = query.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return lista;
	}
	
}
