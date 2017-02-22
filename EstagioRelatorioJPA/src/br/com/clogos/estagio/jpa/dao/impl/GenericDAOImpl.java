package br.com.clogos.estagio.jpa.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.GenericDAO;
import br.com.clogos.estagio.jpa.dao.ObjectModel;

public class GenericDAOImpl<T extends ObjectModel> implements GenericDAO, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	
	@Override
	public Boolean save(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(oT);
			entityManager.getTransaction().commit();
			return true;
		} catch (PersistenceException e) {
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
	public Boolean update(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(oT);
			entityManager.getTransaction().commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			return false;
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	@Override
	public Boolean delete(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entityManager.merge(oT));
			entityManager.getTransaction().commit();
			return true;
		} catch (PersistenceException e) {
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
	public Boolean saveList(List<?> list) {
		entityManager = JpaUtil.getEntityManager();
		try {
		    entityManager.getTransaction().begin();
		    for(Object oT : list) {
		    	entityManager.persist(oT);
		    }
		    entityManager.getTransaction().commit();
		    return Boolean.valueOf(true);
		} catch (Exception e) {
		    entityManager.getTransaction().rollback();
		    e.printStackTrace();
		    return Boolean.valueOf(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findAll(Class<?> clazz, String coluna, String order, String join) {
		entityManager = JpaUtil.getEntityManager();
		List<Object> lista = new ArrayList<Object>();
		try {
			entityManager.getTransaction().begin();
			String nameClass = clazz.getSimpleName();
			lista = entityManager.createQuery("SELECT c FROM " + nameClass+ " c "+join+" ORDER BY c."+coluna+" "+order)
					.getResultList();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
        return lista;
    }
	
	@Override
	public Object findID(Class<?> clazz, String coluna, Long id) {
		entityManager = JpaUtil.getEntityManager();
		Object obj = null;
		try {
			entityManager.getTransaction().begin();
			String nameClass = clazz.getSimpleName();
			obj = entityManager.createQuery("SELECT c FROM " + nameClass+ " c WHERE "+coluna+" = "+id)
					.getSingleResult();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
        return obj;
    }
}
