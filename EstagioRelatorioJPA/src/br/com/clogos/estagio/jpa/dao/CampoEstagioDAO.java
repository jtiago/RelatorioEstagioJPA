package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.CampoEstagio;

public interface CampoEstagioDAO {
	List<CampoEstagio> findAll();
}
