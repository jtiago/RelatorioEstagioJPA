package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.LiberarRelatorioDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.LiberarRelatorio;
import br.com.clogos.estagio.model.Turma;
/**
 * 
 * @author jtiago
 *
 */
public class LiberarRelatorioDAOImpl implements LiberarRelatorioDAO, Serializable {
	private static final long serialVersionUID = -6629642299995093278L;
	private EntityManager entityManager;
	
	/**
	 * Verifica se já existe módulo liberado para aquela turma
	 * não depende se esta aberto ou não, não pode ter dois módulo iguais liberado para mesma turma.
	 */
	@Override
	public Boolean existeModuloLiberado(LiberarRelatorio oT) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT l FROM LiberarRelatorio l JOIN l.turmaLiberarRelatorio lt JOIN lt.semestre s ");
		hql.append("WHERE (l.modulo = :modulo AND lt.id = :idturma) OR (s.id = :idSemestre AND lt.id = :idturma)");
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql.toString(), LiberarRelatorio.class)
					.setParameter("modulo", oT.getModulo())
					.setParameter("idturma", oT.getTurmaLiberarRelatorio().getId())
					.setParameter("idSemestre", oT.getTurmaLiberarRelatorio().getSemestre().getId());
			return query.getResultList().size() != 0;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return false;
	}
	
	/**
	 * Verifica se existe módulo em aberto para o mesmo semestre e turma.
	 */
	@Override
	public Boolean existeModuloAberto(LiberarRelatorio oT) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT l FROM LiberarRelatorio l ");
		hql.append("JOIN l.turmaLiberarRelatorio lt JOIN lt.semestre ls ");
		hql.append("WHERE lt.id = :idturma AND l.aberto = :aberto AND ls.id = :idSemestre " );
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql.toString(), LiberarRelatorio.class)
					.setParameter("idturma", oT.getTurmaLiberarRelatorio().getId())
					.setParameter("aberto", true)
					.setParameter("idSemestre", oT.getTurmaLiberarRelatorio().getSemestre().getId());
			return query.getResultList().size() != 0;
		} catch (Exception e) {
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
	public Boolean fecharModuloLiberado(Long idTurma, String modulo) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE uniweb.LiberarRelatorio SET aberto = ? WHERE fkturma = ? AND modulo = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, 0)
					.setParameter(2, idTurma)
					.setParameter(3, "Modulo_I");
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
	public List<LiberarRelatorio> findAll(Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT l FROM LiberarRelatorio l JOIN FETCH l.turmaLiberarRelatorio t JOIN FETCH t.semestre s WHERE s.id = :idSemestre";
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql, LiberarRelatorio.class)
					.setParameter("idSemestre", idSemestre);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return null;
	}
	
	/**
	 * Retorna true caso o limite da quantidade de relatório esteja estourado.
	 */
	@Override
	public Boolean verificaQtdRelatorio(Aluno aluno) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sql = new StringBuilder();
		Boolean isQtdRelatorio = null;
		sql.append("SELECT qtdRelatorio,  ");
		sql.append("(SELECT count(*) FROM uniweb.relatorio rs INNER JOIN uniweb.Aluno ass ON ass.idaluno=rs.fkaluno ");
		sql.append("INNER JOIN uniweb.Turma t ON t.idturma=rs.fkturma INNER JOIN uniweb.Semestre s ON s.idsemestre=t.fksemestre ");
		sql.append("WHERE s.idsemestre = :idsemestre AND ass.cpf = :cpf AND t.idturma = :idturma) as limiteRelatorio ");
		sql.append("FROM uniweb.LiberarRelatorio l INNER JOIN uniweb.Turma t ON t.idturma=l.fkturma INNER JOIN uniweb.Semestre s ON s.idsemestre=t.fksemestre ");
		sql.append("WHERE t.idturma = :idturma AND s.idsemestre = :idsemestre");
		
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter("idturma", aluno.getTurmaT().getId())
					.setParameter("idsemestre", aluno.getSemestre().getId())
					.setParameter("cpf", aluno.getCpf());
			Object[] objects = (Object[]) query.getSingleResult();
			if(objects != null) {
				isQtdRelatorio = objects[0].toString().equals(objects[1].toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return isQtdRelatorio;
	}
	
	@Override
	public LiberarRelatorio obterLiberarRelatorioPorTurma(Turma turma) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		LiberarRelatorio liberarRelatorio = null;
		hql.append("SELECT l FROM LiberarRelatorio l JOIN l.turmaLiberarRelatorio t JOIN t.semestre s ");
		hql.append(" WHERE s.id = :idSemestre AND t.id = :idTurma");
		
		try {
			TypedQuery<LiberarRelatorio> query = entityManager.createQuery(hql.toString(), LiberarRelatorio.class)
					.setParameter("idSemestre", turma.getSemestre().getId())
					.setParameter("idTurma", turma.getId());
			liberarRelatorio =  query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return liberarRelatorio;
	}
}
