package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.RelatorioDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;

public class RelatorioDAOImpl implements RelatorioDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public Boolean existeRelatorioPreenchido(Aluno aluno) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT r FROM Relatorio r JOIN r.aluno ra ");
		hql.append("WHERE ra.id = :id AND r.validado = :validado");
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("id", aluno.getId())
					.setParameter("validado", false);
			return query.getResultList().size() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return true;
	}

}
