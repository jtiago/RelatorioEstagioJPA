package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
		hql.append("SELECT r FROM Relatorio r JOIN r.aluno ra JOIN r.turmaRelatorio t ");
		hql.append("WHERE ra.cpf = :cpf AND r.validado = :validado AND t.id = :idTurma");
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", aluno.getCpf())
					.setParameter("idTurma", aluno.getTurmaT().getId())
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

	@Override
	public List<Relatorio> findRelatoriosAdmin(Relatorio relatorio) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		List<Relatorio> lista = new LinkedList<Relatorio>();
		hql.append("SELECT r FROM Relatorio r JOIN FETCH r.aluno a JOIN FETCH r.supervisor s JOIN r.campoEstagio c ");
		hql.append("JOIN FETCH s.imagem i JOIN FETCH r.turmaRelatorio t JOIN FETCH t.semestre s ");
		hql.append("WHERE s.id = :idSemestre ");
		if(relatorio.getCampoEstagio() != null) {
			hql.append("AND c.id = :idCampo ");
		}
		if(relatorio.getTurmaRelatorio().getId() != 0) {
			hql.append("AND t.id = :idTurma ");
		}
		if(relatorio.getValidado() != null) {
			hql.append("AND r.validado = :validado ");
		}
		
		if(relatorio.getRevisao() != null) {
			hql.append("AND r.revisao = :revisao ");
		}
		
		hql.append("ORDER BY a.nome ");
		
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class);
			query.setParameter("idSemestre", relatorio.getIdSemestre());
			if(relatorio.getCampoEstagio() != null) {
				query.setParameter("idCampo", relatorio.getCampoEstagio().getId());
			}
			if(relatorio.getTurmaRelatorio().getId() != 0) {
				query.setParameter("idTurma", relatorio.getTurmaRelatorio().getId());
			}
			if(relatorio.getValidado() != null) {
				query.setParameter("validado", relatorio.getValidado());
			}
			if(relatorio.getRevisao() != null) {
				query.setParameter("revisao", relatorio.getValidado());
			}
					
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
	public Boolean updateValidarRelatorio(Long id,  String observacao) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Relatorio SET validado = ?, observacao = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, 1)
					.setParameter(2, observacao)
					.setParameter(3, id);
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
	public Boolean updateRevisaoRelatorio(Long id, String observacao) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Relatorio SET revisao = ?, observacao = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, 1)
					.setParameter(2, observacao)
					.setParameter(3, id);
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
	public List<Relatorio> findRelatoriosRevisao(Aluno aluno) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		List<Relatorio> lista = new LinkedList<Relatorio>();
		hql.append("SELECT r FROM Relatorio r JOIN FETCH r.turmaRelatorio t JOIN FETCH r.aluno a ");
		hql.append("JOIN FETCH r.campoEstagio c JOIN FETCH r.supervisor JOIN FETCH t.semestre s ");
		hql.append("WHERE a.cpf = :cpf AND r.revisao = :revisao AND s.id = :idSemestre ");
		
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", aluno.getCpf())
					.setParameter("revisao", true)
					.setParameter("idSemestre", aluno.getSemestre().getId());
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
	public Boolean saveRevisaoRelatorioAluno(Relatorio relatorio) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Relatorio SET revisao = ?, texto = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, 0)
					.setParameter(2, relatorio.getTexto())
					.setParameter(3, relatorio.getId());
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
	
	public Boolean alterarDataInicioTerminioRelatorio(Relatorio relatorio) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Relatorio SET dataInicio = ?, dataTerminio = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, relatorio.getDataInicio())
					.setParameter(2, relatorio.getDataTerminio())
					.setParameter(3, relatorio.getId());
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
	public List<Relatorio> findRelatorioEnviado(Aluno aluno) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		List<Relatorio> lista = new LinkedList<Relatorio>();
		hql.append("SELECT r FROM Relatorio r JOIN FETCH r.aluno a JOIN FETCH r.campoEstagio c JOIN FETCH r.supervisor ");
		hql.append("JOIN FETCH r.turmaRelatorio t JOIN FETCH t.semestre s ");
		hql.append("WHERE a.cpf = :cpf AND s.id = :idSemestre ");
		
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", aluno.getCpf())
					.setParameter("idSemestre", aluno.getSemestre().getId());
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
}
