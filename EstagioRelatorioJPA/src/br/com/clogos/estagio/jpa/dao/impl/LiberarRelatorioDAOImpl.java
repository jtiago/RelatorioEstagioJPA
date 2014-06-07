package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.LiberarRelatorioDAO;
import br.com.clogos.estagio.model.LiberarRelatorio;
/**
 * 
 * @author jtiago
 *
 */
public class LiberarRelatorioDAOImpl implements LiberarRelatorioDAO, Serializable {
	private static final long serialVersionUID = -6629642299995093278L;
	private EntityManager entityManager;
	
	/**
	 * Quando a consulta retornar dados é porque existe modulo liberado para 
	 * aquele turma passada como paramentro
	 */
	@Override
	public Boolean existeModuloLiberado(LiberarRelatorio oT) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT l FROM LiberarRelatorio l JOIN l.turmaLiberarRelatorio lt ");
		hql.append("WHERE l.modulo = :modulo AND lt.nome = :turma");
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql.toString(), LiberarRelatorio.class)
					.setParameter("modulo", oT.getModulo()).setParameter("turma", oT.getTurmaLiberarRelatorio().getNome());
			return query.getResultList().size() != 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return false;
	}

	@Override
	public Boolean existeModuloAberto(LiberarRelatorio oT) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT l FROM LiberarRelatorio l JOIN l.turmaLiberarRelatorio lt ");
		hql.append("WHERE lt.nome = :turma AND l.aberto = :aberto" );
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql.toString(), LiberarRelatorio.class)
					.setParameter("turma", oT.getTurmaLiberarRelatorio().getNome())
					.setParameter("aberto", true);
			return query.getResultList().size() != 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return false;
	}
}
