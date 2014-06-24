package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.enums.ModuloEnum;
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public Aluno validarAutenticacao(Aluno param) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idaluno,a.nomealuno,a.cpf,a.nometurma,t.nomeCurso,p.nomeperfil,p.idperfil,l.modulo,  ");
		sql.append("cadastroAluno,cadastroCampo,cadastroSupervisor,cadastroTurma,liberarRelatorio,relatorioAluno, ");
		sql.append("relatorioAdmin,l.aberto FROM Aluno a ");
		sql.append("INNER JOIN Turma t ON a.nometurma = t.nometurma ");
		sql.append("INNER JOIN Perfil p ON p.idperfil = a.fkperfil ");
		sql.append("LEFT JOIN LiberarRelatorio l ON l.fkturma = t.idturma ");
		sql.append("WHERE a.cpf = ? AND a.senha = ? ORDER BY idliberar DESC ");
		
		Aluno aluno = null;
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, param.getCpf())
					.setParameter(2, CriptografiaBase64.encrypt(param.getSenha()));
			Iterator i = query.getResultList().iterator();
			if(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				aluno = new Aluno();
				aluno.setId(Long.valueOf(objs[0].toString()));
				aluno.setNome(objs[1].toString());
				aluno.setCpf(objs[2].toString());
				aluno.setNomeTurma(objs[3].toString());
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
			}
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
}
