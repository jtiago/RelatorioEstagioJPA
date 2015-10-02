package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.GrupoCampoEstagio;

public interface GrupoCampoEstagioDAO {
	List<GrupoCampoEstagio> find(GrupoCampoEstagio grupoCampo);
}
