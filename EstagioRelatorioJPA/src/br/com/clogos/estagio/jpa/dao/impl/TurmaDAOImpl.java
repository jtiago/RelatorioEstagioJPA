package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.TurmaDAO;
import br.com.clogos.estagio.model.Turma;

public class TurmaDAOImpl implements Serializable, TurmaDAO {
	private static final long serialVersionUID = 5289808278891409564L;
	private EntityManager entityManager;

	@Override
	public List<Turma> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Turma> lista = new ArrayList<Turma>();
		String hql = "SELECT t FROM Turma t ORDER BY t.nome";
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class);
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
	public Boolean verificaDuplicidade(String nomeTurma) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT t FROM Turma t WHERE t.nome = :param";
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class)
					.setParameter("param", nomeTurma);
			return query.getResultList().size() == 0;
		} catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return false;
	}

	@Override
	public Turma obterCurso(String nomeTurma) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT t FROM Turma t WHERE t.nome = :param";
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class);
			return query.getSingleResult();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return null;
	}

}
