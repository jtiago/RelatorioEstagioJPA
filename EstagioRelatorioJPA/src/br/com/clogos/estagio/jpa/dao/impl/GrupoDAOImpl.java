package br.com.clogos.estagio.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.GrupoDAO;
import br.com.clogos.estagio.model.Grupo;

public class GrupoDAOImpl implements GrupoDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<Grupo> findAll(Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		List<Grupo> lista = new ArrayList<Grupo>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT DISTINCT g FROM Grupo g JOIN FETCH g.turmaGrupo t JOIN FETCH g.listaGrupoCampoEstagio ");
		hql.append("LEFT JOIN g.alunosGrupo a JOIN FETCH t.semestre s WHERE s.id = :idSemestre");
		try {
		TypedQuery<Grupo> query = entityManager.createQuery(hql.toString(), Grupo.class)
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
	public Grupo findGrupoAluno(Long idSemestre, Long idGrupo) {
		entityManager = JpaUtil.getEntityManager();
		Grupo grupo = new Grupo();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT g FROM Grupo g JOIN FETCH g.turmaGrupo t JOIN FETCH g.alunosGrupo a ");
		hql.append("JOIN FETCH t.semestre s WHERE s.id = :idSemestre AND g.id = :idGrupo");
		try {
		TypedQuery<Grupo> query = entityManager.createQuery(hql.toString(), Grupo.class)
				.setParameter("idSemestre", idSemestre)
				.setParameter("idGrupo", idGrupo);
		grupo = query.getSingleResult();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return grupo;
	}

}
