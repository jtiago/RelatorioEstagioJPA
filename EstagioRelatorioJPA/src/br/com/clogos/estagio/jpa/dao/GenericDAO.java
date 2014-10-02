package br.com.clogos.estagio.jpa.dao;

import java.util.List;

public interface GenericDAO {
	Boolean save(Object oT);
	Boolean update(Object oT);
	Boolean delete(Object oT);
	Boolean saveList(List<?> list);
	List<?> findAll(Class<?> clazz, String coluna, String order, String join);
}
