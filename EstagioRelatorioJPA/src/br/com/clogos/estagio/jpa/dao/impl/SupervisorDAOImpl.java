package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.SupervisorDAO;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.util.CriptografiaBase64;
import br.com.clogos.estagio.vo.SupervisorVO;


public class SupervisorDAOImpl implements SupervisorDAO, Serializable {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	@Override
	public List<Supervisor> findAll() {
		entityManager = JpaUtil.getEntityManager();
		List<Supervisor> lista = new ArrayList<Supervisor>();
		String hql = "SELECT s FROM Supervisor s INNER JOIN s.imagem i ORDER BY s.nome";
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql, Supervisor.class);
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
	public List<Supervisor> findPorCampo(Long idCampo) {
		entityManager = JpaUtil.getEntityManager();
		List<Supervisor> lista = new ArrayList<Supervisor>();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT s FROM Supervisor s JOIN s.imagem i JOIN s.campoEstagio c ");
		hql.append("WHERE c.id = :idCampo ORDER BY s.nome");
		
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql.toString(), Supervisor.class)
					.setParameter("idCampo", idCampo);
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
	public Supervisor validarAutenticacao(Supervisor param) {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT s FROM Supervisor s JOIN s.perfil p ");
		hql.append("WHERE s.cpf = :numCpf AND s.senha = :senha AND p.id = :perfil");
		Supervisor supervisor = null;
		
		try {
			TypedQuery<Supervisor> query = entityManager.createQuery(hql.toString(), Supervisor.class)
					.setParameter("numCpf", param.getCpf())
					.setParameter("senha", CriptografiaBase64.encrypt(param.getSenha()))
					.setParameter("perfil", param.getPerfil().getId());
			if(query.getResultList().size() != 0) {
				supervisor = query.getSingleResult();
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return supervisor;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<SupervisorVO> findSupervisorAnalitico() {
		entityManager = JpaUtil.getEntityManager();
		StringBuilder sql = new StringBuilder();
		List<SupervisorVO> listaSupervisoresVO = new LinkedList<SupervisorVO>();
		sql.append("select super.idsupervisor, super.cpfsupervisor, super.nomesupervisor, super.nomecampoestagio, super.siglacampoestagio, ");
		sql.append("(select count(*) from uniweb.RELATORIO rel where rel.status = 2 and fksupervisor = super.idsupervisor) as Validado,  ");
		sql.append("(select count(*) from uniweb.RELATORIO rel where rel.status = 0 and fksupervisor = super.idsupervisor) as Aberto, ");
		sql.append("(select count(*) from uniweb.RELATORIO rel where rel.status = 1 and fksupervisor = super.idsupervisor) as Revisão, ");
		sql.append("CASE ISNULL((select count(*) from uniweb.relatorio re where status in (0,1) and super.idsupervisor=re.fksupervisor),0) ");
		sql.append("when 0 THEN 'OK' ELSE 'Pedente'	END	Situação from ");
		sql.append("(select s.idsupervisor, s.cpfsupervisor, s.nomesupervisor, c.nomecampoestagio, c.siglacampoestagio from uniweb.SUPERVISOR s " );
		sql.append("inner join uniweb.CAMPOESTAGIO c on c.idcampoestagio = s.fkcampoEstagio ) as super");
		
		try {
			Query query = entityManager.createNativeQuery(sql.toString());
			Iterator i = query.getResultList().iterator();
			
			while(i.hasNext()) {
				Object[] objs = (Object[]) i.next();
				SupervisorVO vo = new SupervisorVO();
				vo.setIdSupervisor(Long.valueOf(objs[0].toString()));
				vo.setCpfSupervisor(objs[1].toString());
				vo.setNomeSupervisor(objs[2].toString());
				vo.setNomeCampoEstagio(objs[3].toString());
				vo.setSiglaCampoEstagio(objs[4].toString());
				vo.setQtdValidado(Integer.valueOf(objs[5].toString()));
				vo.setQtdAberto(Integer.valueOf(objs[6].toString()));
				vo.setQtdRevisao(Integer.valueOf(objs[7].toString()));
				vo.setSituacao(objs[8].toString());
				
				listaSupervisoresVO.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return listaSupervisoresVO;
	}
}
