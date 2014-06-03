package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.AutenticarDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class AutenticarDAOImpl implements AutenticarDAO, Serializable {
	private static final long serialVersionUID = 8520295677917104729L;
	private EntityManager entityManager;

	@Override
	public Aluno validarAutenticacao(Aluno param) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT a FROM Aluno a WHERE a.cpf = :numCpf AND a.senha = :senha";
		Aluno aluno = null;
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(hql, Aluno.class)
					.setParameter("numCpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()));
			if(query.getResultList().size() != 0) {
				aluno = query.getSingleResult();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return aluno;
	}
}
