package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.AlunoDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class AlunoDAOImpl implements Serializable, AlunoDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<Aluno> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Aluno> lista = new ArrayList<Aluno>();
		String hql = "SELECT a FROM Aluno a ORDER BY a.nome";
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(hql, Aluno.class);
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
	public Aluno validarAutenticacao(Aluno param) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT a FROM Aluno a  WHERE a.cpf = :numCpf AND a.senha = :senha";
		//String sql = "SELECT a.nome,a.cpf,a.turma,t.nomeCurso FROM Aluno a INNER JOIN Turma t ON a.turma = t.nometurma";
		Aluno aluno = null;
		try {;
			
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
