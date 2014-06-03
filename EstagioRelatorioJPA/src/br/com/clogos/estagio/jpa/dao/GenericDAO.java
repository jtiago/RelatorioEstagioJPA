package br.com.clogos.estagio.jpa.dao;

import java.util.List;

public interface GenericDAO {
	void save(Object oT);
	void update(Object oT);
	void delete(Object oT);
	Boolean saveList(List<?> list);
	List<?> findAll(Class<?> clazz, String coluna, String order);
}
