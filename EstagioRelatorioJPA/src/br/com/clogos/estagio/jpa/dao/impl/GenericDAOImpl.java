package br.com.clogos.estagio.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.JpaUtil;
import br.com.clogos.estagio.jpa.dao.GenericDAO;
import br.com.clogos.estagio.jpa.dao.ObjectModel;

public class GenericDAOImpl<T extends ObjectModel> implements GenericDAO {
	private EntityManager entityManager;
	
	@Override
	public void save(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(oT);
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
		
	}

	@Override
	public void update(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(oT);
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if(entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}

	@Override
	public void delete(Object oT) {
		entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entityManager.merge(oT));
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
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
	public List<?> findAll(Class<?> clazz, String coluna, String order) {
		entityManager = JpaUtil.getEntityManager();
		List<Object> lista = new ArrayList<Object>();
		try {
			entityManager.getTransaction().begin();
			String nameClass = clazz.getSimpleName();
			lista = entityManager.createQuery("SELECT c FROM " + nameClass+ " c ORDER BY c."+coluna+" "+order)
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
}
