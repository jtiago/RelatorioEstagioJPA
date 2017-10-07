package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.enums.StatusEnum;
import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.RelatorioDAO;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.util.Util;
import br.com.clogos.estagio.vo.AlunoFichaVO;
import br.com.clogos.estagio.vo.CampoEstagioFichaVO;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.GrupoFichaVO;
import br.com.clogos.estagio.vo.RelatorioStatusVO;

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
		hql.append("WHERE ra.cpf = :cpf AND r.status = :validado AND t.id = :idTurma");
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", aluno.getCpf())
					.setParameter("idTurma", aluno.getTurmaT().getId())
					.setParameter("validado", StatusEnum.ABERTO);
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
	
	/**
	 * Verifica se existe relatório enviado com o mesmo grupoCampoEstagio
	 * @param aluno
	 * @return
	 */
	@Override
	public Boolean existeRelatorioGrupoCampoEstagio(Relatorio relatorio) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT r FROM Relatorio r JOIN r.aluno ra JOIN r.turmaRelatorio t ");
		hql.append("JOIN r.grupoCampoEstagio gc ");
		hql.append("WHERE ra.cpf = :cpf AND t.id = :idTurma AND gc.id = :idGrupoCampo ");
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", relatorio.getAluno().getCpf())
					.setParameter("idTurma", relatorio.getTurmaRelatorio().getId())
					.setParameter("idGrupoCampo", relatorio.getGrupoCampoEstagio().getId());
			return query.getResultList().size() > 0;
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
		hql.append("JOIN FETCH s.imagem i JOIN FETCH r.turmaRelatorio t JOIN FETCH r.grupoCampoEstagio gc ");
		hql.append("JOIN FETCH gc.grupo JOIN FETCH t.semestre s ");
		hql.append("WHERE s.id = :idSemestre ");
		if(relatorio.getCampoEstagio().getId() != 0) {
			hql.append("AND c.id = :idCampo ");
		}
		if(relatorio.getTurmaRelatorio().getId() != 0) {
			hql.append("AND t.id = :idTurma ");
		}
		if(relatorio.getStatus() != null) {
			hql.append("AND r.status = :status ");
		}
		
		hql.append("ORDER BY a.nome ");
		
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class);
			query.setParameter("idSemestre", relatorio.getIdSemestre());
			if(relatorio.getCampoEstagio().getId() != 0) {
				query.setParameter("idCampo", relatorio.getCampoEstagio().getId());
			}
			if(relatorio.getTurmaRelatorio().getId() != 0) {
				query.setParameter("idTurma", relatorio.getTurmaRelatorio().getId());
			}
			if(relatorio.getStatus() != null) {
				query.setParameter("status", relatorio.getStatus());
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
		sql.append("UPDATE uniweb.Relatorio SET status = ?, observacao = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, StatusEnum.VALIDADO.getCodigo())
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
		sql.append("UPDATE uniweb.Relatorio SET status = ?, observacao = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, StatusEnum.REVISAO.getCodigo())
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
		hql.append("JOIN FETCH r.grupoCampoEstagio gc ");
		hql.append("WHERE a.cpf = :cpf AND r.status = :status AND s.id = :idSemestre ");
		
		try {
			TypedQuery<Relatorio> query = entityManager.createQuery(hql.toString(), Relatorio.class)
					.setParameter("cpf", aluno.getCpf())
					.setParameter("status", StatusEnum.REVISAO)
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
	
	public Boolean alterarDataInicioTerminioRelatorio(Relatorio relatorio) {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Relatorio SET fkgrupocampoestagio = ? WHERE idrelatorio = ?");
		try {
			Query query = entityManager.createNativeQuery(sql.toString())
					.setParameter(1, relatorio.getGrupoCampoEstagio().getId())
					.setParameter(2, relatorio.getId());
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
		hql.append("JOIN FETCH r.turmaRelatorio t JOIN FETCH t.semestre s JOIN FETCH r.grupoCampoEstagio gc ");
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<RelatorioStatusVO> findRelatorioStatus(Long idTurma, Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sql = new StringBuilder();
		List<RelatorioStatusVO> lista = new LinkedList<RelatorioStatusVO>();
		sql.append("SELECT cpf, a.nomealuno, t.nometurma, s.nomeSemestre, lr.qtdRelatorio, lr.modulo, COUNT(rel.fkaluno) as relenviados from uniweb.aluno a ");
		sql.append("inner join uniweb.turma_aluno ta on a.idaluno = ta.alunos_idaluno ");
		sql.append("inner join uniweb.turma t on t.idturma = ta.turmas_idturma ");
		sql.append("inner join uniweb.SEMESTRE s on s.idsemestre=t.fksemestre ");
		sql.append("inner join uniweb.LIBERARRELATORIO lr on lr.fkturma=t.idturma ");
		sql.append("left join uniweb.RELATORIO rel on rel.fkaluno=a.idaluno and rel.modulo=lr.modulo and t.idturma=rel.fkturma ");
		sql.append("WHERE t.fksemestre = :idSemestre AND t.idturma = :idTurma ");
		sql.append("GROUP BY a.cpf, a.nomealuno, t.nometurma, s.nomeSemestre, qtdRelatorio, lr.modulo " );
		sql.append("HAVING (SIGN(COUNT(rel.fkaluno) - lr.qtdRelatorio) = -1) ORDER BY a.nomealuno");
		
		try {
			Query query = entityManager.createNativeQuery(sql.toString()).setParameter("idSemestre", idSemestre).setParameter("idTurma", idTurma);
			Iterator i = query.getResultList().iterator();
			
			while(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				RelatorioStatusVO vo = new RelatorioStatusVO();
				vo.setCpf(objs[0].toString());
				vo.setNomeAluno(objs[1].toString());
				vo.setNomeTurma(objs[2].toString());
				vo.setNomeSemestre(objs[3].toString());
				vo.setQtdRelatorio(Integer.valueOf(objs[4].toString()));
				vo.setModulo(objs[5].toString());
				vo.setRelEnviados(Integer.valueOf(objs[6].toString()));
				
				lista.add(vo);
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
	public FichaAvaliacaoVO findFichaAvaliacao(Long idAluno, Long idTurma, Long idSemestre) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sqlAlunoFicha = new StringBuilder();
		StringBuilder sqlGrupoFicha = new StringBuilder();
		StringBuilder sqlCampoEstagio = new StringBuilder();
		
		AlunoFichaVO  alunoFichaVO = new AlunoFichaVO();
		GrupoFichaVO grupoFichaVO = null;
		CampoEstagioFichaVO campoEstagioFichaVO = null;
		FichaAvaliacaoVO fichaAvaliacaoVO = new FichaAvaliacaoVO();
		List<GrupoFichaVO> listaGrupoCampo = new LinkedList<GrupoFichaVO>();
		List<CampoEstagioFichaVO> listaCampoEstagio = new LinkedList<CampoEstagioFichaVO>();
		
		sqlAlunoFicha.append("SELECT a.idaluno, cpf, a.nomealuno, t.nometurma, t.nomeCurso, rel.modulo, g.nomeGrupo, s.nomeSemestre, lr.qtdRelatorio, count(rel.idrelatorio) from uniweb.aluno a ");
		sqlAlunoFicha.append("inner join uniweb.turma_aluno ta on a.idaluno = ta.alunos_idaluno ");
		sqlAlunoFicha.append("inner join uniweb.turma t on t.idturma = ta.turmas_idturma ");
		sqlAlunoFicha.append("inner join uniweb.SEMESTRE s on s.idsemestre=t.fksemestre ");
		sqlAlunoFicha.append("inner join uniweb.LIBERARRELATORIO lr on lr.fkturma=t.idturma ");
		sqlAlunoFicha.append("left join uniweb.RELATORIO rel on rel.fkaluno=a.idaluno and rel.modulo=lr.modulo and t.idturma=rel.fkturma ");
		sqlAlunoFicha.append("inner join uniweb.grupo g on g.idgrupo in (select g.idgrupo from uniweb.GRUPO g inner join uniweb.grupo_aluno ga on g.idgrupo=ga.grupos_idgrupo where ga.alunosGrupo_idaluno=a.idaluno and g.fkturma=t.idturma) ");
		sqlAlunoFicha.append("WHERE t.fksemestre = :idSemestre AND t.idturma = :idTurma and a.idaluno= :idAluno ");
		sqlAlunoFicha.append("group by a.idaluno, cpf, a.nomealuno, t.nometurma, t.nomeCurso, rel.modulo, s.nomeSemestre, lr.qtdRelatorio, g.nomeGrupo order by a.nomealuno");

		sqlGrupoFicha.append("select g.idgrupo, c.siglacampoestagio, gc.dataInicial, gc.dataFinal, rel.idrelatorio from uniweb.grupo_aluno ga ");
		sqlGrupoFicha.append("inner join uniweb.GRUPO g on g.idgrupo=ga.grupos_idgrupo ");
		sqlGrupoFicha.append("inner join uniweb.TURMA t on t.idturma=g.fkturma ");
		sqlGrupoFicha.append("inner join uniweb.SEMESTRE s on s.idsemestre=t.fksemestre ");
		sqlGrupoFicha.append("inner join uniweb.GrupoCampoEstagio gc on gc.fkgrupo=g.idgrupo ");
		sqlGrupoFicha.append("inner join uniweb.CAMPOESTAGIO c on c.idcampoestagio=gc.fkcampoEstagio ");
		sqlGrupoFicha.append("left join uniweb.RELATORIO rel on rel.fkgrupocampoestagio=gc.id and rel.fkaluno=ga.alunosGrupo_idaluno ");
		sqlGrupoFicha.append("where s.idsemestre = :idSemestre and alunosGrupo_idaluno = :idAluno order by gc.dataInicial ");
		
		sqlCampoEstagio.append("select c.idcampoestagio, c.nomecampoestagio from uniweb.GRUPO g ");
		sqlCampoEstagio.append("inner join uniweb.grupo_aluno ga on ga.grupos_idgrupo=g.idgrupo ");
		sqlCampoEstagio.append("inner join uniweb.GrupoCampoEstagio gce on g.idgrupo=gce.fkgrupo ");
		sqlCampoEstagio.append("inner join uniweb.CAMPOESTAGIO c on c.idcampoestagio=gce.fkcampoEstagio ");
		sqlCampoEstagio.append("where g.fkturma=:idTurma and ga.alunosGrupo_idaluno=:idAluno");
		
		try {
			Query queryAluno = entityManager.createNativeQuery(sqlAlunoFicha.toString())
					.setParameter("idSemestre", idSemestre).setParameter("idTurma", idTurma).setParameter("idAluno", idAluno);
			
			Iterator iAluno = queryAluno.getResultList().iterator();
			
			while(iAluno.hasNext()) {
				Object[] objs = (Object[]) iAluno.next();
				alunoFichaVO.setIdAluno(Long.valueOf(objs[0].toString()));
				alunoFichaVO.setCpf(objs[1].toString());
				alunoFichaVO.setNomeAluno(objs[2].toString());
				alunoFichaVO.setNomeTurma(objs[3].toString());
				alunoFichaVO.setNomeCurso(objs[4].toString());
				alunoFichaVO.setModulo(ModuloEnum.getModuloTipo(objs[5].toString()));
				alunoFichaVO.setNomeGrupo(objs[6].toString());
				alunoFichaVO.setSemestre(objs[7].toString());
				alunoFichaVO.setQtdRelatorio(Integer.valueOf(objs[8].toString()));
				alunoFichaVO.setQtdRelatorioEnviado(Integer.valueOf(objs[9].toString()));
			}
			fichaAvaliacaoVO.setAlunoFichaVO(alunoFichaVO);
			
			Query queryGrupo = entityManager.createNativeQuery(sqlGrupoFicha.toString())
					.setParameter("idSemestre", idSemestre).setParameter("idAluno", idAluno);
			
			Iterator iGrupo = queryGrupo.getResultList().iterator();
			while(iGrupo.hasNext()) {
				grupoFichaVO = new GrupoFichaVO();
				Object[] objs = (Object[]) iGrupo.next();
				grupoFichaVO.setIdGrupo(Long.valueOf(objs[0].toString()));
				grupoFichaVO.setSiglaCampoEstagio(objs[1].toString());
				grupoFichaVO.setDataInicial(Util.convertStringToDate(objs[2].toString()));
				grupoFichaVO.setDataFinal(Util.convertStringToDate(objs[3].toString()));
				grupoFichaVO.setRelEnviado(objs[4] == null ? "NH" : "H");
				listaGrupoCampo.add(grupoFichaVO);
				
			}
			fichaAvaliacaoVO.setListaGrupoCampo(listaGrupoCampo);
			
			Query queryCampo = entityManager.createNativeQuery(sqlCampoEstagio.toString())
					.setParameter("idTurma", idTurma).setParameter("idAluno", idAluno);
			
			Iterator iCampo = queryCampo.getResultList().iterator();
			while(iCampo.hasNext()) {
				campoEstagioFichaVO = new CampoEstagioFichaVO();
				Object[] objs = (Object[]) iCampo.next();
				campoEstagioFichaVO.setIdCampoEstagio(Long.valueOf(objs[0].toString()));
				campoEstagioFichaVO.setNomeCampoEstagio(objs[1].toString());
				listaCampoEstagio.add(campoEstagioFichaVO);
			}
			fichaAvaliacaoVO.setListaCampoEstagio(listaCampoEstagio);
			

		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao obter dados para Ficha de Avaliação", e.getMessage()));
			throw new NumberFormatException();
		} catch (PersistenceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Erro ao obter dados para Ficha de Avaliação", e.getMessage()));
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return fichaAvaliacaoVO;
	}
}
