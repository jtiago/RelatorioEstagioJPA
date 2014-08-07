package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.TurmaDAO;
import br.com.clogos.estagio.model.Turma;

public class TurmaDAOImpl implements Serializable, TurmaDAO {
	private static final long serialVersionUID = 5289808278891409564L;
	private EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Turma> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Turma> lista = new ArrayList<Turma>();
		StringBuilder sql = new StringBuilder();
		Turma turma = null;
		sql.append("SELECT idturma,nometurma,nomecurso,turno " ); //(CASE WHEN l.aberto = 1 THEN modulo ELSE '-' END), idliberar");
		sql.append("FROM Turma t ");
		//sql.append("LEFT JOIN LiberarRelatorio l ON l.fkturma = t.idturma ");
		sql.append("ORDER BY nomecurso");
		try {
			Query query = entityManager.createNativeQuery(sql.toString());
			Iterator i = query.getResultList().iterator();
			while(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				turma = new Turma();
				turma.setId(Long.valueOf(objs[0].toString()));
				turma.setNome(objs[1].toString());
				turma.setNomeCurso(objs[2].toString());
				turma.setTurno(objs[3].toString());
				//turma.getLiberar().setModulo(ModuloEnum.getModulo(objs[4].toString()));
				//turma.getLiberar().setId(Long.valueOf(objs[5].toString()));
				lista.add(turma);
			}
		} catch (PersistenceException e) {
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
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class)
					.setParameter("param", nomeTurma);
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
	
	@Override
	public Turma obterTurma(Long id) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT t FROM Turma t WHERE t.id = :id";
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class)
					.setParameter("id", id);
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
	
	public String getModulo(Object obj) {
		if(obj == null) {
			return "";
		} else {
			return ModuloEnum.valueOf(ModuloEnum.class, obj.toString()).getLabel().toUpperCase();
		}
	}
}
