package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.CampoEstagioDAO;
import br.com.clogos.estagio.jpa.dao.impl.CampoEstagioDAOImpl;
import br.com.clogos.estagio.model.CampoEstagio;


public class CampoEstagioController implements Serializable {
	private static final long serialVersionUID = 1L;
	private CampoEstagioDAO campoEstagioDAO;
	
	public List<CampoEstagio> findAll() {
		return getCampoEstagioDAO().findAll();
	}
	
	public CampoEstagioDAO getCampoEstagioDAO() {
		if(campoEstagioDAO == null) {
			campoEstagioDAO = new CampoEstagioDAOImpl();
		}
		return campoEstagioDAO;
	}

}
