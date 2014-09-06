package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.CampoEstagioDAO;
import br.com.clogos.estagio.model.CampoEstagio;

public class CampoEstagioDAOImpl implements Serializable, CampoEstagioDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<CampoEstagio> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<CampoEstagio> lista = new ArrayList<CampoEstagio>();
		String hql = "SELECT c FROM CampoEstagio c ORDER BY c.nome";
		try {
			TypedQuery<CampoEstagio> query = entityManager.createQuery(hql, CampoEstagio.class);
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
