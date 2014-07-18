package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.RelatorioDAO;
import br.com.clogos.estagio.jpa.dao.impl.RelatorioDAOImpl;
import br.com.clogos.estagio.model.Aluno;

public class RelatorioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RelatorioDAO relatorioDAO;
	
	public Boolean existeRelatorioPreenchido(Aluno aluno) {
		return getRelatorioDAO().existeRelatorioPreenchido(aluno);
	}
	
	public RelatorioDAO getRelatorioDAO() {
		return relatorioDAO == null ? relatorioDAO = new RelatorioDAOImpl() : relatorioDAO;
	}

}
