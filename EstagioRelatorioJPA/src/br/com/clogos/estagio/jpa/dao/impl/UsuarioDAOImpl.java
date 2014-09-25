package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.UsuarioDAO;
import br.com.clogos.estagio.model.Usuario;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class UsuarioDAOImpl implements UsuarioDAO, Serializable {
	private static final long serialVersionUID = 8520295677917104729L;
	private EntityManager entityManager;

	@Override
	public Usuario validarAutenticacao(Usuario param) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT u FROM Usuario u JOIN u.perfil p ");
		hql.append("WHERE u.cpf = :numCpf AND u.senha = :senha AND p.id = :perfil");
		
		Usuario usuario = null;
		try {
			TypedQuery<Usuario> query = entityManager.createQuery(hql.toString(), Usuario.class)
					.setParameter("numCpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()))
					.setParameter("perfil", param.getPerfil().getId());
			if(query.getResultList().size() != 0) {
				usuario = query.getSingleResult();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return usuario;
	}

	@Override
	public Boolean updateSenha(String cpf, String senha) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "UPDATE Usuario SET senha = :senha WHERE cpf = :cpf";
		try {
			Query query = entityManager.createQuery(hql)
					.setParameter("senha", CriptografiaBase64.encrypt(senha))
					.setParameter("cpf", cpf);
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	@Override
	public List<Usuario> findAll() {
		entityManager = JpaUtil.getEntityManager();
		return null;
	}
}
