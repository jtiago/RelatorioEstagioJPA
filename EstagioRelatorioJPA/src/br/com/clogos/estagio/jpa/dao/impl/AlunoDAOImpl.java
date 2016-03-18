package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
		/*sql.append("SELECT idaluno,a.nomealuno,a.cpf,a.nometurma,t.nomeCurso,p.nomeperfil,p.idperfil,l.modulo,  ");
		sql.append("cadastroAluno,cadastroCampo,cadastroSupervisor,cadastroTurma,liberarRelatorio,relatorioAluno, ");
		sql.append("relatorioAdmin,l.aberto,validado,revisao,t.idturma,revisaoRelatorio,relatorioEnviado, s.idsemestre, nomeSemestre, ");
		sql.append("(SELECT count(*) FROM relatorio rs INNER JOIN Aluno ass ON ass.idaluno=rs.fkaluno WHERE rs.fksemestre = :semestre and ass.cpf = :cpf) as limiteRelatorio, ");
		sql.append("qtdRelatorio FROM Aluno a ");
		sql.append("INNER JOIN Turma t ON a.nometurma = t.nometurma ");
		sql.append("INNER JOIN Perfil p ON p.idperfil = a.fkperfil ");
		sql.append("LEFT JOIN LiberarRelatorio l ON l.fkturma = t.idturma AND l.fksemestre = :semestre ");
		sql.append("LEFT JOIN Relatorio r ON r.fkaluno = a.idaluno ");
		sql.append("LEFT JOIN Semestre s ON l.fksemestre = s.idsemestre ");
		sql.append("WHERE a.cpf = :cpf AND a.senha = :senha ORDER BY idrelatorio DESC ");*/
		
		sql.append("SELECT a FROM Aluno a JOIN a.perfil p JOIN FETCH a.turmas t JOIN t.semestre s ");
		sql.append("LEFT JOIN t.liberarRelatorios l ");
		sql.append("WHERE s.id = :semestre AND a.cpf  = :cpf AND a.senha = :senha");
		
		Aluno aluno = null;
		try {
			TypedQuery<Aluno> query = entityManager.createQuery(sql.toString(), Aluno.class)
					.setParameter("semestre", param.getSemestre().getId())
					.setParameter("cpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()));
		aluno = query.getSingleResult();
			/*Iterator i = query.getResultList().iterator();
			if(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				aluno = new Aluno();
				aluno.setId(Long.valueOf(objs[0].toString()));
				aluno.setNome(objs[1].toString());
				aluno.setCpf(objs[2].toString());
				//aluno.setNomeTurma(objs[3].toString());
				aluno.getTurmaT().setNomeCurso(objs[4].toString());
				aluno.getPerfil().setNome(objs[5].toString());
				aluno.getPerfil().setId(Long.valueOf(objs[6].toString()));
				aluno.setModulo(objs[7] == null ? null : ModuloEnum.valueOf(ModuloEnum.class, objs[7].toString()).getLabel().toUpperCase());
				aluno.setFichaRelatorio(objs[7] == null ? null : ModuloEnum.valueOf(ModuloEnum.class, objs[7].toString()).getFicha());
				aluno.getPerfil().setCadastroAluno(Boolean.valueOf(objs[8].toString()));
				aluno.getPerfil().setCadastroCampo(Boolean.valueOf(objs[9].toString()));
				aluno.getPerfil().setCadastroSupervisor(Boolean.valueOf(objs[10].toString()));
				aluno.getPerfil().setCadastroTurma(Boolean.valueOf(objs[11].toString()));
				aluno.getPerfil().setLiberarRelatorio(Boolean.valueOf(objs[12].toString()));
				aluno.getPerfil().setRelatorioAluno(Boolean.valueOf(objs[13].toString()));
				aluno.getPerfil().setRelatorioAdmin(Boolean.valueOf(objs[14].toString()));
				aluno.setModuloLiberado(objs[15] == null ? false : Boolean.valueOf(objs[15].toString()));
				aluno.getRelatorioR().setValidado(objs[16] == null ? false : Boolean.valueOf(objs[16].toString()));
				aluno.getRelatorioR().setRevisao(objs[17] == null ? false : Boolean.valueOf(objs[17].toString()));
				aluno.getTurmaT().setId(Long.valueOf(objs[18].toString()));
				aluno.getPerfil().setRevisaoRelatorio(Boolean.valueOf(objs[19].toString()));
				aluno.getPerfil().setRelatorioEnviado(Boolean.valueOf(objs[20].toString()));
				aluno.getSemestre().setId(objs[21] == null ? null : Long.valueOf(objs[21].toString()));
				aluno.getSemestre().setNomeSemestre(objs[22].toString());
				aluno.setLimiteRelatorio(objs[23].toString().equals(objs[24].toString()));
			}:*/
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
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
	public List<Aluno> findPorCpf(String cpf, Long idSemestre) {
		List<Aluno> lista = new ArrayList<Aluno>();
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECt idaluno, idturma, cpf, a.nomealuno, t.nometurma, t.fksemestre from uniweb.aluno a  ");
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
			sql.append("UPDATE Relatorio SET fkturma = ? ");
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
}
