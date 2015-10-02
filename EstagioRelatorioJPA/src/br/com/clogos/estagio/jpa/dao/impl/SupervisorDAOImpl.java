package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.util.CriptografiaBase64;


public class SupervisorDAOImpl implements SupervisorDAO, Serializable {
	private static final long serialVersionUID = 1L;
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
	
	@Override
	public List<Supervisor> findPorCampo(Long idCampo) {
		entityManager = JpaUtil.getEntityManager();
		List<Supervisor> lista = new ArrayList<Supervisor>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT s FROM Supervisor s JOIN s.imagem i JOIN s.campoEstagio c ");
		hql.append("WHERE c.id = :idCampo ORDER BY s.nome");
		
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql.toString(), Supervisor.class)
					.setParameter("idCampo", idCampo);
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
	public Supervisor validarAutenticacao(Supervisor param) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT s FROM Supervisor s JOIN s.perfil p ");
		hql.append("WHERE s.cpf = :numCpf AND s.senha = :senha AND p.id = :perfil");
		Supervisor supervisor = null;
		
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql.toString(), Supervisor.class)
					.setParameter("numCpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()))
					.setParameter("perfil", param.getPerfil().getId());
			if(query.getResultList().size() != 0) {
				supervisor = query.getSingleResult();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return supervisor;
	}
}
