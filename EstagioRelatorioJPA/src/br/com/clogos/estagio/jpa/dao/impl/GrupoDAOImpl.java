package br.com.clogos.estagio.jpa.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.enums.StatusEnum;
import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.GrupoDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Grupo;

public class GrupoDAOImpl implements GrupoDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<Grupo> findAll(Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		List<Grupo> lista = new ArrayList<Grupo>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT DISTINCT g FROM Grupo g JOIN FETCH g.turmaGrupo t JOIN FETCH g.listaGrupoCampoEstagio gc ");
		hql.append("LEFT JOIN g.alunosGrupo a JOIN FETCH t.semestre s WHERE s.id = :idSemestre ");
		try {
		TypedQuery<Grupo> query = entityManager.createQuery(hql.toString(), Grupo.class)
				.setParameter("idSemestre", idSemestre);
		lista = query.getResultList();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return lista;
	}

	@Override
	public Grupo findGrupoAluno(Long idSemestre, Long idGrupo) {
		entityManager = JpaUtil.getEntityManager();
		Grupo grupo = new Grupo();
		grupo.setAlunosGrupo(new LinkedList<Aluno>());
		StringBuilder hql = new StringBuilder();
		hql.append("select gru.nomealuno, rel1.status as col1, rel2.status as col2, rel3.status as col3, rel4.status as col4, ");
		hql.append("rel5.status as col5, rel6.status as col6 from ");
		hql.append("(select distinct g.nomeGrupo, a.idaluno, a.nomealuno from grupo g ");
		hql.append("inner join grupo_aluno ga on ga.grupos_idgrupo=g.idgrupo inner join aluno a on a.idaluno=ga.alunosGrupo_idaluno ");
		hql.append("inner join Turma t on t.idturma=g.fkturma where g.idgrupo = :idGrupo and t.fksemestre = :idSemestre ) as gru ");
		hql.append("left join relatorio as rel1 on rel1.fkaluno=gru.idaluno and rel1.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial,gc1.id, gc1.fkgrupo having count(*) = 1) ");
		hql.append("left join relatorio as rel2 on rel2.fkaluno=gru.idaluno and rel2.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial, gc1.id, gc1.fkgrupo having count(*) = 2) ");
		hql.append("left join relatorio as rel3 on rel3.fkaluno=gru.idaluno and rel3.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial, gc1.id, gc1.fkgrupo having count(*) = 3) ");
		hql.append("left join relatorio as rel4 on rel4.fkaluno=gru.idaluno and rel4.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial, gc1.id, gc1.fkgrupo having count(*) = 4) ");
		hql.append("left join relatorio as rel5 on rel5.fkaluno=gru.idaluno and rel5.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial, gc1.id, gc1.fkgrupo having count(*) = 5) ");
		hql.append("left join relatorio as rel6 on rel6.fkaluno=gru.idaluno and rel6.fkgrupocampoestagio = ");
		hql.append("(select gc1.id from grupocampoestagio gc1 inner join grupocampoestagio gc2 on gc1.datainicial>=gc2.datainicial and gc2.fkgrupo=:idGrupo ");
		hql.append("where gc1.fkgrupo = :idGrupo group by gc1.datainicial, gc1.id, gc1.fkgrupo having count(*) = 6) ");
		
		try {
			Query query = entityManager.createNativeQuery(hql.toString())
					.setParameter("idGrupo", idGrupo)
					.setParameter("idSemestre", idSemestre);
			@SuppressWarnings("rawtypes")
			Iterator i = query.getResultList().iterator();
			while(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				Aluno aluno = new Aluno();
				aluno.setNome(objs[0].toString());
				aluno.setStatus1(objs[1] != null ? StatusEnum.getLabelStatus(objs[1].toString()) : StatusEnum.NAOENVIADO.getLabel());
				aluno.setStatus2(objs[2] != null ? StatusEnum.getLabelStatus(objs[2].toString()) : StatusEnum.NAOENVIADO.getLabel());
				aluno.setStatus3(objs[3] != null ? StatusEnum.getLabelStatus(objs[3].toString()) : StatusEnum.NAOENVIADO.getLabel());
				aluno.setStatus4(objs[4] != null ? StatusEnum.getLabelStatus(objs[4].toString()) : StatusEnum.NAOENVIADO.getLabel());
				aluno.setStatus5(objs[5] != null ? StatusEnum.getLabelStatus(objs[5].toString()) : StatusEnum.NAOENVIADO.getLabel());
				aluno.setStatus6(objs[6] != null ? StatusEnum.getLabelStatus(objs[6].toString()) : StatusEnum.NAOENVIADO.getLabel());
				grupo.getAlunosGrupo().add(aluno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return grupo;
	}
	
	@Override
	public Grupo findGrupoCPF(Long idSemestre, String cpf, Long idCampo) {
		entityManager = JpaUtil.getEntityManager();
		Grupo grupo = new Grupo();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT g FROM Grupo g JOIN FETCH g.turmaGrupo t JOIN g.alunosGrupo a ");
		hql.append("JOIN FETCH t.semestre s JOIN FETCH g.listaGrupoCampoEstagio gc JOIN gc.campoEstagio ce ");
		hql.append("WHERE s.id = :idSemestre AND a.cpf = :cpf AND ce.id = :idCampo");
		try {
		TypedQuery<Grupo> query = entityManager.createQuery(hql.toString(), Grupo.class)
				.setParameter("idSemestre", idSemestre)
				.setParameter("cpf", cpf)
				.setParameter("idCampo", idCampo);
		grupo = query.getSingleResult();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return grupo;
	}
	
	@Override
	public Grupo findGrupoPorTurmaAluno(Long idTurma, Long idAluno, Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		Grupo grupo = new Grupo();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT g FROM Grupo g JOIN FETCH g.turmaGrupo t JOIN FETCH g.alunosGrupo a ");
		hql.append("JOIN FETCH t.semestre s ");
		hql.append("WHERE s.id = :idSemestre AND a.id = :idAluno AND t.id = :idTurma");
		try {
		TypedQuery<Grupo> query = entityManager.createQuery(hql.toString(), Grupo.class)
				.setParameter("idSemestre", idSemestre)
				.setParameter("idAluno", idAluno)
				.setParameter("idTurma", idTurma);
		grupo = query.getSingleResult();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return grupo;
	}

}
