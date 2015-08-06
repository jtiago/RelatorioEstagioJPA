package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.GrupoDAO;
import br.com.clogos.estagio.jpa.dao.impl.GrupoDAOImpl;

public class GrupoController implements Serializable {
	private static final long serialVersionUID = 1L;
	private GrupoDAO dao;
	
	public GrupoDAO getDao() {
		return dao == null ? dao = new GrupoDAOImpl() : dao;
	}
}
