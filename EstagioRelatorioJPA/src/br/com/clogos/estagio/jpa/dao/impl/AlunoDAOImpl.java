package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.exception.ExceptionNegocial;
import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.AlunoDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Turma;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class AlunoDAOImpl implements Serializable, AlunoDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<Aluno> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Aluno> lista = new ArrayList<Aluno>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT DISTINCT a FROM Aluno a JOIN FETCH a.turmas t ");
		hql.append("ORDER BY a.nome");
		
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(hql.toString(), Aluno.class);
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
		StringBuilder sql = new StringBuilder();
		Aluno aluno = null;
		
		if(existeCPFSemestre(param.getCpf(), param.getSemestre().getId())) {
		
			sql.append("SELECT a FROM Aluno a JOIN FETCH a.perfil p JOIN FETCH a.turmas t JOIN FETCH t.semestre s ");
			sql.append("LEFT JOIN t.liberarRelatorios l ");
			sql.append("WHERE s.id = :semestre AND a.cpf  = :cpf AND a.senha = :senha");
		
			try {
				TypedQuery<Aluno> query = entityManager.createQuery(sql.toString(), Aluno.class)
						.setParameter("semestre", param.getSemestre().getId())
						.setParameter("cpf", param.getCpf())
						.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()));
			aluno = query.getSingleResult();
			} catch (PersistenceException e) {
				ExceptionNegocial.exibirMsgWarn("Atenção: Usuário ou Senha Inválida!");
				entityManager.getTransaction().rollback();
			}
		} else {
			ExceptionNegocial.exibirMsgWarn("Atenção: Não está matrículado no Semestre selecionado");
		}
			
		return aluno;
	}

	@Override
	public Boolean updateSenha(String cpf, String senha) {
		entityManager = JpaUtil.getEntityManager();
		String hql = "UPDATE Aluno SET senha = :senha WHERE cpf = :cpf";
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
	public Aluno find(Long idAluno) {
		Aluno aluno = new Aluno();
		entityManager = JpaUtil.getEntityManager();
		String hql = "SELECT a FROM Aluno a WHERE a.id = :idAluno";
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(hql, Aluno.class)
					.setParameter("idAluno", idAluno);
			aluno = query.getSingleResult();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return aluno;
	}
	
	@Override
	public List<Aluno> findPorTurma(Long idTurma, Long idSemestre) {
		List<Aluno> lista = new ArrayList<Aluno>();
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT a FROM Aluno a JOIN FETCH a.turmas t JOIN FETCH t.semestre s ");
		hql.append("WHERE t.id = :idTurma AND s.id = :idSemestre");
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(hql.toString(), Aluno.class)
					.setParameter("idTurma", idTurma)
					.setParameter("idSemestre", idSemestre);
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Aluno> findPorTurmaGrupo(Long idTurma, Long idSemestre) {
		List<Aluno> lista = new ArrayList<Aluno>();
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sql = new StringBuilder();
		Aluno aluno = null;
		
		sql.append("select distinct a.idaluno, a.cpf, a.nomealuno, a.status, t.idturma from uniweb.ALUNO a  ");
		sql.append("inner join uniweb.turma_aluno ta on a.idaluno=ta.alunos_idaluno ");
		sql.append("inner join uniweb.TURMA t on ta.turmas_idturma=t.idturma ");
		sql.append("inner join uniweb.SEMESTRE s on t.fksemestre=s.idsemestre ");
		sql.append("inner join uniweb.grupo_aluno ga on a.idaluno=ga.alunosGrupo_idaluno ");
		sql.append("inner join uniweb.GRUPO g on ga.grupos_idgrupo=g.idgrupo and g.fkturma=t.idturma ");
		sql.append("where t.idturma=:idTurma and s.idsemestre=:idSemestre order by a.nomealuno ");
		
		try {
			Query queryAluno = entityManager.createNativeQuery(sql.toString())
					.setParameter("idTurma", idTurma).setParameter("idSemestre", idSemestre);
			
			Iterator iAluno = queryAluno.getResultList().iterator();
			
			while(iAluno.hasNext()) {
				Object[] objs = (Object[]) iAluno.next();
				aluno = new Aluno();
				aluno.setTurmaT(new Turma());
				aluno.setId(Long.valueOf(objs[0].toString()));
				aluno.setCpf(objs[1].toString());
				aluno.setNome(objs[2].toString());
				aluno.setStatus(objs[3].toString().toLowerCase());
				aluno.getTurmaT().setId(Long.valueOf(objs[4].toString()));
				lista.add(aluno);
			}
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<Aluno> findPorCpf(String cpf, Long idSemestre) {
		List<Aluno> lista = new ArrayList<Aluno>();
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT idaluno, idturma, cpf, a.nomealuno, t.nometurma, t.fksemestre from uniweb.aluno a  ");
		hql.append("inner join uniweb.turma_aluno ta on a.idaluno = ta.alunos_idaluno ");
		hql.append("inner join uniweb.turma t on t.idturma = ta.turmas_idturma ");
		hql.append("where cpf= :cpf and t.fksemestre = :idSemestre ");
		
		try {
			Query query = entityManager.createNativeQuery(hql.toString())
					.setParameter("cpf", cpf)
					.setParameter("idSemestre", idSemestre);
			Iterator i = query.getResultList().iterator();
			while(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				Aluno aluno = new Aluno();
				aluno.setId(Long.valueOf(objs[0].toString()));
				aluno.getTurmaT().setId(Long.valueOf(objs[1].toString()));
				aluno.setCpf(objs[2].toString());
				aluno.setNome(objs[3].toString());
				aluno.getTurmaT().setNome(objs[4].toString());
				aluno.getTurmaT().getSemestre().setId(Long.valueOf(objs[5].toString()));
				lista.add(aluno);
			}
			
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
	public Boolean transferirAlunoTurmaRelatorio(Aluno aluno, Long idTurmaTransferir) {
		entityManager = JpaUtil.getEntityManager();
		//ExemploJPA: Update Key k set k.counter = 0 where exists (Select u from User u join u.devices d where u.login = "x" and d.applet.key = k)
		String hql = "UPDATE uniweb.turma_aluno SET turmas_idturma = :idTurmaTransf WHERE alunos_idaluno = :idAluno AND turmas_idturma = :idTurmaOri";
		try {
			Query queryAlunoTurma = entityManager.createNativeQuery(hql)
					.setParameter("idTurmaTransf", idTurmaTransferir)
					.setParameter("idAluno", aluno.getId())
					.setParameter("idTurmaOri", aluno.getTurmaT().getId());
			entityManager.getTransaction().begin();
			queryAlunoTurma.executeUpdate();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE uniweb.relatorio SET fkturma = ? ");
			sql.append("WHERE fkturma = ? AND fkaluno = ?");
			
			Query queryRelatorio = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, idTurmaTransferir)
					.setParameter(2, aluno.getTurmaT().getId())
					.setParameter(3, aluno.getId());
			queryRelatorio.executeUpdate();
			
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			return false;
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
	
	private Boolean existeCPFSemestre(String cpf, Long idSemestre) {
		Boolean retorno = false;
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT idaluno, idturma, cpf, a.nomealuno, t.nometurma, t.fksemestre from uniweb.aluno a  ");
		hql.append("inner join uniweb.turma_aluno ta on a.idaluno = ta.alunos_idaluno ");
		hql.append("inner join uniweb.turma t on t.idturma = ta.turmas_idturma ");
		hql.append("where cpf= :cpf and t.fksemestre = :idSemestre ");
		
		try {
			Query query = entityManager.createNativeQuery(hql.toString())
					.setParameter("cpf", cpf)
					.setParameter("idSemestre", idSemestre);
			if(query.getResultList().size() > 0) {
				retorno = true;
			}
			
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			ExceptionNegocial.exibirMsgErro("Problemas ao verificar Aluno Matrículado.");
		}
		
		return retorno;
	}
}
