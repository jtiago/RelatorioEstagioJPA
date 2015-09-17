package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.TurmaDAO;
import br.com.clogos.estagio.model.Turma;

public class TurmaDAOImpl implements Serializable, TurmaDAO {
	private static final long serialVersionUID = 5289808278891409564L;
	private EntityManager entityManager;

	@Override
	public List<Turma> findAll(Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		List<Turma> lista = new ArrayList<Turma>();
		String hql = "SELECT t FROM Turma t INNER JOIN FETCH t.semestre s WHERE s.id = :idSemestre";
		try {
		TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class)
				.setParameter("idSemestre", idSemestre);
		lista = query.getResultList();
			
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
	
	@Override
	public Turma obterTurmaPorNome(String nomeTurma) {
		entityManager = JpaUtil.getEntityManager();
		Turma turma = null;
		String hql = "SELECT t FROM Turma t WHERE t.nome = :nomeTurma";
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql, Turma.class)
					.setParameter("nomeTurma", nomeTurma);
			if(query.getResultList().size() > 0) {
				turma = query.getSingleResult();
			}
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return turma;
	}
	
	public String getModulo(Object obj) {
		if(obj == null) {
			return "";
		} else {
			return ModuloEnum.valueOf(ModuloEnum.class, obj.toString()).getLabel().toUpperCase();
		}
	}

	@Override
	public List<Turma> obterTurmaPorAluno(Long idSemestre, Long idAluno) {
		entityManager = JpaUtil.getEntityManager();
		List<Turma> lista = new ArrayList<Turma>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT t FROM Turma t JOIN FETCH t.alunos a JOIN t.semestre s ");
		hql.append("WHERE s.id = :idSemestre AND a.id = :idAluno");
		
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql.toString(), Turma.class)
					.setParameter("idSemestre", idSemestre)
					.setParameter("idAluno", idAluno);
			lista = query.getResultList();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return lista;
	}
	
	@Override
	public List<Turma> obterTurmaSemVinculoAluno(Long idAluno, Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		List<Turma> lista = new ArrayList<Turma>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT tur FROM Turma tur JOIN tur.semestre se WHERE tur.id NOT IN ( ");
		hql.append("SELECT t.id FROM Turma t JOIN t.alunos a JOIN t.semestre s ");
		hql.append("WHERE a.id =:idAluno AND s.id =:idSemestre) AND se.id =:idSemestre");
		
		try {
			TypedQuery<Turma> query = entityManager.createQuery(hql.toString(), Turma.class)
					.setParameter("idSemestre", idSemestre)
					.setParameter("idAluno", idAluno);
			lista = query.getResultList();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return lista;
	}
}
