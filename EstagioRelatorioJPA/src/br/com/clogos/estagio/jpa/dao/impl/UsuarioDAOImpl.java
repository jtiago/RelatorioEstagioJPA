package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
		String hql = "SELECT u FROM Usuario a WHERE u.cpf = :numCpf AND u.senha = :senha";
		Usuario usuario = null;
		try {
			TypedQuery<Usuario> query = entityManager.createQuery(hql, Usuario.class)
					.setParameter("numCpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()));
			if(query.getResultList().size() != 0) {
				usuario = query.getSingleResult();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return usuario;
	}
}
