package br.com.clogos.estagio.jpa.dao;

import java.util.List;

public interface GenericDAO {
	void save(Object oT);
	void update(Object oT);
	void delete(Object oT);
	public Boolean saveList(List<?> list);
}
