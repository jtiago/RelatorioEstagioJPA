package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.GrupoCampoEstagioDAO;
import br.com.clogos.estagio.model.Grupo;
import br.com.clogos.estagio.model.GrupoCampoEstagio;


public class GrupoCampoEstagioDAOImpl implements GrupoCampoEstagioDAO, Serializable {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	
	@Override
	public List<GrupoCampoEstagio> find(GrupoCampoEstagio grupoCampo) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		List<GrupoCampoEstagio> lista = new LinkedList<GrupoCampoEstagio>();
		hql.append("SELECT gc FROM GrupoCampoEstagio gc JOIN gc.grupo g JOIN gc.campoEstagio c ");
		hql.append("WHERE g.id = :idGrupo AND c.id = :idCampo ");
		
		try {
			TypedQuery<GrupoCampoEstagio> query = entityManager.createQuery(hql.toString(), GrupoCampoEstagio.class)
					.setParameter("idGrupo", grupoCampo.getGrupo().getId())
					.setParameter("idCampo", grupoCampo.getCampoEstagio().getId());
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
	
	@Override
	public List<GrupoCampoEstagio> findPorGrupo(Grupo grupo) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		List<GrupoCampoEstagio> lista = new LinkedList<GrupoCampoEstagio>();
		hql.append("SELECT gc FROM GrupoCampoEstagio gc JOIN gc.grupo g JOIN gc.campoEstagio c ");
		hql.append("WHERE g.id = :idGrupo ORDER BY gc.dataInicial ");
		
		try {
			TypedQuery<GrupoCampoEstagio> query = entityManager.createQuery(hql.toString(), GrupoCampoEstagio.class)
					.setParameter("idGrupo", grupo.getId());
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
