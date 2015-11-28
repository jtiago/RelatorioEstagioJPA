package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.GrupoCampoEstagioDAO;
import br.com.clogos.estagio.jpa.dao.impl.GrupoCampoEstagioDAOImpl;
import br.com.clogos.estagio.model.Grupo;
import br.com.clogos.estagio.model.GrupoCampoEstagio;

public class GrupoCampoEstagioController implements Serializable {
	private static final long serialVersionUID = 1L;
	private GrupoCampoEstagioDAO dao;
	
	public GrupoCampoEstagioDAO getDao() {
		return dao == null ? dao = new GrupoCampoEstagioDAOImpl() : dao;
	}
	
	public List<GrupoCampoEstagio> find(GrupoCampoEstagio grupoCampo) {
		return getDao().find(grupoCampo);
	}
	
	public List<GrupoCampoEstagio> findPorGrupo(Grupo grupo) {
		return getDao().findPorGrupo(grupo);
	}
	
	public List<GrupoCampoEstagio> findPorGrupoCampo(Long idGrupo, Long idCampo) {
		return getDao().findPorGrupoCampo(idGrupo, idCampo);
	}
}
